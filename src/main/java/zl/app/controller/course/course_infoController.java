package zl.app.controller.course;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zl.app.controller.base.BaseController;
import zl.app.mysql.bean.course.course_info;
import zl.app.mysql.services.course.course_infoService;
import zl.app.utility.CInteger;
import zl.app.utility.CString;
import zl.app.utility.ReturnResult;

@RestController
@RequestMapping("/course_info")
public class course_infoController extends BaseController {

	@Autowired
	private course_infoService _course_infoService;

	
	
	/*
	 * 用户--根据id获取用户信息
	 */
	@RequestMapping("/GetCourseById")
	public ReturnResult GetCourseById(HttpServletRequest request) throws Exception {

		var result = new ReturnResult();

		String p_id = super.getParams(request, "id");
		if (CString.IsNullOrEmpty(p_id)) {
			result.setIsOk(false);
			result.setMessage("id不能为空!");
			return result;
		}

		Integer id = CInteger.tryParse(p_id);

		var data = _course_infoService.getModelByid(id);
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
	 * 获取所有课程
	 */
	@RequestMapping("/GetList")
	public ReturnResult GetList(HttpServletRequest request) throws Exception {
		var result = new ReturnResult();

		var data_list = _course_infoService.getListBystrWhere("");
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
	@RequestMapping("/GetCourseByPage")
	public ReturnResult GetCourseByPage(HttpServletRequest request) {
		var result = new ReturnResult();

		var p_pi = super.getParams(request, "pi");
		var p_ps = super.getParams(request, "ps");
		var search =  super.getParams(request, "search");
		var is_charge =  super.getParams(request, "is_charge");

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

		var list = _course_infoService.getListByPage(search,CInteger.tryParse(is_charge), pi, ps, "id desc", count);
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
		long success = _course_infoService.deletebyWhere(ids);
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
	public ReturnResult Add(@RequestBody course_info data) throws Exception {

		var result = new ReturnResult();

		String course_no = data.getCourse_no();
		if (CString.IsNullOrEmpty(course_no)) {
			result.setIsOk(false);
			result.setMessage("用户编码不能为空!");
			return result;
		}
		
		String course_name = data.getCourse_name();
		if (CString.IsNullOrEmpty(course_name)) {
			result.setIsOk(false);
			result.setMessage("用户名不能为空!");
			return result;
		}

		String strWhere = String.format("course_name='%s' and course_no='%s'", course_name,course_no);
		var item = _course_infoService.getModelBystrWhere(strWhere);
		if (item != null) {

			result.setIsOk(false);
			result.setMessage("课程已经存在!");

		} else {

			long success = _course_infoService.insert(data);
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
	public ReturnResult Edit(@RequestBody course_info data) throws Exception {

		var result = new ReturnResult();

		String course_no = data.getCourse_no();
		if (CString.IsNullOrEmpty(course_no)) {
			result.setIsOk(false);
			result.setMessage("课程编码不能为空!");
			return result;
		}
		
		String course_name = data.getCourse_name();
		if (CString.IsNullOrEmpty(course_name)) {
			result.setIsOk(false);
			result.setMessage("课程名不能为空!");
			return result;
		}


		data.setEditColumns("course_name,course_des,is_charge");

		data.setStrWhere(String.format("id=%s", data.getId()));

		int success = _course_infoService.update(data);
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
