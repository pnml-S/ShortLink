package org.tckry.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tckry.shortlink.admin.common.biz.user.UserContext;
import org.tckry.shortlink.admin.common.convention.exception.ClientException;
import org.tckry.shortlink.admin.common.convention.result.Result;
import org.tckry.shortlink.admin.dao.entity.GroupDO;
import org.tckry.shortlink.admin.dao.mapper.GroupMapper;
import org.tckry.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
import org.tckry.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import org.tckry.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import org.tckry.shortlink.admin.remote.ShortLinkActualRemoteService;

import org.tckry.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import org.tckry.shortlink.admin.service.GroupService;
import org.tckry.shortlink.admin.toolkit.RandomGenerator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.tckry.shortlink.admin.common.constant.RedisCacheConstant.LOCK_GROUP_CREATE_KEY;

/**
 * 短链接分组接口实现曾
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    private final ShortLinkActualRemoteService shortLinkActualRemoteService;
    private final RedissonClient redissonClient;
    @Value("${short-link.group.max-num}")
    private Integer groupMaxNum;


    @Override
    public void saveGroup(String groupName) {
        saveGroup(UserContext.getUsername(),groupName);
    }

    @Override
    public void saveGroup(String username, String groupName) {
        RLock lock = redissonClient.getLock(StrUtil.format(LOCK_GROUP_CREATE_KEY, username));
        lock.lock();
        try {
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getUsername,username)
                    .eq(GroupDO::getDelFlag, 0);
            List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
            if (CollUtil.isNotEmpty(groupDOList) && groupDOList.size() == groupMaxNum) {
                throw new ClientException(String.format("超出用户最大分组数量：%d", groupMaxNum));
            }
            String gid;
            do {
                gid = RandomGenerator.generateRandomString();   // 自动生成的gid万一重复，先判断生成的gid是否在数据库存在，存在的话重新生成
            }while (!hasGid(username,gid));

            GroupDO groupDO = GroupDO.builder()
                    .name(groupName)
                    .gid(gid)
                    .username(username)
                    .sortOrder(0)
                    .build();

            baseMapper.insert(groupDO);
        } finally {
            lock.unlock();
        }

    }

    /**
    * 查询用户短链接分组集合
    * @Param: []
    * @return: 短链接分组集合
    * @Date: 2023/12/19
    */

    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        // TODO 从当前请求里面获取用户名，由网关管理，这里先不做
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag,0)
                .eq(GroupDO::getUsername,UserContext.getUsername())
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);

        // 把每个分组内的短链接数量封装进分组集合ShortLinkGroupRespDTO
        Result<List<ShortLinkGroupCountQueryRespDTO>> listResult = shortLinkActualRemoteService
                .listGroupShortLinkCount(groupDOList.stream().map(GroupDO::getGid).toList());// List<GroupDO>转 List<String>的gid集合

        List<ShortLinkGroupRespDTO> shortLinkGroupRespDTOList = BeanUtil.copyToList(groupDOList, ShortLinkGroupRespDTO.class);  // List<GroupDO>转 List<ShortLinkGroupRespDTO，但是集合内的ShortLinkGroupRespDTO数量字段还为空
        // 为List内的每个ShortLinkGroupRespDTO对象设置分组内的连接总数量
        shortLinkGroupRespDTOList.forEach(each->{
            Optional<ShortLinkGroupCountQueryRespDTO> first = listResult.getData().stream()
                    .filter(item -> Objects.equals(item.getGid(), each.getGid()))   // 找到当前shortLinkGroupRespDTO的gid在用户gid集合里相等的gid
                    .findFirst();
            // first 为空才设置
            first.ifPresent(item -> each.setShortLinkCount(first.get().getShortLinkCount()));
        });
        return shortLinkGroupRespDTOList;
    }

    /**
    * 修改短链接分组名称
    * @Param: [requestParam]
    * @return: java.lang.Void
    * @Date: 2023/12/19
    */

    @Override
    public void updateGroup(ShortLinkGroupUpdateReqDTO requestParam) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, requestParam.getGid())
                .eq(GroupDO::getDelFlag, 0);
        GroupDO groupDO = new GroupDO();
        groupDO.setName(requestParam.getName());    // 修改分组名称
        baseMapper.update(groupDO, updateWrapper);
    }

    @Override
    public void deleteGroup(String gid) {
        //采用软删除的方式，update ，delflag设为1
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getDelFlag, 0);
        GroupDO groupDO = new GroupDO();
        groupDO.setDelFlag(1);
        baseMapper.update(groupDO, updateWrapper);
    }

    @Override
    public void sortGroup(List<ShortLinkGroupSortReqDTO> requestParam) {
        requestParam.forEach(each->{
            GroupDO groupDO = GroupDO.builder()
                    .sortOrder(each.getSortOrder()) // 只修改 顺序，只build的这个值
                    .build();
            LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                    .eq(GroupDO::getUsername, UserContext.getUsername())
                    .eq(GroupDO::getGid, each.getGid())
                    .eq(GroupDO::getDelFlag, 0);
            baseMapper.update(groupDO,updateWrapper);
        });
    }

    private boolean hasGid(String username,String gid){
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag,0)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername,Optional.ofNullable(username).orElse(UserContext.getUsername()));
        GroupDO hasGroupFlag = baseMapper.selectOne(queryWrapper);
        return hasGroupFlag ==null;
    }
}
