package zl.app.mysql.bean.grant;

import zl.app.annotation.TableAnnotation.Column;
import zl.app.annotation.TableAnnotation.Id;
import zl.app.annotation.TableAnnotation.Table;
import zl.app.mysql.bean.base.BaseModel;

@Table(value = "student_course_grant")
public class student_course_grant extends BaseModel {
	
	public  student_course_grant() {
		
	}
	

	private   int    id;
	private   String    stu_no;
	private   String    course_no;

	
	@Id(value = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Column(value = "stu_no")	
	public String getStu_no() {
		return stu_no;
	}
	public void setStu_no(String stu_no) {
		this.stu_no = stu_no;
	}
	
	@Column(value = "course_no")	
	public String getCourse_no() {
		return course_no;
	}
	public void setCourse_no(String course_no) {
		this.course_no = course_no;
	}


	
	

}
