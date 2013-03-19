package lbsyun.basestruct;

import java.util.HashMap;
import java.util.Map;

import lbsyun.ServiceException;
import lbsyun.constant.CoordType;
import lbsyun.utils.StringUtil;


/**
 * Poi的基本信息
 * 
 * @author kuangzhijie
 *
 */
public class PoiBasicInfo {
	/**
	 * POI的名称
	 */
	public String name;
	
	/**
	 * POI的地址
	 */
	public String address;
	/**
	 * POI的电话
	 */
	public String telephone;
	/**
	 * POI的邮编
	 */
	public String zipCode;
	/**
	 * POI所属省份的ID
	 */
	public int provinceId;
	/**
	 * POI所在城市的ID
	 */
	public int cityId;
	/**
	 * POI所在区域的ID
	 */
	public int districtId;
	/**
	 * POI所在省份的名称
	 */
	public String province;
	/**
	 * POI所在城市的名称
	 */
	public String city;
	/**
	 * POI所在区域的名称
	 */
	public String district;
	/**
	 * POI的坐标
	 */
	public GeoPoint location;
	/**
	 * POI坐标加密类型
	 */
	public CoordType coordType;
	/**
	 * POI的标签集
	 */
	public String[] poiTag;
	/**
	 * POI的类别ID
	 */
	public int categoryId;
	/**
	 * POI的重要性，默认是127，数值在0~255之间
	 */
	public int poiImportance;
	/**
	 * POI的创建时间
	 */
	public String createTime;
	/**
	 * POI的ICON
	 */
	public String icon;
	/**
	 * POI的百度UID
	 */
	public Long baiduUid;
	/**
	 * 线面POI时的坐标序列
	 */
	public GeoPoint[] geoSequence;
	/**
	 * 周边检索中Poi点到中心的距离
	 */
	public double distance;
	
	/**
	 * 无参数的构造
	 */
	public PoiBasicInfo() {
		init();
	}
	
	/**
	 * 基于Poi名称与坐标的构造，坐标加密类型默认为百度加密坐标
	 * 
	 * @param name Poi名称
	 * @param location Poi坐标
	 */
	public PoiBasicInfo(String name, GeoPoint location) {
		this(name, location, CoordType.BAIDU);
	}
	
	/**
	 * 基于Poi名称、坐标及加密类型的构造
	 * 
	 * @param name Poi名称
	 * @param location Poi坐标
	 * @param coordType 坐标加密类型
	 */
	public PoiBasicInfo(String name, GeoPoint location, CoordType coordType) {
		init();
		this.name = name;
		this.location = location;
		this.coordType = coordType;
	}
	
	/**
	 * 基于Poi名称、坐标、加密类型及其他参数的构造
	 * 
	 * @param name Poi名称
	 * @param location Poi坐标
	 * @param coordType Poi坐标加密类型
	 * @param opts 其他参数
	 * @throws ServiceException
	 */
	public PoiBasicInfo(String name, GeoPoint location, CoordType coordType, Map<String, String> opts) throws ServiceException {
		init();
		this.name = name;
		this.location = location;
		this.coordType = coordType;
		if (null == opts || opts.isEmpty()) {
			return;
		}
		if (opts.containsKey("address")) {
			address = opts.get("address");
		}
		if (opts.containsKey("telephone")) {
			telephone = opts.get("telephone");
		}
		if (opts.containsKey("zip_code")) {
			zipCode = opts.get("zip_code");
		}
		if (opts.containsKey("poi_tag")) {
			poiTag = opts.get("poi_tag").split(",");
		}
		if (opts.containsKey("category_id")) {
			categoryId = Integer.valueOf(opts.get("category_id"));
		}
		if (opts.containsKey("geo_sequence")) {
			String[] pts = opts.get("geo_sequence").split("|");
			int len = pts.length;
			geoSequence = new GeoPoint[len];
			for (int i = 0; i < len; ++i) {
				String[] pt = pts[i].split(",");
				if (pt.length < 2) {
					throw new ServiceException("geo_sequence's format error");
				}
				geoSequence[i] = new GeoPoint(Double.valueOf(pt[0]), Double.valueOf(pt[1]));
			}
		}
		if (opts.containsKey("icon")) {
			icon = opts.get("icon");
		}
		if (opts.containsKey("baidu_uid")) {
			baiduUid = Long.valueOf(opts.get("baidu_uid"));
		}
		if (opts.containsKey("poi_importance")) {
			poiImportance = Integer.valueOf(opts.get("poi_importance"));
		}
	}
	
