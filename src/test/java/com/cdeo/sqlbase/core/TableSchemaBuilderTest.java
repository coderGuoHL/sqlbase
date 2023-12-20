package com.cdeo.sqlbase.core;


import com.cdeo.sqlbase.core.schema.TableSchemaBuilder;
import org.junit.jupiter.api.Test;

/**
 * 表概要生成器测试
 *
 * @author https://github.com/liyupi
 */
class TableSchemaBuilderTest {

    @Test
    void getFieldTypeByValue() {
        System.out.println(TableSchemaBuilder.getFieldTypeByValue("123.4"));
    }
}