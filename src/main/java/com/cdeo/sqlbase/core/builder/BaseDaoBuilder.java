package com.cdeo.sqlbase.core.builder;

import cn.hutool.core.util.StrUtil;
import com.cdeo.sqlbase.common.ErrorCode;
import com.cdeo.sqlbase.core.model.dto.FieldGenerateDTO;
import com.cdeo.sqlbase.core.model.dto.JavaDaoCodeGenerateDTO;
import com.cdeo.sqlbase.core.model.enums.FieldTypeEnum;
import com.cdeo.sqlbase.core.schema.TableSchema;
import com.cdeo.sqlbase.exception.BusinessException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BaseDaoBuilder {
    private static Configuration configuration;

    @Resource
    public void setConfiguration(Configuration configuration) {
        BaseDaoBuilder.configuration = configuration;
    }

    // 根据tableSchema生成的对应的dao层代码，
    // 1、获取到field字段中带有’id‘的字段，这种字段需要生成查询方法
    @SneakyThrows
    public static String buildBaseDaoCode(TableSchema tableSchema) {
        JavaDaoCodeGenerateDTO javaDaoCodeGenerateDTO = prepareDataForTemp(tableSchema);

        StringWriter out = new StringWriter();
        Template temp = getTemplate("base_dao.ftl");
        temp.process(javaDaoCodeGenerateDTO, out);
        return out.toString();
    }

    private static Template getTemplate(String fileName) {
        Template temp;
        try {
            temp = configuration.getTemplate(fileName);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.PATH_NOT_FOUND, e.getMessage());
        }

        return temp;
    }

    private static JavaDaoCodeGenerateDTO prepareDataForTemp(TableSchema tableSchema) {
        JavaDaoCodeGenerateDTO javaDaoCodeGenerateDTO = new JavaDaoCodeGenerateDTO();
        String tableName = tableSchema.getTableName();
        String upperCamelTableName = StringUtils.capitalize(StrUtil.toCamelCase(tableName));
        List<TableSchema.Field> fieldList = tableSchema.getFieldList();
        List<FieldGenerateDTO> fieldDTOList;
        List<FieldGenerateDTO> containIdfieldDTOList;


        javaDaoCodeGenerateDTO.setTableName(tableName);
        javaDaoCodeGenerateDTO.setTableAlias("a");
        javaDaoCodeGenerateDTO.setClassName(upperCamelTableName);
        // 获取字段里的信息塞入到 fieldGenerateDTO 中
        fieldDTOList = fieldList.stream().map(field -> {
            FieldGenerateDTO fieldGenerateDTO = new FieldGenerateDTO();
            String originalFieldName = field.getFieldName();
            FieldTypeEnum fieldTypeEnum = Optional.ofNullable(FieldTypeEnum.getEnumByValue(field.getFieldType())).orElse(FieldTypeEnum.TEXT);

            fieldGenerateDTO.setFieldName(originalFieldName);
            fieldGenerateDTO.setCamelFieldName(StrUtil.toCamelCase(originalFieldName));
            fieldGenerateDTO.setBigCamelFieldName(StringUtils.capitalize(StrUtil.toCamelCase(originalFieldName)));
            fieldGenerateDTO.setJavaType(fieldTypeEnum.getJavaType());
            return fieldGenerateDTO;
        }).collect(Collectors.toList());

        //
        containIdfieldDTOList = fieldDTOList.stream().filter(fieldGenerateDTO -> {
            String camelFieldName = fieldGenerateDTO.getCamelFieldName();
            if (StringUtils.isEmpty(camelFieldName)) {
                return false;
            }

            return camelFieldName.contains("Id");
        }).collect(Collectors.toList());

        javaDaoCodeGenerateDTO.setFieldList(fieldDTOList);
        javaDaoCodeGenerateDTO.setContainIdFieldList(containIdfieldDTOList);
        return javaDaoCodeGenerateDTO;
    }

}
