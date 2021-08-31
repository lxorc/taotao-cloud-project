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
package com.taotao.cloud.canal.abstracts.option.content;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.taotao.cloud.canal.abstracts.option.AbstractDBOption;

/**
 * 新增数据
 * @version 1.0.0
 * @author shuigedeng
 * @since 2021/8/30 21:52
 */
public abstract class InsertOption extends AbstractDBOption {
	
	@Override
	protected void setEventType() {
		this.eventType = CanalEntry.EventType.INSERT;
	}
}
