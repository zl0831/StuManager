package zl.app.controller.user;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zl.app.controller.base.BaseController;
import zl.app.enums.YesOrNoEnum;
import zl.app.mysql.bean.user.sys_user;
import zl.app.mysql.services.user.sys_userService;
import zl.app.utility.CInteger;
import zl.app.utility.CString;
import zl.app.utility.ReturnResult;

@RestController
@RequestMapping("/sys_user")
public class sys_userController extends BaseController {

	@Autowired
	private sys_userService _sys_userService;

	
	/*
	 * 用户--根据id获取用户信息
	 */
	@RequestMapping("/GetUserById")
	public ReturnResult GetUserById(HttpServletRequest request) throws Exception {

		var result = new ReturnResult();

		String p_id = super.getParams(request, "id");
		if (CString.IsNullOrEmpty(p_id)) {
			result.setIsOk(false);
			result.setMessage("id不能为空!");
			return result;
		}

		Integer id = CInteger.tryParse(p_id);

		var data = _sys_userService.getModelByid(id);
		if (data != null) {

			result.setIsOk(true);
			result.setData(data);
			result.setMessage("成功");

		} else {

			result.setIsOk(false);
			result.setMessage("没有过查询到任何内容");
		}

		return result;

	}
	
	/*
	 * 获取所有用户信息，用于角色分配用户管理
	 */
	@RequestMapping("/GetUserList")
	public ReturnResult GetUserList(HttpServletRequest request) throws Exception {

		var result = new ReturnResult();

		var data_list = _sys_userService.getListBystrWhere("");
		if (data_list != null) {

			result.setIsOk(true);
			result.setData_list(data_list);
			result.setMessage("查询成功");

		} else {

			result.setIsOk(false);
			result.setMessage("没有查询到任何内容");
		}

		return result;

	}
	

	/*
	 * 用户--获取用户列表分页
	 */
	@RequestMapping("/GetUsersByPage")
	public ReturnResult GetUsersByPage(HttpServletRequest request) {
		var result = new ReturnResult();

		var p_pi = super.getParams(request, "pi");
		var p_ps = super.getParams(request, "ps");

		if (CString.IsNullOrEmpty(p_pi)) {
			result.setIsOk(false);
			result.setMessage("pi不能为空!");
			return result;
		}
		if (CString.IsNullOrEmpty(p_ps)) {
			result.setIsOk(false);
			result.setMessage("ps不能为空!");
			return result;
		}

		int pi = CInteger.tryParse(p_pi);
		int ps = CInteger.tryParse(p_ps);

		AtomicLong count = new AtomicLong();

		var list = _sys_userService.getUsersListByPage("", pi, ps, "id desc", count);
		if (list != null && list.size() > 0) {

			result.setData_list(list);

			result.setPageIndex(pi);
			result.setPageSize(ps);
			result.setTotalCount(count.get());
			result.setIsOk(true);
			result.setMessage("查询成功");

		} else {

			result.setTotalCount(count.get());
			result.setIsOk(false);
			result.setMessage("没有查询到任何信息");
		}

		return result;
	}

	@RequestMapping("/DelUser")
	public ReturnResult DelUser(HttpServletRequest request) {

		var result = new ReturnResult();

		String ids = super.getParams(request, "ids");

		if (CString.IsNullOrEmpty(ids)) {
			result.setIsOk(false);
			result.setMessage("id不能为空!");
			return result;
		}
		
		if(YesOrNoEnum.Yes.toString().equals(ids)) {
			result.setIsOk(false);
			result.setMessage("不能删除id为1的用户!");
			return result;
		}

		// id:1,2,3
		long success = _sys_userService.deletebyWhere(ids);
		if (success > 0) {

			result.setCode(success);
			result.setIsOk(true);
			result.setMessage("删除成功");

		} else {
			result.setIsOk(false);
			result.setMessage("删除失败");
		}

		return result;

	}

