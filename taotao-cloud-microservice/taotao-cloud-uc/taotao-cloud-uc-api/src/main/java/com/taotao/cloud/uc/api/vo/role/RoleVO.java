package com.taotao.cloud.uc.api.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 用户VO
 *
 * @author shuigedeng
 * @since 2020/5/14 10:44
 */
@Schema(name = "RoleVO", description = "角色VO")
public class RoleVO implements Serializable {

	private static final long serialVersionUID = 5126530068827085130L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "角色名称")
	private String name;

	@Schema(description = "角色code")
	private String code;

	@Schema(description = "备注")
	private String remark;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "最后修改时间")
	private LocalDateTime lastModifiedTime;

	public RoleVO() {
	}

	public RoleVO(Long id, String name, String code, String remark, LocalDateTime createTime,
		LocalDateTime lastModifiedTime) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.remark = remark;
		this.createTime = createTime;
		this.lastModifiedTime = lastModifiedTime;
	}


	@Override
	public String toString() {
		return "RoleVO{" +
			"id=" + id +
			", name='" + name + '\'' +
			", code='" + code + '\'' +
			", remark='" + remark + '\'' +
			", createTime=" + createTime +
			", lastModifiedTime=" + lastModifiedTime +
			'}';
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		RoleVO roleVO = (RoleVO) o;
		return Objects.equals(id, roleVO.id) && Objects.equals(name, roleVO.name)
			&& Objects.equals(code, roleVO.code) && Objects.equals(remark,
			roleVO.remark) && Objects.equals(createTime, roleVO.createTime)
			&& Objects.equals(lastModifiedTime, roleVO.lastModifiedTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, code, remark, createTime, lastModifiedTime);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public static RoleVOBuilder builder() {
		return new RoleVOBuilder();
	}


	public static final class RoleVOBuilder {

		private Long id;
		private String name;
		private String code;
		private String remark;
		private LocalDateTime createTime;
		private LocalDateTime lastModifiedTime;

		private RoleVOBuilder() {
		}

		public static RoleVOBuilder aRoleVO() {
			return new RoleVOBuilder();
		}

		public RoleVOBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public RoleVOBuilder name(String name) {
			this.name = name;
			return this;
		}

		public RoleVOBuilder code(String code) {
			this.code = code;
			return this;
		}

		public RoleVOBuilder remark(String remark) {
			this.remark = remark;
			return this;
		}

		public RoleVOBuilder createTime(LocalDateTime createTime) {
			this.createTime = createTime;
			return this;
		}

		public RoleVOBuilder lastModifiedTime(LocalDateTime lastModifiedTime) {
			this.lastModifiedTime = lastModifiedTime;
			return this;
		}

		public RoleVO build() {
			RoleVO roleVO = new RoleVO();
			roleVO.setId(id);
			roleVO.setName(name);
			roleVO.setCode(code);
			roleVO.setRemark(remark);
			roleVO.setCreateTime(createTime);
			roleVO.setLastModifiedTime(lastModifiedTime);
			return roleVO;
		}
	}
}
