package zl.app.enums;
public enum DBActionTypeEnum {

	Insert(1), Update(2), Common(3);

	 
	// 定义私有变量
	private int Code;

	// 构造函数，枚举类型只能为私有
	private DBActionTypeEnum(int _nCode) {
		 
		this.Code = _nCode;
	}
 

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}
	 

	
	
}
