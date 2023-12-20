package org.tckry.shortlink.project.dto.req;

import lombok.Data;

import java.util.Date;

/**
 * 短连接创建请求对象
 * @program: shortlink
 * @description:
 * @author: lydms
 * @create: 2023-12-20 17:35
 **/
@Data
public class ShortLinkCreateReqDTO {

    /**
     * 域名
     */
    private String domain;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 分组标识
     */
    private String gid;


    /**
     * 创建类型 0：控制台 1：接口
     */
    private Integer createdType;

    /**
     * 有效期类型 0：永久有效 1：用户自定义
     */
    private Integer validDateType;

    /**
     * 有效期
     */
    private Date validDate;

    /**
     * 描述
     */
    private String describe;

}
