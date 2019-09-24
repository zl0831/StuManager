package zl.app.controller.base;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zl.app.annotation.CheckNULLAnnotation;
import zl.app.utility.CString;
import zl.app.utility.ReturnResult;

public class BaseController {		
	
	public final static String Paramter_Session_Key = "/Root/Paramter";
	public final static String Admin_Session_Key = "/Root/Admin";
	
	// 编码格式
	protected final String ENCODING_UTF8 = "UTF-8";
	

	public Object getSessionAdmin(HttpServletRequest request) {
		return request.getSession().getAttribute(Admin_Session_Key);
	}
	
	public void setSessionAdmin(HttpServletRequest request, Object data) {
		request.getSession().setAttribute(Admin_Session_Key, data);
	}
	
	public void clearAdminSession(HttpServletRequest request) {
		request.getSession().removeAttribute(Admin_Session_Key);
	}
	
	
	
	
	/**
	 * 设置Request和Response编码格式
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	protected void setEncoding(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding(ENCODING_UTF8);
			response.setCharacterEncoding(ENCODING_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* 获取URL参数 */
	protected String getParams(HttpServletRequest request,String key) {
		//setEncoding(request,response);
		
		String value = request.getParameter(key);
		return value;
	}
	/*
	 * 检查属生不能为空
	 */
	protected ReturnResult CheckNULL(Object t) throws Exception {
		var Data_Result = new ReturnResult();
		
		try {
			Method[] m = t.getClass().getMethods();
			if (null == m || m.length <= 0) {
				throw new Exception("Error Input Object! No Method.");
			}
			
			for (Method i : m) {
				if(null != i.getAnnotation(CheckNULLAnnotation.CheckNULL.class)){
					System.out.println("验证属性:"+i.getAnnotation(CheckNULLAnnotation.CheckNULL.class).value());
					if(CString.IsNullOrEmpty(i.invoke(t, null).toString())) {
						Data_Result.setIsOk(false);
						Data_Result.setMessage(String.format("%s不能为空!", i.getAnnotation(CheckNULLAnnotation.CheckNULL.class).value()));
						return Data_Result;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Data_Result.setIsOk(true);
		return Data_Result;
	}
	

	
}
