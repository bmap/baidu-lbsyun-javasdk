package lbsyun.constant;
/**
 * 排序，升序|降序
 * 
 * @author kuangzhijie
 *
 */
public enum SortRule{
	DESCENDING(0), // 降序
	ASCENDING(1); // 升序
	
	private int code;
	
	private SortRule(int code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return String.valueOf( this.code );
	}
}
