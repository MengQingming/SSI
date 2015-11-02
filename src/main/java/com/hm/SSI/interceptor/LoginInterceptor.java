package com.hm.SSI.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;



/**
 * 
 * @ClassName: LoginInterceptor 
 * @Description: 登录拦截
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	private static Logger log = Logger.getLogger(LoginInterceptor.class);
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object arg2) throws Exception {
		String uri = req.getRequestURI();
/*//		StringBuffer url = req.getRequestURL();
		if(!uri.equals("/WMS/services/user/login")){
//		if(uri.indexOf("/user/login") == -1){
			log.info("用户拦截器被触发");
//			System.out.println("======用户拦截器被触发======");
			User user = (User) req.getSession().getAttribute("user");
			if(user == null) {
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
				log.info("未登陆或session过期，被拦截");
//				System.out.println("=====未登陆或session过期，被拦截=====");
				return false;
			}
		}*/
		return true;
	}

}
