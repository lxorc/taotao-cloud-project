
package com.taotao.cloud.member.biz.controller.buyer.connect;


import com.taotao.cloud.common.constant.CommonConstant;
import com.taotao.cloud.common.model.Result;
import com.taotao.cloud.logger.annotation.RequestLogger;
import com.taotao.cloud.member.biz.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 买家端,web联合登录
 */
@Validated
@RestController
@Tag(name = "买家端-web联合登录API", description = "买家端-web联合登录API")
@RequestMapping("/member/buyer/passport/connect/connect")
public class ConnectWebBindController {

	@Autowired
	private ConnectService connectService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ConnectUtil connectUtil;

	@Operation(summary = "WEB信任登录授权", description = "WEB信任登录授权", method = CommonConstant.GET)
	@RequestLogger(description = "WEB信任登录授权")
	@PreAuthorize("@el.check('admin','timing:list')")
	@GetMapping("/login/web/{type}")
	public Result<String> webAuthorize(
		@Parameter(allowableValues = "QQ,WECHAT,WECHAT_PC", name = "type", description = "登录方式:QQ,微信,微信_PC") @PathVariable String type,
		HttpServletResponse response) throws IOException {
		AuthRequest authRequest = connectUtil.getAuthRequest(type);
		String authorizeUrl = authRequest.authorize(UuidUtils.getUUID());
		response.sendRedirect(authorizeUrl);
		return Result.success(authorizeUrl);
	}

	@Operation(summary = "信任登录统一回调地址", description = "信任登录统一回调地址", method = CommonConstant.GET)
	@RequestLogger(description = "信任登录统一回调地址")
	@PreAuthorize("@el.check('admin','timing:list')")
	@GetMapping("/callback/{type}")
	public void callBack(@PathVariable String type, AuthCallback callback,
		HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws IOException {
		connectUtil.callback(type, callback, httpServletRequest, httpServletResponse);
	}

	@Operation(summary = "信任登录响应结果获取", description = "信任登录响应结果获取", method = CommonConstant.GET)
	@RequestLogger(description = "信任登录响应结果获取")
	@PreAuthorize("@el.check('admin','timing:list')")
	@GetMapping("/result")
	public Result<Object> callBackResult(String state) {
		if (state == null) {
			throw new ServiceException(ResultCode.USER_CONNECT_LOGIN_ERROR);
		}
		return connectUtil.getResult(state);
	}

	@Operation(summary = "WEB信任登录授权", description = "WEB信任登录授权", method = CommonConstant.GET)
	@RequestLogger(description = "WEB信任登录授权")
	@PreAuthorize("@el.check('admin','timing:list')")
	@GetMapping("/register/auto")
	public Result<Token> webAuthorize() {
		Token token = memberService.autoRegister();
		return Result.success(token);
	}

	@Operation(summary = "unionID登录", description = "unionID登录", method = CommonConstant.GET)
	@RequestLogger(description = "unionID登录")
	@PreAuthorize("@el.check('admin','timing:list')")
	//@ApiImplicitParams({
	//	@ApiImplicitParam(name = "openId", value = "openid", required = true, paramType = "query"),
	//	@ApiImplicitParam(name = "type", value = "联合类型", required = true,
	//		allowableValues = "WECHAT,QQ,ALIPAY,WEIBO,APPLE", paramType = "query"),
	//	@ApiImplicitParam(name = "uniAccessToken", value = "联合登陆返回的accessToken", required = true, paramType = "query")
	//})
	@GetMapping("/app/login")
	public Result<Token> unionLogin(ConnectAuthUser authUser,
		@RequestHeader("uuid") String uuid) {
		try {
			return Result.success(connectService.appLoginCallback(authUser, uuid));
		} catch (Exception e) {
			log.error("unionID登录错误", e);
		}
		return null;
	}

}
