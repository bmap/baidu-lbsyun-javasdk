package lbsyun.constant;

/**
 * 坐标加密标准，国测局加密 | 百度加密 | 无加密
 * 
 * @author kuangzhijie
 *
 */
public enum CoordType{
	GOV(1), // 国测局加密
	BAIDU(2), // 百度加密
	NONE(3); // 无加密
	
	private int code;
	
	private CoordType(int code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return String.valueOf( this.code );
	}
}
