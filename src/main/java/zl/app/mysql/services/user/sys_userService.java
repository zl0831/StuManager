package zl.app.mysql.services.user;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zl.app.mysql.bean.user.sys_user;
import zl.app.mysql.mapper.user.sys_userMapper;
import zl.app.mysql.services.base.MySqlBaseService;

@Service
public class sys_userService extends MySqlBaseService<sys_user> {

	@Autowired
	private sys_userMapper _sys_userMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(_sys_userMapper);
	}

	public sys_user getModelByid(Integer id) {

		var item = new sys_user();

		item.setStrWhere(String.format("id=%s", id));

		return super.getModelBystrWhere(item);

	}
	

	public sys_user getModelBystrWhere(String strWhere) {

		var item = new sys_user();

		item.setStrWhere(strWhere);

		return super.getModelBystrWhere(item);

	}
	
	public List<Map<String, Object>> getListBystrWhere(String strWhere) throws Exception {

		var item = new sys_user();

		item.setStrWhere(strWhere);

		return super.getListByStrWhere(item);

	}

	public List<Map<String, Object>> getUsersListByPage(String strWhere, int PageIndex, int PageSize, String OrderList,
			AtomicLong count) {

		var item = new sys_user();

		item.setOrderList(OrderList);
		item.setPageIndex(PageIndex);
		item.setPageSize(PageSize);

		count.set(super.getCount(item));// 计算总记录数

		return super.getListByPage(item);
	}


	public int update(sys_user data) {
		return super.update(data);
	}


	public long insert(sys_user data) {
		return super.insert(data);
	}


	public int deleteById(sys_user data) throws Exception {
		return super.deleteById(data);
	}


	public int deletebyWhere(String ids) {

		String strWhere = String.format("id in (%s)", ids);

		var item = new sys_user();

		item.setStrWhere(strWhere);

		return super.deletebyWhere(item);
	}
	

}
