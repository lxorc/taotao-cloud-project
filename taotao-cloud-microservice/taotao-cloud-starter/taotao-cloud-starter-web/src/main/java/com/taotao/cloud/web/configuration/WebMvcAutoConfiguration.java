/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
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
package com.taotao.cloud.web.configuration;


import static com.taotao.cloud.web.filter.XssFilter.IGNORE_PARAM_VALUE;
import static com.taotao.cloud.web.filter.XssFilter.IGNORE_PATH;

import cn.hutool.core.collection.CollUtil;
import com.taotao.cloud.common.constant.StarterName;
import com.taotao.cloud.common.model.SecurityUser;
import com.taotao.cloud.common.utils.common.SecurityUtil;
import com.taotao.cloud.common.utils.log.LogUtil;
import com.taotao.cloud.redis.repository.RedisRepository;
import com.taotao.cloud.web.annotation.EnableUser;
import com.taotao.cloud.web.filter.TenantFilter;
import com.taotao.cloud.web.filter.TraceFilter;
import com.taotao.cloud.web.filter.VersionFilter;
import com.taotao.cloud.web.filter.WebContextFilter;
import com.taotao.cloud.web.filter.XssFilter;
import com.taotao.cloud.web.interceptor.DoubtApiInterceptor;
import com.taotao.cloud.web.interceptor.HeaderThreadLocalInterceptor;
import com.taotao.cloud.web.interceptor.PrometheusMetricsInterceptor;
import com.taotao.cloud.web.listener.RequestMappingScanListener;
import com.taotao.cloud.web.properties.FilterProperties;
import com.taotao.cloud.web.properties.InterceptorProperties;
import com.taotao.cloud.web.properties.XssProperties;
import com.taotao.cloud.web.sensitive.desensitize.DesensitizeProperties;
import com.taotao.cloud.web.validation.converter.IntegerToEnumConverterFactory;
import com.taotao.cloud.web.validation.converter.String2DateConverter;
import com.taotao.cloud.web.validation.converter.String2LocalDateConverter;
import com.taotao.cloud.web.validation.converter.String2LocalDateTimeConverter;
import com.taotao.cloud.web.validation.converter.String2LocalTimeConverter;
import com.taotao.cloud.web.validation.converter.StringToEnumConverterFactory;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import okhttp3.OkHttpClient;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义mvc配置
 *
 * @author shuigedeng
 * @version 2021.9
 * @since 2021-09-02 21:30:20
 */
