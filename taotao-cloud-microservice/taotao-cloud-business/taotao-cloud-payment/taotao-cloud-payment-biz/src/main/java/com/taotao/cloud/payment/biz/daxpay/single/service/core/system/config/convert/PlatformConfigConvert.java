package com.taotao.cloud.payment.biz.daxpay.single.service.core.system.config.convert;

import com.taotao.cloud.payment.biz.daxpay.single.service.core.system.config.entity.PlatformConfig;
import com.taotao.cloud.payment.biz.daxpay.single.service.dto.system.config.PlatformConfigDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author xxm
 * @since 2024/1/2
 */
@Mapper
public interface PlatformConfigConvert {
    PlatformConfigConvert CONVERT = Mappers.getMapper(PlatformConfigConvert.class);

    PlatformConfigDto convert(PlatformConfig in);
}
