package zl.app.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

   
   
    /**
     * 进入controller层之前拦截请求
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
    	
    	 
    	String key = zl.app.controller.base.BaseController.Admin_Session_Key;

    	Object object = httpServletRequest.getSession().getAttribute(key);

    	System.out.println("sessionid==" + httpServletRequest.getSession().getId());
    	
    	String url = httpServletRequest.getRequestURI();
//    	if(object==null)
//    	{
//    	   System.out.println(url);
//           httpServletResponse.sendRedirect("/login.html");
//    	   return false;
//    	}
//    	
    	
    	//过滤掉企业用户维护和看板
    	if( url.indexOf("login/") > 0 ) {
    		return true;
    	} else {
    		String localUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + "/";
    		if(object==null) {
    			System.out.println("拦截页面："+httpServletRequest.getRequestURL());
    			httpServletResponse.sendRedirect(localUrl + "login.html");
    			return false;
        	} else {
        		return true;
        	}
    	}
    	
    	 
    	
    }
    
    
    
    
}