package zl.app.controller.student;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zl.app.controller.base.BaseController;
import zl.app.mysql.bean.student.student_info;
import zl.app.mysql.services.student.student_infoService;
import zl.app.utility.CInteger;
import zl.app.utility.CString;
import zl.app.utility.ReturnResult;

@RestController
@RequestMapping("/student_info")
public class student_infoController extends BaseController {

	@Autowired
	private student_infoService _student_infoService;

	
	/*
	 * 用户--根据id获取用户信息
	 */
	@RequestMapping("/GetStudentById")
	public ReturnResult GetStudentById(HttpServletRequest request) throws Exception {

		var result = new ReturnResult();

		String p_id = super.getParams(request, "id");
		if (CString.IsNullOrEmpty(p_id)) {
			result.setIsOk(false);
			result.setMessage("id不能为空!");
			return result;
		}

		Integer id = CInteger.tryParse(p_id);

		var data = _student_infoService.getModelByid(id);
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
	 * 用户--获取用户列表分页
	 */
	@RequestMapping("/GetStudentByPage")
	public ReturnResult GetStudentByPage(HttpServletRequest request) {
		var result = new ReturnResult();

		var p_pi = super.getParams(request, "pi");
		var p_ps = super.getParams(request, "ps");
		var search =  super.getParams(request, "search");
		var is_vip =  super.getParams(request, "is_vip");
		var course_no =  super.getParams(request, "course_no");

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

		var list = _student_infoService.getListByPage(search,course_no,CInteger.tryParse(is_vip), pi, ps, "id desc", count);
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

	@RequestMapping("/Del")
	public ReturnResult Del(HttpServletRequest request) {

		var result = new ReturnResult();

		String ids = super.getParams(request, "ids");

		if (CString.IsNullOrEmpty(ids)) {
			result.setIsOk(false);
			result.setMessage("id不能为空!");
			return result;
		}
		

		// id:1,2,3
		long success = _student_infoService.deletebyWhere(ids);
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

	@RequestMapping("/Add")
	public ReturnResult Add(@RequestBody student_info data) throws Exception {

		var result = new ReturnResult();

		String stu_no = data.getStu_no();
		if (CString.IsNullOrEmpty(stu_no)) {
			result.setIsOk(false);
			result.setMessage("学生编码不能为空!");
			return result;
		}
		
		String name = data.getName();
		if (CString.IsNullOrEmpty(name)) {
			result.setIsOk(false);
			result.setMessage("学生名不能为空!");
			return result;
		}
		

		String strWhere = String.format("stu_no='%s'", stu_no);
		var item = _student_infoService.getModelBystrWhere(strWhere);
		if (item != null) {

			result.setIsOk(false);
			result.setMessage("学生编号已经存在!");

		} else {
			
			long success = _student_infoService.insert(data);
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

	@RequestMapping("/Edit")
	public ReturnResult Edit(@RequestBody student_info  data) throws Exception {

		var result = new ReturnResult();

		String stu_no = data.getStu_no();
		if (CString.IsNullOrEmpty(stu_no)) {
			result.setIsOk(false);
			result.setMessage("学生编码不能为空!");
			return result;
		}
		
		String name = data.getName();
		if (CString.IsNullOrEmpty(name)) {
			result.setIsOk(false);
			result.setMessage("学生名不能为空!");
			return result;
		}


		data.setEditColumns("name,sex,mobile,email,vx,address,is_vip");

		data.setStrWhere(String.format("stu_no='%s'", stu_no));

		int success = _student_infoService.update(data);
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
