package zl.app.mysql.services.student;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zl.app.mysql.bean.student.student_info;
import zl.app.mysql.mapper.student.student_infoMapper;
import zl.app.mysql.services.base.MySqlBaseService;
import zl.app.utility.CString;

@Service
public class student_infoService extends MySqlBaseService<student_info> {

	@Autowired
	private student_infoMapper _student_infoMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(_student_infoMapper);
	}

	public student_info getModelByid(Integer id) {

		var item = new student_info();

		item.setStrWhere(String.format("id=%s", id));

		return super.getModelBystrWhere(item);

	}

	public student_info getModelBystrWhere(String strWhere) {

		var item = new student_info();

		item.setStrWhere(strWhere);

		return super.getModelBystrWhere(item);

	}

	public List<Map<String, Object>> getListByPage(String search, String course_no, int is_vip, int PageIndex,
			int PageSize, String OrderList, AtomicLong count) {

		var item = new student_info();

		StringBuffer sb = new StringBuffer();
		sb.append("select si.id,si.stu_no,si.name,si.sex,si.mobile,.si.email,si.vx,si.address,si.is_vip,DATE_FORMAT(si.reg_data,'%Y-%m-%d') reg_data from student_info si ");
		if (!CString.IsNullOrEmpty(course_no)) {
			sb.append(" join student_course_grant scg on si.stu_no=scg.stu_no");
			sb.append(String.format(" join course_info ci on ci.course_no=scg.course_no and ci.course_no ='%s'",
					course_no));
		}
		if (is_vip != -1) {
			sb.append(String.format("where si.is_vip=%s", is_vip));
		}
		if (!CString.IsNullOrEmpty(search)) {
			sb.append(String.format("and LOCATE('%s',si.name) > 0", search));
		}

		item.setOrderList(OrderList);
		item.setPageIndex(PageIndex);
		item.setPageSize(PageSize);

		item.setTable_SQL(sb.toString());

		if (count != null) {
			count.set(super.getCountBySql(item));// 计算总记录数
		}

		return super.getListByPage_Slow_ManyTable(item);
	}

	public int update(student_info data) {
		return super.update(data);
	}

	public long insert(student_info data) {
		return super.insert(data);
	}

	public int deleteById(student_info data) throws Exception {
		return super.deleteById(data);
	}

	public int deletebyWhere(String ids) {

		String strWhere = String.format("id in (%s)", ids);

		var item = new student_info();

		item.setStrWhere(strWhere);

		return super.deletebyWhere(item);
	}

}
