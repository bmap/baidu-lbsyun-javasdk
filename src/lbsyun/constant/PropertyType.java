package lbsyun.constant;

/**
 * PropertyType的枚举，int32 | int64 | float | double | string
 * 
 * @author kuangzhijie
 *
 */
public enum PropertyType {
	INT32(1), INT64(2), FLOAT(3), DOUBLE(4), STRING(10);

	private int code;
	
	private PropertyType(int code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return String.valueOf( this.code );
	}
	
	
	/**
	 * 属性字段类型数组转成字符串数组
	 * 
	 * @param resourceArray
	 * @return 字符串数组
	 */
	public static String[] toStringArray(PropertyType[] resourceArray) {
		if (null == resourceArray) {
			return null;
		}
		String[] dstArray = new String[resourceArray.length];
		for (int i = 0; i < resourceArray.length; ++i) {
			dstArray[i] = resourceArray[i].toString();
		}
		return dstArray;
	}
	
	/**
	 * 根据数值获得属性类型
	 * 
	 * @param code
	 * @return 属性类型
	 */
	public static PropertyType valueOf(int code) {
		switch (code) {
		case 1:
			return INT32;
		case 2:
			return INT64;
		case 3:
			return FLOAT;
		case 4:
			return DOUBLE;
		case 10:
			return STRING;
		default:
			return null;
		}
	}
}
