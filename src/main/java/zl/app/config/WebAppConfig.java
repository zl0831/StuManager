package zl.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** 
 * 
 * 注册拦截器 
 * Created by SYSTEM on 2017/8/16. 
 */  
@Component
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
	
	
    @Override  
    public void addInterceptors(InterceptorRegistry registry) {  
        //注册自定义拦截器，添加拦截路径和排除拦截路径  
    	registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
    	
    		.excludePathPatterns("/error","/html/**","/login.html","/register.html");
    	 
    }  
    
 

} 
