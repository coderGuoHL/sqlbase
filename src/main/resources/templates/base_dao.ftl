package com.test.${className}DAO;
// ${'路径需要修改为本地包路径,或者直接复制下面代码的内容即可'}

public abstract class ${className}DAO extends GeneralDAO<${className}> {
<#-- 生成id字段的查询方法 -->
<#list containIdFieldList as containIdField>
    public List<${className}> qryBy${containIdField.bigCamelFieldName}(${containIdField.javaType} ${containIdField.camelFieldName}) {
        if (ObjectUtils.isEmpty(${containIdField.camelFieldName})) {
            return new ArrayList<>();
        }

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM ${tableName} ${tableAlias!''} WHERE ${tableAlias!''}.${containIdField.fieldName} = ?");
        return queryForList(sql.toString(), ${containIdField.camelFieldName});
    }

</#list>
}