	@RequestMapping("/AddUser")
	public ReturnResult AddUser(HttpServletRequest request) throws Exception {

		var result = new ReturnResult();

		String user_no = super.getParams(request, "user_no");
		if (CString.IsNullOrEmpty(user_no)) {
			result.setIsOk(false);
			result.setMessage("用户编码不能为空!");
			return result;
		}
		
		String user_name = super.getParams(request, "user_name");
		if (CString.IsNullOrEmpty(user_name)) {
			result.setIsOk(false);
			result.setMessage("用户名不能为空!");
			return result;
		}

		String user_pwd = super.getParams(request, "user_pwd");
		if (CString.IsNullOrEmpty(user_pwd)) {
			result.setIsOk(false);
			result.setMessage("密码不能为空!");
			return result;
		}
		//user_pwd = CryptTools.MD5Encode(user_pwd);

		String mobile = super.getParams(request, "mobile");		
		String email = super.getParams(request, "email");
		String real_name = super.getParams(request, "real_name");
		

		String strWhere = String.format("user_name='%s'", user_name);
		var item = _sys_userService.getModelBystrWhere(strWhere);
		if (item != null) {

			result.setIsOk(false);
			result.setMessage("用户名已经存在!");

		} else {

			var data_item = new sys_user();

			data_item.setUser_no(user_no);
			data_item.setUser_name(user_name);
			data_item.setUser_pwd(user_pwd);
			data_item.setMobile(mobile);
			data_item.setEmail(email);
			data_item.setReal_name(real_name);

			long success = _sys_userService.insert(data_item);
			if (success > 0) {

				result.setIsOk(true);
				result.setMessage("插入成功");

			} else {

				result.setIsOk(false);
				result.setMessage("插入失败");

			}

		}

		return result;

	}

	@RequestMapping("/EditUser")
	public ReturnResult EditUser(HttpServletRequest request) throws Exception {

		var result = new ReturnResult();

		String mobile = super.getParams(request, "mobile");
		String email = super.getParams(request, "email");
		String real_name = super.getParams(request, "real_name");
		String id = super.getParams(request, "id");

		var data_item = new sys_user();

		data_item.setMobile(mobile);
		data_item.setEmail(email);
		data_item.setReal_name(real_name);
		data_item.setEditColumns("mobile,email,real_name");

		int uid = CInteger.tryParse(id);
		data_item.setStrWhere(String.format("id=%s", uid));

		int success = _sys_userService.update(data_item);
		if (success > 0) {

			result.setCode(success);

			result.setIsOk(true);
			result.setMessage("更新成功");

		} else {
			result.setIsOk(false);
			result.setMessage("更新失败");
		}

		return result;

	}


	@RequestMapping("/EditUserPwd")
	public ReturnResult EditUserPwd(HttpServletRequest request) throws Exception {

		var result = new ReturnResult();

		var p_uid = super.getParams(request, "id");
		var p_pwd = super.getParams(request, "pwd");
		if (CString.IsNullOrEmpty(p_uid)) {
			result.setIsOk(false);
			result.setMessage("用户id不能为空!");
			return result;
		}
		if (CString.IsNullOrEmpty(p_pwd)) {
			result.setIsOk(false);
			result.setMessage("密码不能为空!");
			return result;
		}

		int uid = CInteger.tryParse(p_uid);

		var data = new sys_user();

		//String user_pwd = CryptTools.MD5Encode(p_pwd);
		data.setUser_pwd(p_pwd);

		String strWhere = String.format("id=%s", uid);
		data.setStrWhere(strWhere);

		data.setEditColumns("user_pwd");

		int success = _sys_userService.update(data);
		if (success > 0) {

			result.setCode(success);

			result.setIsOk(true);
			result.setMessage("更新成功");

		} else {
			result.setIsOk(false);
			result.setMessage("更新失败");
		}

		return result;

	}


}
