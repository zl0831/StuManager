package zl.app.mysql.bean.student;


import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import zl.app.annotation.TableAnnotation.Column;
import zl.app.annotation.TableAnnotation.Id;
import zl.app.annotation.TableAnnotation.Table;
import zl.app.mysql.bean.base.BaseModel;

@Table(value = "student_info")
public class student_info extends BaseModel {
	
	public  student_info() {

	}
	

	private   int    id;
	private   String    stu_no;
	private   String    name;
	private   int    sex;
	private   String    mobile;
	private   String    email;
	private   String    vx;
	private   String    address;
	private   int    is_vip;
	private   Timestamp    reg_data;
	
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
	
	@Column(value = "name")	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(value = "sex")	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	@Column(value = "mobile")	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(value = "email")	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(value = "vx")	
	public String getVx() {
		return vx;
	}
	public void setVx(String vx) {
		this.vx = vx;
	}
	
	@Column(value = "address")	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(value = "is_vip")	
	public int getIs_vip() {
		return is_vip;
	}
	public void setIs_vip(int is_vip) {
		this.is_vip = is_vip;
	}
	
	@Column(value = "reg_data")	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Timestamp getReg_data() {
		return reg_data;
	}
	public void setReg_data(Timestamp reg_data) {
		this.reg_data = reg_data;
	}
	

	
	
	

}
