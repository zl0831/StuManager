package zl.app.controller.grant;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zl.app.controller.base.BaseController;
import zl.app.mysql.bean.grant.student_course_grant;
import zl.app.mysql.services.grant.student_course_grantService;
import zl.app.utility.CInteger;
import zl.app.utility.CString;
import zl.app.utility.ReturnResult;

@RestController
@RequestMapping("/student_course_grant")
public class student_course_grantController extends BaseController {

	@Autowired
	private student_course_grantService _student_course_grantService;

	
	@RequestMapping("/GetListByStuNo")
	public ReturnResult GetListByStuNo(HttpServletRequest request) throws Exception {
		var result = new ReturnResult();
		
		var stu_no = super.getParams(request, "stu_no");
		if(CString.IsNullOrEmpty(stu_no)) {
			result.setIsOk(false);
			result.setMessage("学生编码不能为空");
			return result;
		}
		
		var list = _student_course_grantService.getListByStuNo(stu_no);
		if(list!=null && list.size() >0) {
			result.setIsOk(true);
			result.setData_list(list);
			result.setMessage("查询成功");
		} else {
			result.setIsOk(false);
			result.setMessage("未查询到数据");
		}	
		
		return result;
	}
	
	
	@RequestMapping("/AddOneGrant")
	public ReturnResult AddOneGrant(HttpServletRequest request) throws Exception {
		var result = new ReturnResult();
		
		var stu_no = super.getParams(request, "stu_no");
		if(CString.IsNullOrEmpty(stu_no)) {
			result.setIsOk(false);
			result.setMessage("学生编码不能为空");
			return result;
		}
		
		var course_no = super.getParams(request, "course_no");
		if(CString.IsNullOrEmpty(course_no)) {
			result.setIsOk(false);
			result.setMessage("课程编码不能为空");
			return result;
		}
		
		var date = _student_course_grantService.getInfoByStuNo(stu_no,course_no);
		if(date != null) {
			result.setIsOk(false);
			result.setMessage("已添加过此课程");
			return result;
		}
		
		var item = new student_course_grant();
		item.setStu_no(stu_no);
		item.setCourse_no(course_no);
		
		var success = _student_course_grantService.insert(item);
		if(success >0) {
			result.setIsOk(true);
			result.setMessage("操作成功");
		} else {
			result.setIsOk(false);
			result.setMessage("操作失败");
		}	
		
		return result;
	}
	
	@RequestMapping("/DeleteByIds")
	public ReturnResult DeleteByIds(HttpServletRequest request) throws Exception {
		var result = new ReturnResult();
		
		var ids = super.getParams(request, "ids");
		if(CString.IsNullOrEmpty(ids)) {
			result.setIsOk(false);
			result.setMessage("id不能为空");
			return result;
		}		
		
		var success = _student_course_grantService.deletebyWhere(String.format("id in (%s)", CString.sqlIn(ids)));
		if(success >0) {
			result.setIsOk(true);
			result.setMessage("操作成功");
		} else {
			result.setIsOk(false);
			result.setMessage("操作失败");
		}	
		
		return result;
	}
	
	
	@RequestMapping("/DeleteByStuNo")
	public ReturnResult DeleteByStuNo(HttpServletRequest request) throws Exception {
		var result = new ReturnResult();
		
		var stu_no = super.getParams(request, "stu_no");
		if(CString.IsNullOrEmpty(stu_no)) {
			result.setIsOk(false);
			result.setMessage("学生编码不能为空");
			return result;
		}		
		
		var success = _student_course_grantService.deletebyWhere(String.format("stu_no = '%s'", stu_no));
		if(success >0) {
			result.setIsOk(true);
			result.setMessage("操作成功");
		} else {
			result.setIsOk(false);
			result.setMessage("操作失败");
		}	
		
		return result;
	}
	
	
	/**
	 * 保存模块菜单
	 */
	@RequestMapping("/OperateGrant")
	public ReturnResult OperateGrant(HttpServletRequest request) {
		var result = new ReturnResult();
		String stu_no = super.getParams(request, "stu_no");
		if (CString.IsNullOrEmpty(stu_no)) {
			result.setIsOk(false);
			result.setMessage("学生编号不能为空");
			return result;
		}

		String courses = super.getParams(request, "List");
		var list = new ArrayList<student_course_grant>();
		if (!CString.IsNullOrEmpty(courses)) {
			String[] arr = courses.split(",");
			for (var a : arr) {
				var item = new student_course_grant();
				
				item.setCourse_no(a);
				item.setStu_no(stu_no);

				list.add(item);
			}
		}

		String success = _student_course_grantService.updateGrant(list, stu_no);
		String[] reStrings = success.split("-");
		int del = CInteger.tryParse(reStrings[0]);
		int add = CInteger.tryParse(reStrings[0]);

		if (del >= 0 && add >= 0) {
			result.setIsOk(true);
			result.setMessage("操作成功");
		} else {
			result.setIsOk(false);
			result.setMessage("操作失败");
		}

		return result;
	}
	


}