@AutoConfiguration
@EnableConfigurationProperties({
	FilterProperties.class,
	InterceptorProperties.class,
	DesensitizeProperties.class
})
public class WebMvcAutoConfiguration implements WebMvcConfigurer, InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		LogUtil.started(WebMvcAutoConfiguration.class, StarterName.WEB_STARTER);
	}

	/**
	 * redisRepository
	 */
	@Autowired
	private RedisRepository redisRepository;
	/**
	 * filterProperties
	 */
	@Autowired
	private FilterProperties filterProperties;
	@Autowired
	private InterceptorProperties interceptorProperties;
	/**
	 * xssProperties
	 */
	@Autowired
	private XssProperties xssProperties;
	/**
	 * requestCounter
	 */
	@Autowired
	private Counter requestCounter;
	/**
	 * requestLatency
	 */
	@Autowired
	private Summary requestLatency;
	/**
	 * inprogressRequests
	 */
	@Autowired
	private Gauge inprogressRequests;
	/**
	 * requestLatencyHistogram
	 */
	@Autowired
	private Histogram requestLatencyHistogram;

	//路径匹配规则
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		//// 设置是否模糊匹配，默认真。例如/user是否匹配/user.*。如果真，也就是说"/user.html"的请求会被"/user"的Controller所拦截。
		//configurer.setUseSuffixPatternMatch(false);
		//// 设置是否自动后缀模式匹配，默认真。如/user是否匹配/user/。如果真，也就是说, "/user"和"/user/"都会匹配到"/user"的Controller。
		//configurer.setUseTrailingSlashMatch(true);
	}

	//内容协商策略
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		//// 自定义策略
		//configurer.favorPathExtension(true)// 是否通过请求Url的扩展名来决定mediaType，默认true
		//	.ignoreAcceptHeader(true)// 不检查Accept请求头
		//	.parameterName("mediaType")
		//	.defaultContentType(MediaType.TEXT_HTML)// 设置默认的MediaType
		//	.mediaType("html", MediaType.TEXT_HTML)// 请求以.html结尾的会被当成MediaType.TEXT_HTML
		//	.mediaType("json", MediaType.APPLICATION_JSON)// 请求以.json结尾的会被当成MediaType.APPLICATION_JSON
		//	.mediaType("xml", MediaType.APPLICATION_ATOM_XML);// 请求以.xml结尾的会被当成MediaType.APPLICATION_ATOM_XML
		//
		//// 或者下面这种写法
		//Map<String, MediaType> map = new HashMap<>();
		//map.put("html", MediaType.TEXT_HTML);
		//map.put("json", MediaType.APPLICATION_JSON);
		//map.put("xml", MediaType.APPLICATION_ATOM_XML);
		//// 指定基于参数的解析类型
		//ParameterContentNegotiationStrategy negotiationStrategy = new ParameterContentNegotiationStrategy(map);
		//// 指定基于请求头的解析
		//configurer.strategies(Arrays.asList(negotiationStrategy));
	}

	//异步调用支持
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		// 注册callable拦截器
		configurer.registerCallableInterceptors(new TimeoutCallableProcessingInterceptor());
		// 注册deferredResult拦截器
		configurer.registerDeferredResultInterceptors();
		// 异步请求超时时间
		configurer.setDefaultTimeout(1000);
		// 设定异步请求线程池callable等, spring默认线程不可重用
		configurer.setTaskExecutor(new ThreadPoolTaskExecutor());
	}

	//参数解析器
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new LoginUserArgumentResolver());
	}

	//返回值处理器
	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
	}

	//拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (interceptorProperties.getHeader()) {
			registry.addInterceptor(new HeaderThreadLocalInterceptor())
				.addPathPatterns("/**");
		}

		if (interceptorProperties.getPrometheus()) {
			registry.addInterceptor(new PrometheusMetricsInterceptor(
					requestCounter,
					requestLatency,
					inprogressRequests,
					requestLatencyHistogram))
				.addPathPatterns("/**");
		}

		if (interceptorProperties.getDoubtApi()) {
			registry.addInterceptor(new DoubtApiInterceptor(interceptorProperties))
				.addPathPatterns("/**")
				.excludePathPatterns("/actuator/**");
		}

	}

	//信息转化器
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

	}

	//信息转化器扩展
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

	}

	//异常处理器扩展
	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
	}

	//格式化器和转换器
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new IntegerToEnumConverterFactory());
		registry.addConverterFactory(new StringToEnumConverterFactory());

		registry.addConverter(new String2DateConverter());
		registry.addConverter(new String2LocalDateConverter());
		registry.addConverter(new String2LocalDateTimeConverter());
		registry.addConverter(new String2LocalTimeConverter());
	}

	//静态资源处理器
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/**")
			.addResourceLocations("classpath:/static/");
	}

	//视图控制器
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry
			.addViewController("/index")
			.setViewName("index");
	}

	//视图解析器
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		//registry.viewResolver(internalResourceViewResolver());
	}
	//@Bean
	//public InternalResourceViewResolver internalResourceViewResolver() {
	//	InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
	//	//请求视图文件的前缀地址
	//	internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
	//	//请求视图文件的后缀
	//	internalResourceViewResolver.setSuffix(".jsp");
	//	return internalResourceViewResolver;
	//}

	@Bean
	@LoadBalanced
	public OkHttpClient.Builder builder() {
		return new OkHttpClient.Builder();
	}

	@Bean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
			.configure()
			// 快速失败模式
			.failFast(true)
			.buildValidatorFactory();
		return validatorFactory.getValidator();
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Bean
	public RequestMappingScanListener requestMappingScanListener() {
		return new RequestMappingScanListener(redisRepository);
	}

	@Bean
	@ConditionalOnProperty(prefix = FilterProperties.PREFIX, name = "version", havingValue = "true")
	public FilterRegistrationBean<VersionFilter> lbIsolationFilter() {
		FilterRegistrationBean<VersionFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new VersionFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setName(VersionFilter.class.getName());
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 6);
		return registrationBean;
	}

	@Bean
	@ConditionalOnProperty(prefix = FilterProperties.PREFIX, name = "tenant", havingValue = "true")
	public FilterRegistrationBean<TenantFilter> tenantFilter() {
		FilterRegistrationBean<TenantFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new TenantFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setName(TenantFilter.class.getName());
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 5);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<TraceFilter> traceFilter() {
		FilterRegistrationBean<TraceFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new TraceFilter(filterProperties));
		registrationBean.addUrlPatterns("/*");
		registrationBean.setName(TraceFilter.class.getName());
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 4);
		return registrationBean;
	}

	@Bean
	@ConditionalOnProperty(prefix = FilterProperties.PREFIX, name = "webContext", havingValue = "true")
	public FilterRegistrationBean<WebContextFilter> webContextFilter() {
		FilterRegistrationBean<WebContextFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new WebContextFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setName(WebContextFilter.class.getName());
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 3);
		return registrationBean;
	}

	/**
	 * 配置跨站攻击过滤器
	 */
	@Bean
	@ConditionalOnProperty(prefix = XssProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
	public FilterRegistrationBean<XssFilter> filterRegistrationBean() {
		FilterRegistrationBean<XssFilter> filterRegistration = new FilterRegistrationBean<>();
		filterRegistration.setFilter(new XssFilter());
		filterRegistration.setEnabled(xssProperties.getEnabled());
		filterRegistration.addUrlPatterns(xssProperties.getPatterns().toArray(new String[0]));
		filterRegistration.setOrder(xssProperties.getOrder());

		Map<String, String> initParameters = new HashMap<>(4);
		initParameters.put(IGNORE_PATH, CollUtil.join(xssProperties.getIgnorePaths(), ","));
		initParameters.put(IGNORE_PARAM_VALUE,
			CollUtil.join(xssProperties.getIgnoreParamValues(), ","));
		filterRegistration.setInitParameters(initParameters);
		return filterRegistration;
	}

	/**
	 * 通过header里的token获取用户信息
	 *
	 * @author shuigedeng
	 * @version 2021.9
	 * @see <a
	 * href="https://my.oschina.net/u/4149877/blog/3143391/print">https://my.oschina.net/u/4149877/blog/3143391/print</a>
	 * @see <a
	 * href="https://blog.csdn.net/aiyaya_/article/details/79221733">https://blog.csdn.net/aiyaya_/article/details/79221733</a>
	 * @since 2021-09-02 21:32:45
	 */
	public static class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

		public LoginUserArgumentResolver() {
		}

		@Override
		public boolean supportsParameter(MethodParameter parameter) {
			boolean isHasEnableUserAnn = parameter.hasParameterAnnotation(EnableUser.class);
			boolean isHasLoginUserParameter = parameter.getParameterType()
				.isAssignableFrom(SecurityUser.class);
			return isHasEnableUserAnn && isHasLoginUserParameter;
		}

		@Override
		public Object resolveArgument(MethodParameter methodParameter,
			ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest,
			WebDataBinderFactory webDataBinderFactory) throws Exception {
			EnableUser user = methodParameter.getParameterAnnotation(EnableUser.class);
			boolean value = user.value();
			HttpServletRequest request = nativeWebRequest.getNativeRequest(
				HttpServletRequest.class);
			SecurityUser loginUser = SecurityUtil.getCurrentUser();

			//根据value状态获取更多用户信息，待实现
			return loginUser;
		}
	}
}
