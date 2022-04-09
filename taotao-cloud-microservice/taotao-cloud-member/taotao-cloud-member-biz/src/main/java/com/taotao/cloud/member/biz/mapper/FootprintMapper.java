package com.taotao.cloud.member.biz.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.taotao.cloud.member.biz.entity.MemberBrowse;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 浏览历史数据处理层
 */
public interface FootprintMapper extends BaseMapper<MemberBrowse> {

	/**
	 * 获取用户足迹的SkuId分页
	 *
	 * @param page         分页
	 * @param queryWrapper 查询条件
	 * @return 用户足迹的SkuId分页
	 */
	@Select("""
		select sku_id
		from li_foot_print
		${ew.customSqlSegment}
		""")
	List<String> footprintSkuIdList(IPage<String> page,
		@Param(Constants.WRAPPER) Wrapper<MemberBrowse> queryWrapper);

	/**
	 * 删除超过100条后的记录
	 *
	 * @param memberId 会员ID
	 */
	@Delete("""
		DELETE FROM li_foot_print WHERE
		       (SELECT COUNT(b.id) FROM
		            ( SELECT * FROM li_foot_print WHERE member_id = #{memberId} ) b) >100
		            AND id =(SELECT a.id FROM ( SELECT * FROM li_foot_print WHERE member_id = #{memberId} ORDER BY create_time ASC LIMIT 1 ) AS a)
		""")
	void deleteLastFootPrint(Long memberId);

}
