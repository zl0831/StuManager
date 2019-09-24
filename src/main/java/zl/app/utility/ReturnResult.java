package zl.app.utility;

public class ReturnResult {

	public ReturnResult() {
		this.IsOk = false;
		this.Message = "操作未初始化";
		this.Code=0;  
	}

	private boolean IsOk;
	private long Code;
	private String Message;
	private Object data;
	private java.util.List<?> data_list;
	private int pageIndex;
	private long totalCount;
	private int pageSize;
	private String token;
	private String user_no;

	public int getPageIndex() {
		return pageIndex;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public java.util.List<?> getData_list() {
		return data_list;
	}

	public void setData_list(java.util.List<?> data_list) {
		this.data_list = data_list;
	}

	public boolean isIsOk() {
		return IsOk;
	}

	public void setIsOk(boolean isOk) {
		IsOk = isOk;
	}

	public long getCode() {
		return Code;
	}

	public void setCode(long code) {
		Code = code;
	}

 
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUser_no() {
		return user_no;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}

	
	
}
