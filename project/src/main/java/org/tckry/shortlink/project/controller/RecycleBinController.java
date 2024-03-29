package org.tckry.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.tckry.shortlink.project.common.convention.result.Result;
import org.tckry.shortlink.project.common.convention.result.Results;
import org.tckry.shortlink.project.dto.req.*;
import org.tckry.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import org.tckry.shortlink.project.service.RecycleBinService;

/**
 * 回收站控制层
 * @program: shortlink
 * @description:
 * @author: lydms
 * @create: 2023-12-25 14:54
 **/
@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;


    /**
    * 保存回收站
    * @Param: [requestParam]
    * @return: org.tckry.shortlink.project.common.convention.result.Result<java.lang.Void>
    * @Date: 2023/12/25
    */

    @PostMapping("/api/short-link/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        recycleBinService.saveRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 分页查询短链接
     * @Param: [requestParam]
     * @return:
     * @Date: 2023/12/20
     */
    @GetMapping("/api/short-link/v1/recycle-bin/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkRecycleBinPageReqDTO requestParam){ // GET 请求，body不带数据，这个get请求，它传递的是param（接在url后面），所以不拿requestbody接（请求体），不用@RequestBody
        return Results.success(recycleBinService.pageShortLink(requestParam));
    }

    /** 
    * 恢复短链接
    * @Param: [requestParam]
    * @return: org.tckry.shortlink.project.common.convention.result.Result<java.lang.Void>
    * @Date: 2023/12/25
    */
    @PostMapping("/api/short-link/v1/recycle-bin/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO requestParam) {
        recycleBinService.recoverRecycleBin(requestParam);
        return Results.success();
    }

    /**
    * 移除短链接
    * @Param: [requestParam]
    * @return: org.tckry.shortlink.project.common.convention.result.Result<java.lang.Void>
    * @Date: 2023/12/25
    */
    @PostMapping("/api/short-link/v1/recycle-bin/remove")
    public Result<Void> removeRecycleBin(@RequestBody RecycleBinRemoveReqDTO requestParam) {
        recycleBinService.removeRecycleBin(requestParam);
        return Results.success();
    }


}
