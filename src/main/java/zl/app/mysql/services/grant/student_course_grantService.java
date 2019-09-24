package zl.app.mysql.services.grant;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zl.app.mysql.bean.grant.student_course_grant;
import zl.app.mysql.mapper.grant.student_course_grantMapper;
import zl.app.mysql.services.base.MySqlBaseService;

@Service
public class student_course_grantService extends MySqlBaseService<student_course_grant> {

	@Autowired
	private student_course_grantMapper _student_course_grantMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(_student_course_grantMapper);
	}
	
	
	public List<Map<String, Object>> getListByStuNo(String stu_no) throws Exception {

		var item = new student_course_grant();
		
		var sb = new StringBuffer();
		sb.append("select scg.id,ci.course_no,ci.course_name,ci.is_charge from course_info ci ");
		sb.append(" join student_course_grant scg on ci.course_no=scg.course_no ");
		sb.append(String.format(" where scg.stu_no = '%s'",stu_no));

		item.setTable_SQL(sb.toString());
		item.setPageSize(2000);
		item.setOrderList("id desc");

		return super.getListByPage_Slow_ManyTable(item);

	}
	
	public student_course_grant getInfoByStuNo(String stu_no,String course_no) {
		var item = new student_course_grant();
		
		item.setStrWhere(String.format("stu_no='%s' and course_no='%s'", stu_no,course_no));
		
		return super.getModelBystrWhere(item);
	}
	
	public long insert(student_course_grant item) {
		return super.insert(item);
	}	
	
	public long insertByList(List<student_course_grant> data) {
		return _student_course_grantMapper.insertAllGrant(data);
	}

	
	public int deletebyWhere(String strWhere) {
		var item = new student_course_grant();

		item.setStrWhere(strWhere);

		return super.deletebyWhere(item);
	}
	
	/**
	 * 批量增加
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String initInsertAllSql(Map<String, Object> params) {
		var list = (List<student_course_grant>) params.get("list");
		var sb = new StringBuffer();
		sb.append("insert into student_course_grant (stu_no,course_no) values ");
		String ru = "(%s,%s)";
		for (int i = 0; i < list.size(); i++) {
			sb.append(String.format(ru, list.get(i).getStu_no(), list.get(i).getCourse_no()));
			if (i < list.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	@Transactional
	public String updateGrant(List<student_course_grant> list,String stu_no) {

		String strWhere = String.format("stu_no = '%s'", stu_no);
		long del = deletebyWhere(strWhere);

		long add = 0;
		if (list != null && list.size() > 0) {
			add = insertByList(list);
		}

		return String.format("%s-%s", del,add);
	}
	

}
