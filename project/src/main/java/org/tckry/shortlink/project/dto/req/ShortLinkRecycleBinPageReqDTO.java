package org.tckry.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.tckry.shortlink.project.dao.entity.ShortLinkDO;

import java.util.List;

/**
 * 短链接分页请求参数
 * @program: shortlink
 * @description:
 * @author: lydms
 * @create: 2023-12-25 16:29
 **/
@Data
public class ShortLinkRecycleBinPageReqDTO extends Page<ShortLinkDO> {

    /**
     * 分组标识
     */
    private List<String> gidList;
}
