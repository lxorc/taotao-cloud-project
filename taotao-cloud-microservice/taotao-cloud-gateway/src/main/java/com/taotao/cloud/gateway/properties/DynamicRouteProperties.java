/*
 * Copyright 2002-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.gateway.properties;

import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 动态路由配置
 *
 * @author shuigedeng
 * @version 1.0.0
 * @since 2020/5/2 11:15
 * <p>
 * Data Id：scg-routes Group：SCG_GATEWAY
 * <p>
 * [{ "id": "consumer-router", "order": 0, "predicates": [{ "args": { "pattern": "/consume/**" },
 * "name": "Path" }], "uri": "lb://nacos-consumer" },{ "id": "provider-router", "order": 2,
 * "predicates": [{ "args": { "pattern": "/provide/**" }, "name": "Path" }], "uri":
 * "lb://nacos-provider" }]
 */
@RefreshScope
@ConfigurationProperties(prefix = DynamicRouteProperties.PREFIX)
public class DynamicRouteProperties {

	public static final String PREFIX = "taotao.cloud.gateway.dynamic.route";

	/**
	 * 是否开启
	 */
	private Boolean enabled = false;

	/**
	 * 类型
	 */
	private String type = "nacos";

	private String dataId = "";

	private String groupId = "";

	public DynamicRouteProperties() {
	}

	public DynamicRouteProperties(Boolean enabled, String type, String dataId, String groupId) {
		this.enabled = enabled;
		this.type = type;
		this.dataId = dataId;
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "DynamicRouteProperties{" +
			"enabled=" + enabled +
			", type='" + type + '\'' +
			", dataId='" + dataId + '\'' +
			", groupId='" + groupId + '\'' +
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
		DynamicRouteProperties that = (DynamicRouteProperties) o;
		return Objects.equals(enabled, that.enabled) && Objects.equals(type,
			that.type) && Objects.equals(dataId, that.dataId) && Objects.equals(
			groupId, that.groupId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(enabled, type, dataId, groupId);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
