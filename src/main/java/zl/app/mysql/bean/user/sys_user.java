package zl.app.mysql.bean.user;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import zl.app.annotation.TableAnnotation.Column;
import zl.app.annotation.TableAnnotation.Id;
import zl.app.annotation.TableAnnotation.Table;
import zl.app.mysql.bean.base.BaseModel;

@Table(value = "sys_user")
public class sys_user extends BaseModel {
	
	public  sys_user() {
		
	}
	

	private   int    id;
	private   String    user_no;
	private   String    user_name;
	private   String    real_name;
	private   String    mobile;
	private   String    email;
	private   String    user_pwd;
	private   Timestamp    reg_date;
	
	@Id(value = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(value = "user_no")	
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	
	@Column(value = "user_name")	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	@Column(value = "real_name")	
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
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
	
	@Column(value = "user_pwd")	
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	
	@Column(value = "reg_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	
	
	

}
