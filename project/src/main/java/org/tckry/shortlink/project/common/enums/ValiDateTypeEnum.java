package org.tckry.shortlink.project.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 有效期类型
 * @program: shortlink
 * @description:
 * @author: lydms
 * @create: 2023-12-22 20:01
 **/
@RequiredArgsConstructor
public enum ValiDateTypeEnum {

    /**
     * 永久有效期
     */
    PERMANENT(0),

    /**
     * 自定义有效期
     */
    CUSTOM(1);

    @Getter
    private final int type;


}
