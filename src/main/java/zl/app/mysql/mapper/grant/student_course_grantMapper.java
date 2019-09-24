package zl.app.mysql.mapper.grant;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zl.app.mysql.bean.grant.student_course_grant;
import zl.app.mysql.mapper.base.IMySqlBaseMapper;
import zl.app.mysql.services.grant.student_course_grantService;



@Mapper
public interface student_course_grantMapper extends IMySqlBaseMapper  {
	
	/**
	 * 	批量插入
	 * @return
	 */
	@InsertProvider(type = student_course_grantService.class, method = "initInsertAllSql")  
    int insertAllGrant(@Param("list") List<student_course_grant> data);

}


 
 