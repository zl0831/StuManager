package zl.app.controller.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zl.app.controller.base.BaseController;
import zl.app.mysql.services.user.sys_userService;
import zl.app.utility.CString;
import zl.app.utility.ReturnResult;

@RestController
@RequestMapping("/login")
public class loginController extends BaseController {

	@Autowired
	private sys_userService _sys_userService;


	@RequestMapping("/CheckLogin")
	public ReturnResult CheckLogin(HttpServletRequest request) throws Exception {

		var result = new ReturnResult();

		String username = super.getParams(request, "username");
		String userpwd = super.getParams(request, "password");

		if (CString.IsNullOrEmpty(username)) {
			result.setIsOk(false);
			result.setMessage("用户名不能为空!");
			return result;
		}
		if (CString.IsNullOrEmpty(userpwd)) {
			result.setIsOk(false);
			result.setMessage("密码不能为空!");
			return result;
		}

		var user = _sys_userService.getModelBystrWhere(String.format("user_name='%s'", username));
		if (user != null) {		
			//单机个人用没必要对密码加密
			//userpwd = CryptTools.MD5Encode(userpwd);

			if (user.getUser_pwd().equals(userpwd)) {
				result.setIsOk(true);
				result.setMessage("查询成功!");
				result.setData(user);

				super.setSessionAdmin(request, user);// 保存验证通过的用户信息

			} else {
				result.setIsOk(false);
				result.setMessage("密码错误!");
			}

		} else {
			result.setIsOk(false);
			result.setMessage("用户名不存在!");
		}

		return result;
	}

	@RequestMapping("/CheckIsLogin")
	public ReturnResult CheckIsLogin(HttpServletRequest request) throws Exception {

		var result = new ReturnResult();

		Object user = super.getSessionAdmin(request);
		if (user == null) {
			result.setIsOk(false);
			result.setMessage("登录状态已经失效");
		} else {
			result.setIsOk(true);
			result.setMessage("登录成功");
		}

		return result;
	}


	@RequestMapping("/SignOut")
	public void SignOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.clearAdminSession(request);
		response.sendRedirect("/login.html");
	}



}
