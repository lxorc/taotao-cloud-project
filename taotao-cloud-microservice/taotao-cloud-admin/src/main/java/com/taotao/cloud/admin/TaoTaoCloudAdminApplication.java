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
package com.taotao.cloud.admin;

import cn.hutool.json.JSONObject;
import com.taotao.cloud.common.utils.JsonUtil;
import com.taotao.cloud.dingtalk.annatations.EnableTaoTaoCloudDingtalk;
import com.taotao.cloud.dingtalk.entity.DingerRequest;
import com.taotao.cloud.dingtalk.enums.MessageSubType;
import com.taotao.cloud.dingtalk.model.DingerSender;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import reactor.core.publisher.Mono;

/**
 * TaoTaoCloudAdminServerApplication
 *
 * @author shuigedeng
 * @version 1.0.0
 * @since 2020/11/26 下午7:55
 */
@EnableDiscoveryClient
@EnableAdminServer
@EnableTaoTaoCloudDingtalk
@SpringBootApplication
public class TaoTaoCloudAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaoTaoCloudAdminApplication.class, args);
	}

	@Bean
	public DingDingNotifier dingDingNotifier(InstanceRepository repository) {
		return new DingDingNotifier(repository);
	}

	public static class DingDingNotifier extends AbstractStatusChangeNotifier {

		private final String[] ignoreChanges = new String[]{"UNKNOWN:UP", "DOWN:UP", "OFFLINE:UP"};

		@Autowired
		private DingerSender sender;

		public DingDingNotifier(InstanceRepository repository) {
			super(repository);
		}

		@Override
		protected boolean shouldNotify(InstanceEvent event, Instance instance) {
			if (!(event instanceof InstanceStatusChangedEvent)) {
				return false;
			} else {
				InstanceStatusChangedEvent statusChange = (InstanceStatusChangedEvent) event;
				String from = this.getLastStatus(event.getInstance());
				String to = statusChange.getStatusInfo().getStatus();
				return Arrays.binarySearch(this.ignoreChanges, from + ":" + to) < 0
					&& Arrays.binarySearch(this.ignoreChanges, "*:" + to) < 0
					&& Arrays.binarySearch(this.ignoreChanges, from + ":*") < 0;
			}
		}

		@Override
		protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
			String serviceName = instance.getRegistration().getName();
			String serviceUrl = instance.getRegistration().getServiceUrl();

			StringBuilder str = new StringBuilder();
			str.append("taotao微服务监控 \n");
			str.append("[服务名] : ").append(serviceName).append("\n");
			str.append("[服务ip]: ").append(serviceUrl).append("\n");

			return Mono.fromRunnable(() -> {
				if (event instanceof InstanceStatusChangedEvent) {
					String status = ((InstanceStatusChangedEvent) event).getStatusInfo()
						.getStatus();
					switch (status) {
						// 健康检查没通过
						case "DOWN" -> str.append("[服务状态]: ").append(status).append("(")
							.append("健康检查没通过").append(")").append("\n");

						// 服务离线
						case "OFFLINE" -> str.append("[服务状态]: ").append(status).append("(")
							.append("服务离线").append(")").append("\n");

						//服务上线
						case "UP" -> str.append("[服务状态]: ").append(status).append("(")
							.append("服务上线").append(")").append("\n");

						// 服务未知异常
						case "UNKNOWN" -> str.append("[服务状态]: ").append(status).append("(")
							.append("服务未知异常").append(")").append("\n");

						default -> {
						}

					}

					Map<String, Object> details = ((InstanceStatusChangedEvent) event).getStatusInfo()
						.getDetails();
					str.append("[详情]: ").append(JsonUtil.toJSONString(details));

					sender.send(
						MessageSubType.TEXT,
						DingerRequest.request(str.toString()));
				}
			});
		}


		@Configuration
		public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {

			private final String adminContextPath;

			public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
				this.adminContextPath = adminServerProperties.getContextPath();
			}

			@Override
			protected void configure(HttpSecurity http) throws Exception {
				SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
				successHandler.setTargetUrlParameter("redirectTo");
				successHandler.setDefaultTargetUrl(adminContextPath + "/");
				http.authorizeRequests()
					//1.配置所有静态资源和登录页可以公开访问
					.antMatchers(adminContextPath + "/assets/**").permitAll()
					.antMatchers(adminContextPath + "/login").permitAll()
					.antMatchers("/actuator/**").permitAll()
					.antMatchers("/actuator").permitAll()
					.antMatchers("/instances").permitAll()
					.antMatchers("/instances/**").permitAll()
					.anyRequest().authenticated()
					.and()
					//2.配置登录和登出路径
					.formLogin().loginPage(adminContextPath + "/login")
					.successHandler(successHandler)
					.and()
					.logout().logoutUrl(adminContextPath + "/logout").and()
					//3.开启http basic支持，admin-client注册时需要使用
					.httpBasic().and()
					.csrf()
					//4.开启基于cookie的csrf保护
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
					//5.忽略这些路径的csrf保护以便admin-client注册
					.ignoringAntMatchers(
						adminContextPath + "/instances",
						adminContextPath + "/actuator/**"
					);
			}
		}

	}
}
