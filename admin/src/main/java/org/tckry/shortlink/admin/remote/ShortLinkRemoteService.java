package org.tckry.shortlink.admin.remote;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RequestParam;
import org.tckry.shortlink.admin.common.convention.result.Result;
import org.tckry.shortlink.admin.dto.req.RecycleBinRecoverReqDTO;
import org.tckry.shortlink.admin.dto.req.RecycleBinRemoveReqDTO;
import org.tckry.shortlink.admin.dto.req.RecycleBinSaveReqDTO;
import org.tckry.shortlink.admin.remote.dto.req.*;
import org.tckry.shortlink.admin.remote.dto.resp.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短链接中台远程调用服务（改造为微服务弃用）
 * service 层参数不加注解@RequestBody
 */
public interface ShortLinkRemoteService {


//    /**
//    * 创建短链接
//    * @Param: [requestParam]
//    * @return:
//    * @Date: 2023/12/21
//    */
//    default Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO requestParam){ // 这里不加@RequestBody，中台调用后台，后台统一格式转json，中台之间都传对象
//        String resultBodyStr = HttpUtil.post("http://localhost:8001/api/short-link/v1/create",JSON.toJSONString(requestParam));
//        return JSON.parseObject(resultBodyStr,new TypeReference<>() {   //  new TypeReference<>()作用：Result内含泛型对象，进行反序列化时不知道具体类型，帮助类型转换进行反序列化
//        });
//    }

//    /**
//     * 批量创建短链接
//     * @Param: [requestParam]
//     * @return: org.tckry.shortlink.admin.common.convention.result.Result<org.tckry.shortlink.admin.remote.dto.resp.ShortLinkBatchCreateRespDTO>
//     * @Date: 2023/12/30
//     */
//    default Result<ShortLinkBatchCreateRespDTO> batchCreateShortLink(ShortLinkBatchCreateReqDTO requestParam){
//        String resultBodyStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/create/batch", JSON.toJSONString(requestParam));
//        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
//        });
//    }

//    /**
//     * 短链接修改
//     * @Param: [requestParam]
//     * @return: void
//     * @Date: 2023/12/22
//     */
//    default void updateShortLink(ShortLinkUpdateReqDTO requestParam){   // 这里不加@RequestBody，中台调用后台，后台统一格式转json，中台之间都传对象
//        HttpUtil.post("http://localhost:8001/api/short-link/v1/update",JSON.toJSONString(requestParam));
//    }

//    /**
//    * 分页查询短链接
//    * @Param: [requestParam] 分页短链接请求参数
//    * @return:  查询短链接响应
//    * @Date: 2023/12/21
//    */
//    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){    // // 这里不加@RequestBody，中台调用后台，后台统一格式转json，中台之间都传对象
//        Map<String,Object> requestMap = new HashMap<>();    // 请求GetMapping方式，传入的json数据通过Map进行放入自动解析
//        requestMap.put("gid",requestParam.getGid());
//        requestMap.put("orderTag",requestParam.getOrderTag());
//        requestMap.put("current",requestParam.getCurrent());
//        requestMap.put("size",requestParam.getSize());
//        String resultPageStr = HttpUtil.get("http://localhost:8001/api/short-link/v1/page",requestMap);
//        return JSON.parseObject(resultPageStr, new TypeReference<>() {   //  new TypeReference<>()作用：Result内含泛型对象，进行反序列化时不知道具体类型，帮助类型转换进行反序列化
//        });
//    }

//    /**
//     * 查询分组短链接数量
//     * @Param: [requestParam] 分组短链接数量请求参数
//     * @return:  返回查询分组短链接数量
//     * @Date: 2023/12/21
//     */
//    default Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(List<String> requestParam){    // 接口内方法含方法体，default方法
//        Map<String,Object> requestMap = new HashMap<>();    // 请求GetMapping方式，传入的json数据通过Map进行放入自动解析
//        requestMap.put("requestParam",requestParam);
//        String resultPageStr = HttpUtil.get("http://localhost:8001/api/short-link/v1/count",requestMap);
//        return JSON.parseObject(resultPageStr, new TypeReference<>() {   //  new TypeReference<>()作用：Result内含泛型对象，进行反序列化时不知道具体类型，帮助类型转换进行反序列化
//        });
//    }

//    /**
//    * 根据 URL 获取对应网站标题
//    * @Param: [url]
//    * @return: org.tckry.shortlink.admin.common.convention.result.Result<java.lang.String>
//    * @Date: 2023/12/24
//    */
//   default Result<String> getTitleByUrl(@RequestParam("url") String url) {
//       String resultStr = HttpUtil.get("http://localhost:8001/api/short-link/v1/title?url=" + url);
//       return JSON.parseObject(resultStr, new TypeReference<>() {   //  new TypeReference<>()作用：Result内含泛型对象，进行反序列化时不知道具体类型，帮助类型转换进行反序列化
//       });
//   }

//   /**
//   * 保存回收站
//   * @Param: [requestParam]
//   * @return: org.tckry.shortlink.admin.common.convention.result.Result<java.lang.Void>
//   * @Date: 2023/12/25
//   */
//   default void saveRecycleBin(RecycleBinSaveReqDTO requestParam){  // 这里不加@RequestBody，中台调用后台，后台统一格式转json，中台之间都传对象
//       HttpUtil.post("http://localhost:8001/api/short-link/v1/recycle-bin/save",JSON.toJSONString(requestParam));
//
//   }

//    /**
//     * 分页查询回收站短链接
//     * @Param: [requestParam] 分页短链接请求参数
//     * @return:  查询短链接响应
//     * @Date: 2023/12/21
//     */
//    default Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam){    // // 这里不加@RequestBody，中台调用后台，后台统一格式转json，中台之间都传对象
//        Map<String,Object> requestMap = new HashMap<>();    // 请求GetMapping方式，传入的json数据通过Map进行放入自动解析
//        requestMap.put("gidList",requestParam.getGidList());  // 加上分组不利于检索， 这里去掉分组去中台处理分组
//        requestMap.put("current",requestParam.getCurrent());
//        requestMap.put("size",requestParam.getSize());
//        String resultPageStr = HttpUtil.get("http://localhost:8001/api/short-link/v1/recycle-bin/page",requestMap);
//        return JSON.parseObject(resultPageStr, new TypeReference<>() {   //  new TypeReference<>()作用：Result内含泛型对象，进行反序列化时不知道具体类型，帮助类型转换进行反序列化
//        });
//    }
//
//    /**
//    * 恢复短链接
//    * @Param: [requestParam]
//    * @return:
//    * @Date: 2023/12/25
//    */
//    default void recoverRecycleBin(RecycleBinRecoverReqDTO requestParam){
//        HttpUtil.post("http://localhost:8001/api/short-link/v1/recycle-bin/recover",JSON.toJSONString(requestParam));
//    }
//
//    /**
//    * 移除短链接
//    * @Param: [requestParam]
//    * @return: void
//    * @Date: 2023/12/25
//    */
//    default void removeRecycleBin(RecycleBinRemoveReqDTO requestParam) {
//        HttpUtil.post("http://localhost:8001/api/short-link/v1/recycle-bin/remove",JSON.toJSONString(requestParam));
//    }

//    /**
//    * 访问单个短链接指定时间内监控数据
//    * @Param: [requestParam]
//    * @return: org.tckry.shortlink.admin.common.convention.result.Result<org.tckry.shortlink.admin.remote.dto.resp.ShortLinkStatsRespDTO>
//    * @Date: 2023/12/27
//    */
//
//    default Result<ShortLinkStatsRespDTO> oneShortLinkStats(ShortLinkStatsReqDTO requestParam){
//        String resultBodyStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/stats", BeanUtil.beanToMap(requestParam));
//        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
//        });
//    }
//
//    /**
//     * 访问分组短链接指定时间内监控数据
//     * @Param: [requestParam]
//     * @return:
//     */
//    default Result<ShortLinkStatsRespDTO> groupShortLinkStats(ShortLinkGroupStatsReqDTO requestParam){
//        String resultBodyStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/stats/group", BeanUtil.beanToMap(requestParam));
//        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
//        });
//    }

//    /**
//    * 访问单个短链接指定时间内监控访问记录数据
//    * @Param: [requestParam]
//    * @return:
//    * @Date: 2023/12/27
//    */
//    default Result<IPage<ShortLinkStatsAccessRecordRespDTO>> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam){
//        String resultBodyStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/stats/access-record", BeanUtil.beanToMap(requestParam));
//        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
//        });
//    }

//    /**
//    * 访问分组短链接指定时间内监控访问记录数据
//    * @Param: [requestParam]
//    * @return: org.tckry.shortlink.admin.common.convention.result.Result<com.baomidou.mybatisplus.core.metadata.IPage<org.tckry.shortlink.admin.remote.dto.resp.ShortLinkStatsAccessRecordRespDTO>>
//    * @Date: 2023/12/30
//    */
//    default Result<IPage<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsAccessRecord(ShortLinkGroupStatsAccessRecordReqDTO requestParam){
//        String resultBodyStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/stats/access-record/group", BeanUtil.beanToMap(requestParam));
//        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
//        });
//    }


}
