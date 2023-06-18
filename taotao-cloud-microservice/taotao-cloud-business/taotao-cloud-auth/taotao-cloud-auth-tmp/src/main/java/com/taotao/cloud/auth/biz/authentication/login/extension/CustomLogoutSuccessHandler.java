package com.taotao.cloud.auth.biz.authentication.login.extension;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


/**
 * 登出处理器
 *
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

//    private final TokenStore tokenStore;
//    private final LoginRecordService loginRecordService;
//    private final SecurityProperties securityProperties;
//    private final UserAccountService userAccountService;
//    private ApplicationContext applicationContext;
//
//    public CustomLogoutSuccessHandler(TokenStore tokenStore,
//                                      LoginRecordService loginRecordService,
//                                      SecurityProperties securityProperties,
//                                      UserAccountService userAccountService) {
//        this.tokenStore = tokenStore;
//        this.loginRecordService = loginRecordService;
//        this.securityProperties = securityProperties;
//        this.userAccountService = userAccountService;
//    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
//        String token = TokenUtils.getToken(request);
//
//        // 退出地址
//        String logoutUrl = getLogoutUrl(request, token);
//
//        LOGGER.debug("logout info, token={}, logoutUrl={}", token, logoutUrl);
//
//        if (authentication == null && token != null) {
//            authentication = tokenStore.readAuthentication(token);
//        }
//        if (authentication == null) {
//            LOGGER.warn("logout user not found. token={}", token);
//            response.sendRedirect(logoutUrl);
//            return;
//        }
//
//        // 查询登出用户
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof CustomUserDetails) {
//            CustomUserDetails details = (CustomUserDetails) principal;
//            User logoutUser = userAccountService.findLoginUser(details.getUserId());
//            loginRecordService.saveLocalLoginUser(logoutUser);
//        }
//
//        // 发布退出登录事件
//        LogoutEvent logoutEvent = new LogoutEvent(request, response, authentication);
//        applicationContext.publishEvent(logoutEvent);
//
//        response.sendRedirect(logoutUrl);
    }

//    @Nonnull
//    protected String getLogoutUrl(HttpServletRequest request, String token) {
//        String logoutUrl = request.getParameter(OAuth2Utils.REDIRECT_URI);
//        if (StringUtils.isBlank(logoutUrl) && token != null) {
//            logoutUrl = loginRecordService.getLogoutUrl(token);
//            if (logoutUrl != null) {
//                loginRecordService.removeLogoutUrl(token);
//            }
//        }
//        if (StringUtils.isBlank(logoutUrl)) {
//            String referer = request.getHeader("Referer");
//            if (referer != null) {
//                logoutUrl = referer;
//            } else {
//                logoutUrl = securityProperties.getLogin().getSuccessUrl();
//            }
//        }
//        return logoutUrl;
//    }
//
//    protected TokenStore getTokenStore() {
//        return tokenStore;
//    }
//
//    protected LoginRecordService getLoginRecordService() {
//        return loginRecordService;
//    }
//
//    protected SecurityProperties getSecurityProperties() {
//        return securityProperties;
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
}
