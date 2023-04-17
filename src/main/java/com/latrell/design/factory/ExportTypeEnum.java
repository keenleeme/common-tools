package com.latrell.design.factory;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 导出枚举类
 *
 * @author liz
 * @date 2023/3/11-15:47
 */
@Getter
@AllArgsConstructor
public enum ExportTypeEnum {

    WORD("1", "word"),
    PDF("2", "pdf"),
    EXCEL("3", "excel");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    @Override
    public String toString() {
        return this.value;
    }
}
