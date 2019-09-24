package zl.app.mysql.services.course;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zl.app.mysql.bean.course.course_info;
import zl.app.mysql.mapper.course.course_infoMapper;
import zl.app.mysql.services.base.MySqlBaseService;
import zl.app.utility.CString;

@Service
public class course_infoService extends MySqlBaseService<course_info> {

	@Autowired
	private course_infoMapper _course_infoMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(_course_infoMapper);
	}

	public course_info getModelByid(Integer id) {

		var item = new course_info();

		item.setStrWhere(String.format("id=%s", id));

		return super.getModelBystrWhere(item);

	}
	

	public course_info getModelBystrWhere(String strWhere) {

		var item = new course_info();

		item.setStrWhere(strWhere);

		return super.getModelBystrWhere(item);

	}
	
	public List<Map<String, Object>> getListBystrWhere(String strWhere) throws Exception {

		var item = new course_info();

		item.setStrWhere(strWhere);

		return super.getListByStrWhere(item);

	}
	

	public List<Map<String, Object>> getListByPage(String search, int is_charge,int PageIndex, int PageSize, String OrderList,
			AtomicLong count) {
		
		var item = new course_info();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from course_info where 1=1 ");
		if(is_charge != -1) {
			sb.append(String.format("and is_charge=%s",is_charge));
		}
		if(!CString.IsNullOrEmpty(search)) {
			sb.append(String.format("and LOCATE('%s',course_name) > 0",search));
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


	public int update(course_info data) {
		return super.update(data);
	}


	public long insert(course_info data) {
		return super.insert(data);
	}


	public int deleteById(course_info data) throws Exception {
		return super.deleteById(data);
	}


	public int deletebyWhere(String ids) {

		String strWhere = String.format("id in (%s)", ids);

		var item = new course_info();

		item.setStrWhere(strWhere);

		return super.deletebyWhere(item);
	}
	

}
