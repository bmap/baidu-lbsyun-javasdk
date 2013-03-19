package lbsyun.basestruct;
/**
 * 矩形框
 * 
 * @author kuangzhijie
 *
 */
public class Bounds {
	/**
	 * 左下角坐标点
	 */
	private GeoPoint leftBottom;
	/**
	 * 右上角坐标点
	 */
	private GeoPoint rightTop;
	
	/**
	 * 获取左下角坐标点
	 * 
	 * @return 左下角坐标点
	 */
	public GeoPoint getLeftBottom() {
		return leftBottom;
	}

	/**
	 * 设置左下角坐标点
	 * 
	 * @param leftBottom 左下角坐标点
	 */
	public void setLeftBottom(GeoPoint leftBottom) {
		this.leftBottom = leftBottom;
	}

	/**
	 * 获取右上角坐标点
	 * 
	 * @return 右上角坐标点
	 */
	public GeoPoint getRightTop() {
		return rightTop;
	}

	/**
	 * 设置右上角坐标点 
	 * 
	 * @param rightTop 右上角坐标点
	 */
	public void setRightTop(GeoPoint rightTop) {
		this.rightTop = rightTop;
	}

	/**
	 * 基于左下角点与右上角点的构造
	 * 
	 * @param leftBottom 左下角点
	 * @param rightTop 右上角点
	 */
	public Bounds(GeoPoint leftBottom, GeoPoint rightTop) {
		this.leftBottom = leftBottom;
		this.rightTop = rightTop;
	}
	
	/**
	 * 基于边框的构造
	 * @param left 左经度
	 * @param right 右经度
	 * @param top 上纬度
	 * @param bottom 下纬度
	 */
	public Bounds(double left, double right, double top, double bottom) {
		this.leftBottom = new GeoPoint(bottom, left);
		this.rightTop = new GeoPoint(top, right);
	}

	@Override
	public String toString() {
		return leftBottom.toString() + ";" + rightTop.toString();
	}
	
	/**
	 * 返回Bounds的字符串
	 * @param lat1 左下角纬度
	 * @param lon1 左下角经度
	 * @param lat2 右下角纬度
	 * @param lon2 右下角经度
	 * @return Bounds的字符串
	 */
	public static String toString(double lat1, double lon1, double lat2, double lon2) {
		StringBuilder sb = new StringBuilder();
		return sb.append(lat1).append(",").append(lon1).append(";")
				.append(lat2).append(",").append(lon2).toString();
	}
}