	public PoiBasicInfo(Map<String, String> opts) throws ServiceException {
		init();
		if (null == opts || opts.isEmpty()) {
			return;
		}
		if (opts.containsKey("name")) {
			name = opts.get("name");
		}
		if (opts.containsKey("original_lat") && opts.containsKey("original_lon")) {
			location = new GeoPoint(Double.valueOf(opts.get("original_lat")),
					Double.valueOf(opts.get("original_lon")));
		}
		if (opts.containsKey("original_coord_type")) {
			coordType = CoordType.valueOf(opts.get("original_coord_type"));
		}		
		if (opts.containsKey("address")) {
			address = opts.get("address");
		}
		if (opts.containsKey("telephone")) {
			telephone = opts.get("telephone");
		}
		if (opts.containsKey("zip_code")) {
			zipCode = opts.get("zip_code");
		}
		if (opts.containsKey("poi_tag")) {
			poiTag = opts.get("poi_tag").split(",");
		}
		if (opts.containsKey("category_id")) {
			categoryId = Integer.valueOf(opts.get("category_id"));
		}
		if (opts.containsKey("geo_sequence")) {
			String[] pts = opts.get("geo_sequence").split("|");
			int len = pts.length;
			geoSequence = new GeoPoint[len];
			for (int i = 0; i < len; ++i) {
				String[] pt = pts[i].split(",");
				if (pt.length < 2) {
					throw new ServiceException("geo_sequence's format error");
				}
				geoSequence[i] = new GeoPoint(Double.valueOf(pt[0]), Double.valueOf(pt[1]));
			}
		}
		if (opts.containsKey("icon")) {
			icon = opts.get("icon");
		}
		if (opts.containsKey("baidu_uid")) {
			baiduUid = Long.valueOf(opts.get("baidu_uid"));
		}
		if (opts.containsKey("poi_importance")) {
			poiImportance = Integer.valueOf(opts.get("poi_importance"));
		}
	}
	
	/**
	 * 初始化Poi的基本信息
	 */
	public void init() {
		name = null;
		address = null;
		telephone = null;
		zipCode = null;
		location = null;
		poiTag = null;
		categoryId = -1;
		poiImportance = -1;
		geoSequence = null;
		icon = null;
		baiduUid = null;
		coordType = null;
		distance = 0;
	}
	
	/**
	 * 把POI的基本信息轮换成参数键值对，用于POI创建与修改
	 * 
	 * @param info POI基本信息
	 * @return 参数键值对
	 */
	public HashMap<String, String> toParams() {
		HashMap<String, String> params = new HashMap<String, String>();
		injectParams(params, this);
		return params;
	}
	
	/**
	 * 注入POI基本信息到参数键值对中，用于POI创建与修改
	 * 
	 * @param params 参数集
	 * @param info POI基本信息
	 */
	public static void injectParams(Map<String, String> params,
			PoiBasicInfo info) {
		if (null != info.name) {
			params.put("name", info.name);
		}
		if (null != info.location) {
			params.put("original_lat", Double.toString(info.location.getLatitude()));
			params.put("original_lon", Double.toString(info.location.getLongitude()));
		}
		if (null != info.coordType) {
			params.put("original_coord_type", info.coordType.toString());
		}
		if (null != info.address) {
			params.put("address", info.address);
		}
		if (null != info.telephone) {
			params.put("telephone", info.telephone);
		}
		if (null != info.zipCode) {
			params.put("zip_code", info.zipCode);
		}
		if (null != info.geoSequence) {
			params.put("geo_sequence", StringUtil.join(info.geoSequence, "|"));
		}
		if (null != info.icon) {
			params.put("icon", info.icon);
		}
		if (null != info.baiduUid) {
			params.put("baidu_uid", info.baiduUid.toString());
		}
		if (null != info.poiTag) {
			params.put("poi_tag", StringUtil.join(info.poiTag, ","));
		}
		if (info.categoryId >= 0) {
			params.put("category_id", Integer.toString(info.categoryId));
		}
		if (info.poiImportance >= 0) {
			params.put("poi_importance", Integer.toString(info.poiImportance));
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName()).append("[")
				.append("name=").append(name).append(",")
				.append("original_lat=").append(location.getLatitude());
		if (null != coordType) {
			sb.append(",").append("original_coord_type=")
			.append(coordType.name());
		}
		return	sb.append(",").append("address=")
				.append(address).append(",").append("telephone=")
				.append(telephone).append(",").append("zip_code=")
				.append(zipCode).append(",").append("poi_tag=")
				.append(StringUtil.join(poiTag, ",")).append(",")
				.append("province=").append(province).append(",")
				.append("province_id=").append(provinceId).append(",")
				.append("city=").append(city).append(",")
				.append("city_id=").append(cityId).append(",")
				.append("district=").append(district).append(",")
				.append("district_id=").append(districtId).append(",")
				.append("distance=").append(distance).append(",")
				.append("category_id=").append(categoryId).append(",")
				.append("geo_sequence=").append(geoSequence).append(",")
				.append("icon=").append(icon).append(",").append("baidu_uid=")
				.append(baiduUid).append("]").toString();
	}
	
}
