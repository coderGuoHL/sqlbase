package com.cdeo.sqlbase.core.model.vo;

import com.cdeo.sqlbase.core.schema.TableSchema;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * 生成的返回值
 *
 * @author https://github.com/liyupi
 */
@Data
public class GenerateVO implements Serializable {

    private TableSchema tableSchema;

    private String createSql;

    private List<Map<String, Object>> dataList;

    private String insertSql;

    private String dataJson;

    private String javaEntityCode;

    private String javaObjectCode;

    private String typescriptTypeCode;

    private String baseDaoCode;

    private String mysqlDaoCode;

    private String oracleDaoCode;

    private static final long serialVersionUID = 7122637163626243606L;
}
