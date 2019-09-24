package zl.app.mysql.mapper.base;


import java.util.List;
import java.util.Map;

public interface IMySqlBaseMapper {

	 Map<String, Object> getModelByID(Object obj);
	 Map<String, Object> getModelBystrWhere(Object obj);
	
	 List<Map<String, Object>> getListByStrWhere(Object obj);
	 
	 List<Map<String, Object>> getListByPage(Object obj);
	 List<Map<String, Object>> getListByPage_Slow(Object obj);
	 List<Map<String, Object>> getListByPage_Fast_NoWhere(Object obj);//不带where条件查询速度快
	 List<Map<String, Object>> getListByPage_Fast(Object obj); 
	 
	 List<Map<String, Object>> getListByPage_Slow_ManyTable(Object obj); 
	 
	 int getCountBySql(Object obj);
	 int getCount(Object obj);
	 int deleteById(Object obj);
	 int deletebyWhere(Object obj);
 
     int update(Object obj);
	 int insert(Object obj);
	 int existsTable(Object obj);
	
}