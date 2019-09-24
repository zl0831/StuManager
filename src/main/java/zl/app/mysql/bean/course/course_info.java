package zl.app.mysql.bean.course;

import zl.app.annotation.TableAnnotation.Column;
import zl.app.annotation.TableAnnotation.Id;
import zl.app.annotation.TableAnnotation.Table;
import zl.app.mysql.bean.base.BaseModel;

@Table(value = "course_info")
public class course_info extends BaseModel {
	
	public  course_info() {
		
	}
	

	private   int    id;
	private   String    course_no;
	private   String    course_name;
	private   String    course_des;
	private   int    is_charge;

	
	@Id(value = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(value = "course_no")
	public String getCourse_no() {
		return course_no;
	}
	public void setCourse_no(String course_no) {
		this.course_no = course_no;
	}
	
	@Column(value = "course_name")
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	
	@Column(value = "course_des")
	public String getCourse_des() {
		return course_des;
	}
	public void setCourse_des(String course_des) {
		this.course_des = course_des;
	}
	
	@Column(value = "is_charge")
	public int getIs_charge() {
		return is_charge;
	}
	public void setIs_charge(int is_charge) {
		this.is_charge = is_charge;
	}
	

	
	
	

}
