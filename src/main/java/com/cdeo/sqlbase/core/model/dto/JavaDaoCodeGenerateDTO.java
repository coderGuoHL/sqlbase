package com.cdeo.sqlbase.core.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class JavaDaoCodeGenerateDTO {
    // 表名的驼峰展示
    private String className;

    private String classComment;

    // 需要生成查询语句时填入表的名字
    private String tableName;

    // 取两个单词间的首字母
    private String tableAlias;

    // 所有字段名
    private List<FieldGenerateDTO> fieldList;

    private List<FieldGenerateDTO> containIdFieldList;
}
