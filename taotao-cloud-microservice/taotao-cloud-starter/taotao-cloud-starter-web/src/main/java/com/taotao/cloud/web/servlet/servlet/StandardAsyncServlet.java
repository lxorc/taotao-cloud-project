package com.taotao.cloud.web.servlet.servlet;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 标准异步servlet
 *
 * @author shuigedeng
 * @version 2022.09
 * @since 2022-10-27 10:17:33
 */
// asyncSupported 表示本Servelt是否支持异步
@WebServlet(urlPatterns = "/standardAsyncServlet", asyncSupported = true, description = "异步servlet")
public class StandardAsyncServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		AsyncContext asyncContext = req.startAsync();
		asyncContext.start(() -> {
			try {
				resp.getWriter().write("async : hello world!");
				// 异步操作时候最终这里要进行结束，在这之前可以多次调用request和response：
				// eg: asyncContext.getResponse().getWriter().write("hello");
				asyncContext.complete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		// resp.getWriter().write("Hello World!");
	}
}
