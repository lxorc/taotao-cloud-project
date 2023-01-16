package com.taotao.cloud.workflow.api.common.database.sql.impl;


import com.taotao.cloud.workflow.api.common.database.source.DbBase;

/**
 * 类功能
 *
 */
public class SqlPostgre extends SqlBase {

    /**
     * 构造初始化
     */
    public SqlPostgre(DbBase dbBase) {
        super(dbBase);
    }

    @Override
    protected void init() {
        String fieldListSql =  "SELECT\n" +
                "\tbase.\"column_name\"\n"+ DbAliasConst.FIELD_NAME+",\n" +
                "\tcol_description ( t1.oid, t2.attnum )\n"+DbAliasConst.FIELD_COMMENT+",\n" +
                "\tbase.udt_name\n"+DbAliasConst.DATA_TYPE+",\n" +
                "\tt2.attnotnull AS\t"+ DbAliasConst.ALLOW_NULL+",\n" +
                "\tCOALESCE(character_maximum_length, numeric_precision, datetime_precision)\n"+DbAliasConst.DATA_LENGTH+",\n" +
                "\t(CASE\n" +
                "\t\tWHEN ( SELECT t2.attnum = ANY ( conkey ) FROM pg_constraint WHERE conrelid = t1.oid AND contype = 'p' ) = 't' \n" +
                "\t\tTHEN 1 ELSE 0 \n" +
                "\tEND ) \n" + DbAliasConst.PRIMARY_KEY +
                "\nFROM\n" +
                "\tinformation_schema.COLUMNS base,\n" +
                "\tpg_class t1,\n" +
                "\tpg_attribute t2 \n" +
                "WHERE\n" +
                "\tbase.\"table_name\" = "+ ParamEnum.TABLE.getParamSign()+" \n" +
                "\tAND t1.relname = base.\"table_name\" \n" +
                "\tAND t2.attname = base.\"column_name\" \n" +
                "\tAND t1.oid = t2.attrelid \n" +
                "\tAND t2.attnum > 0;\n";
        String tableListSql =
                "select relname as "+ DbAliasConst.TABLE_NAME +",cast(obj_description(relfilenode,'pg_class') " +
                        "as varchar) as " +DbAliasConst.TABLE_COMMENT+ ",reltuples " + DbAliasConst.TABLE_SUM
                        + " from pg_class c\n" +
                        "where relname in (select tablename from pg_tables where schemaname='public'" +
                        " and position('_2' in tablename)=0);";
        String existsTableSql =
                "select relname as "+ DbAliasConst.TABLE_NAME +" from pg_class c\n" +
                        "where relname = "+ParamEnum.TABLE.getParamSign()+" ;";
        setInstance(fieldListSql,tableListSql, existsTableSql,
                "{table}:",
                "",
                "{table}"
        );
    }

    @Override
    public String[] getPageSql(String sql, String sortType, Integer currentPage, Integer pageSize) {
        int startIndex = currentPage - 1;
        String sortSql = StringUtil.isEmpty(sortType)?"":" ORDER BY " + sortType;
        // 获取dataListSql
        String dataListSql = sql + sortSql + " LIMIT "+ pageSize+ " OFFSET " + startIndex * pageSize + ";";
        // 获取totalSql
        String totalSql = "SELECT COUNT(*) " + DbAliasEnum.TOTAL_RECORD.asByDb(this.dbBase) + " FROM (" + sql + ") workflow_tab;";
        return new String[]{dataListSql, totalSql};
    }

}
