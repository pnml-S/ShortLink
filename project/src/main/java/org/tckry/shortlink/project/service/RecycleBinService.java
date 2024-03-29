package org.tckry.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.tckry.shortlink.project.dao.entity.ShortLinkDO;
import org.tckry.shortlink.project.dto.req.*;
import org.tckry.shortlink.project.dto.resp.ShortLinkPageRespDTO;

/** 
* 回收站管理接口层
* @Param:
* @return: 
* @Date: 2023/12/25
*/

public interface RecycleBinService extends IService<ShortLinkDO>{

    /**
    * 保存回收站
    * @Param: [requestParam]
    * @return: void
    * @Date: 2023/12/25
    */
    void saveRecycleBin(RecycleBinSaveReqDTO requestParam);

    /**
     * 分页查询回收站短链接
     * @Param: [requestParam]
     * @return:
     * @Date: 2023/12/20
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkRecycleBinPageReqDTO requestParam);

    /** 
    * 恢复短链接
    * @Param: [requestParam]
    * @return: void
    * @Date: 2023/12/25
    */
    
    void recoverRecycleBin(RecycleBinRecoverReqDTO requestParam);

    /**
    * 移除短链接
    * @Param: [requestParam]
    * @return: void
    * @Date: 2023/12/25
    */

    void removeRecycleBin(RecycleBinRemoveReqDTO requestParam);
}
