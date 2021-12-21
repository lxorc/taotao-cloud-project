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
package com.taotao.cloud.oauth2.biz.service;

import com.taotao.cloud.common.constant.RedisConstant;
import com.taotao.cloud.common.exception.BaseException;
import com.taotao.cloud.redis.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SmsService
 *
 * @author shuigedeng
 * @version 1.0.0
 * @since 2021/12/21 20:52
 */
@Service
public class SmsService {

	@Autowired
	private com.taotao.cloud.sms.service.SmsService smsService;
	@Autowired
	private RedisRepository redisRepository;

	public boolean sendSms(String phoneNumber) {
		String code = smsService.sendSms(
			"",
			"",
			smsService.sendRandCode(6),
			""
		);

		// 添加发送日志

		redisRepository
			.setExpire(RedisConstant.SMS_KEY_PREFIX + phoneNumber, code.toLowerCase(), 120);

		return true;
	}

	public boolean checkSms(String code, String phoneNumber) {
		String key = RedisConstant.SMS_KEY_PREFIX + phoneNumber;
		if (!redisRepository.exists(key)) {
			throw new BaseException("手机验证码不合法");
		}

		Object captcha = redisRepository.get(key);
		if (captcha == null) {
			throw new BaseException("手机验证码已失效");
		}
		if (!code.toLowerCase().equals(captcha)) {
			throw new BaseException("手机验证码错误");
		}

		return true;
	}
}
