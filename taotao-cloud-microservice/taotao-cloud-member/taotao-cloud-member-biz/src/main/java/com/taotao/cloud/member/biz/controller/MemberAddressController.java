package com.taotao.cloud.member.biz.controller;

import com.taotao.cloud.member.biz.service.IMemberAddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员收货地址管理API
 *
 * @author shuigedeng
 * @since 2020-10-16 16:23:49
 * @since 1.0
 */
@Validated
@RestController
@RequestMapping("/member/address")
@Tag(name = "会员收货地址管理API", description = "会员收货地址管理API")
public class MemberAddressController {
    private final IMemberAddressService memberAddressService;

	public MemberAddressController(
		IMemberAddressService memberAddressService) {
		this.memberAddressService = memberAddressService;
	}
}
