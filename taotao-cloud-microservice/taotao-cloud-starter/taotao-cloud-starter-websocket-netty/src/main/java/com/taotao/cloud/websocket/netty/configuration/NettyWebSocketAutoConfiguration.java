package com.taotao.cloud.websocket.netty.configuration;

import com.taotao.cloud.common.constant.StarterName;
import com.taotao.cloud.common.utils.log.LogUtils;
import com.taotao.cloud.websocket.netty.properties.NettyWebsocketProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.yeauty.annotation.EnableWebSocket;

/**
 * 网状汽车配置网络套接字
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-05-20 17:39:22
 */
@AutoConfiguration
@EnableWebSocket
@EnableConfigurationProperties({NettyWebsocketProperties.class})
@ConditionalOnProperty(prefix = NettyWebsocketProperties.PREFIX, name = "enabled", havingValue = "true")
public class NettyWebSocketAutoConfiguration implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		LogUtils.started(NettyWebSocketAutoConfiguration.class,
			StarterName.WEBSOCKET_NETTY_STARTER);
	}

	// @Bean
	// public ServerEndpointExporter serverEndpointExporter() {
	// 	return new ServerEndpointExporter();
	// }
}
