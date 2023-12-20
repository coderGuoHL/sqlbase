package com.cdeo.sqlbase.core.model.dto;

import lombok.Data;

@Data
public class FieldGenerateDTO {
    /**
     * 表的字段名，一般来说是下划线写法
     */
    private String fieldName;

    /**
     * 表的字段名的小驼峰写法
     */
    private String camelFieldName;

    /**
     * 表的字段名的大驼峰写法
     */
    private String bigCamelFieldName;

    /**
     * 注释（字段中文名）
     */
    private String comment;

    private String javaType;
}
