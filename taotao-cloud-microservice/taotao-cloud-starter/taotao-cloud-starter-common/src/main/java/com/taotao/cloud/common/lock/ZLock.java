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
package com.taotao.cloud.common.lock;

/**
 * 锁对象抽象
 *
 * @author shuigedeng
 * @version 1.0.0
 * @since 2020/5/2 09:12
 */
public class ZLock implements AutoCloseable {

	private final Object lock;

	private final DistributedLock locker;

	public ZLock(Object lock, DistributedLock locker) {
		this.lock = lock;
		this.locker = locker;
	}

	@Override
	public void close() throws Exception {
		locker.unlock(lock);
	}

	public Object getLock() {
		return lock;
	}
}
