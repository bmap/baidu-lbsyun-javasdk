package lbsyun.constant;
/**
 * Scope的枚举值，基本信息、基本信息+扩展信息
 * 
 * @author kuangzhijie
 *
 */
public enum Scope {
	BASIC(1), DETAIL(2);
	
	private int code;
	
	private Scope(int code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return String.valueOf( this.code );
	}
	
	public static Scope valueOf(int code){
		switch(code) {
		case 1:
			return BASIC;
		case 2:
			return DETAIL;
		default:
			return BASIC;
		}
	}
}
