package lbsyun.basestruct;

/**
 * 地理位置点
 * 
 * @author kuangzhijie
 *
 */
public class GeoPoint {
	/**
	 * 纬度
	 */
	private double latitude;
	/**
	 * 经度
	 */
	private double longitude;
	/**
	 * 获取纬度
	 * 
	 * @return 纬度
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * 设置纬度
	 * 
	 * @param latitude 纬度
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * 获取经度
	 * 
	 * @return 经度
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * 设置经度
	 * 
	 * @param longitude 经度
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 设置经纬度
	 * @param latitude 纬度
	 * @param longitude 经度
	 */
	public GeoPoint(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return latitude + "," + longitude;
	}
}
