package com.cdeo.sqlbase.core.builder;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cdeo.sqlbase.core.generator.DataGenerator;
import com.cdeo.sqlbase.core.generator.DataGeneratorFactory;
import com.cdeo.sqlbase.core.model.enums.MockTypeEnum;
import com.cdeo.sqlbase.core.schema.TableSchema;
import com.cdeo.sqlbase.core.schema.TableSchema.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 数据生成器
 *
 * @author https://github.com/liyupi
 */
public class DataBuilder {

    /**
     * 生成数据
     *
     * @param tableSchema
     * @param rowNum
     * @return
     */
    public static List<Map<String, Object>> generateData(TableSchema tableSchema, int rowNum) {
        List<Field> fieldList = tableSchema.getFieldList();
        // 初始化结果数据
        List<Map<String, Object>> resultList = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            resultList.add(new HashMap<>());
        }
        // 依次生成每一列
        for (Field field : fieldList) {
            MockTypeEnum mockTypeEnum = Optional.ofNullable(MockTypeEnum.getEnumByValue(field.getMockType()))
                    .orElse(MockTypeEnum.NONE);
            DataGenerator dataGenerator = DataGeneratorFactory.getGenerator(mockTypeEnum);
            List<String> mockDataList = dataGenerator.doGenerate(field, rowNum);
            String fieldName = field.getFieldName();
            // 填充结果列表
            if (CollectionUtils.isNotEmpty(mockDataList)) {
                for (int i = 0; i < rowNum; i++) {
                    resultList.get(i).put(fieldName, mockDataList.get(i));
                }
            }
        }
        return resultList;
    }
}
