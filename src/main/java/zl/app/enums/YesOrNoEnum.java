package zl.app.enums;

 
public enum YesOrNoEnum {

	Yes(1), No(0);

	final static YesOrNoEnum[] YesOrNoEnums = YesOrNoEnum.values();

	// 定义私有变量
	private int nCode;

	// 构造函数，枚举类型只能为私有
	private YesOrNoEnum(int _nCode) {
		this.nCode = _nCode;
	}

	@Override
	public String toString() {
		return String.valueOf(this.nCode);
	}

	public int code() {
		return this.nCode;
	}
	
	public int getValue() {  
        return code();  
    }  

	/**
	 * 判断是否包含值
	 * 
	 * @param nCode
	 * @see
	 */
	public static boolean contain(int nCode) {
		boolean isHave = false;
		for (int i = 0; i < YesOrNoEnums.length; i++) {
			if (YesOrNoEnums[i].code() == nCode) {
				isHave = true;
				break;
			}
		}

		return isHave;
	}

	
	
	
	
	
	
	
}