package lbsyun.constant;
/**
 * GEOTYPE的枚举值，点、线、面POI
 * @author kuangzhijie
 *
 */
public enum GeoType {
	POINT(1), // 点
	LINE(2), // 线
	FLAT(3); // 面
	
	private int code;
	
	private GeoType(int code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return String.valueOf( this.code );
	}
}
