package com.taotao.cloud.workflow.api.database.model;


import com.taotao.cloud.workflow.api.database.model.interfaces.JdbcGetMod;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DbTableModel extends JdbcGetMod {

	/**
	 * 标识
	 */
	private String id;

	/**
	 * 表名
	 */
	private String table;

	/**
	 * 新建表名
	 */
	private String newTable;

	/**
	 * 表说明 （PS:属性名歧义,但涉及多平台，故内部做处理）
	 */
	private String tableName;

	public String getTableComment() {
		return tableName;
	}

	/**
	 * 说明 (PS:这个字段用来返回，字段名+注释)
	 */
	private String description;

	/**
	 * 大小
	 */
	private String size;

	/**
	 * 总数
	 */
	private Integer sum;

	/**
	 * 主键
	 */
	private String primaryKey;

	/**
	 * 数据源主键
	 */
	private String dataSourceId;

	@Override
	public void setMod(ModelDTO modelDTO) {
		try {
			ResultSet resultSet = modelDTO.getResultSet();
			//表名
			String table = resultSet.getString(DbAliasConst.TABLE_NAME);
			//表注释
			String tableComment = resultSet.getString(DbAliasConst.TABLE_COMMENT);
			//表说明
			String description = table + "(" + tableComment + ")";
			//表总数
			Integer sum = resultSet.getInt(DbAliasConst.TABLE_SUM);
			//表大小（由于部分数据库，版本取消了此功能）
			/*String size = resultSet.getString(DbAliasEnum.TABLE_SIZE.AS());*/
			this.
				setTable(table).
				setTableName(tableComment).
				setDescription(description).
				setSum(sum).
				setSize(size);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
