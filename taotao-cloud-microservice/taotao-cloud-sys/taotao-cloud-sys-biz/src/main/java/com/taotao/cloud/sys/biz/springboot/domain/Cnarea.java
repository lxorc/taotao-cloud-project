package com.taotao.cloud.sys.biz.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Model class of cnarea.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cnarea{

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** id. */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * cid：该字段用于mongodb的"_id"索引
	 * 1、需要@Id注解
	 * 2、取名无所谓，反正在mongodb中最后都会转化为"_id"
	 * 3、定义为String类型，如果定义为Integer可能索引只会是0，会出现key重复导致数据库插不进去的情况；
	 * 4、该类型也是MongoRepository泛型中主键的ID
	 */
	//private String cid;

	/** parent_id. */
	@Column
	private String parentCode;

	/** level. */
	@Column
	private Integer level;

	/** area_code. */
	@Column
	private  String areaCode;

	/** zip_code. */
	@Column
	private Integer zipCode;

	/** city_code. */
	@Column
	private String cityCode;

	/** name. */
	@Column
	private String name;

	/** short_name. */
	@Column
	private String shortName;

	/** merger_name. */
	@Column
	private String mergerName;

	/** pinyin. */
	@Column
	private String pinyin;

	/** lng. */
	@Column
	private Double lng;

	/** lat. */
	@Column
	private Double lat;
}
