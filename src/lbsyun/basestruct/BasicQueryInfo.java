package lbsyun.basestruct;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lbsyun.ServiceException;



/**
 * POI条件查询的条件类
 * 
 * @author kuangzhijie
 *
 */
public class BasicQueryInfo {
	/**
	 * POI名称
	 */
	public String name;
	/**
	 * POI标签
	 */
	public String tag;
	/**
	 * POI重要性，0~255间，为负值时，则设为默认值，即127
	 */
	public int importance;
	/**
	 * 检索矩形框
	 */
	public Bounds bounds;
	/**
	 * 检索省份ID
	 */
	public int provinceId;
	/**
	 * 检索城市ID
	 */
	public int cityId;
	/**
	 * 检索区域ID
	 */
	public int districtId;
	/**
	 * 检索省份名称
	 */
	public String provinceName;
	/**
	 * 检索城市名称
	 */
	public String cityName;
	/**
	 * 检索区域名称
	 */
	public String districtName;
	/**
	 * 开始时间
	 */
	public Date startDate;
	/**
	 * 截止时间
	 */
	public Date endDate;
	/**
	 * 无参数构造
	 */
	public BasicQueryInfo() {
		init();
	}
	/**
	 * 基于Map的构造
	 * 
	 * @param opts 参数
	 * @throws ServiceException 
	 */
	public BasicQueryInfo(Map<String, String> opts) throws ServiceException {
		init();
		if (null == opts || opts.isEmpty()) {
			return;
		}
		
		if (opts.containsKey("name")) {
			name = opts.get("name");
		}
		if (opts.containsKey("poi_tag")) {
			tag = opts.get("poi_tag");
		}
		if (opts.containsKey("poi_importance")) {
			importance = Integer.valueOf(opts.get("poi_importance"));
		}
		if (opts.containsKey("bounds")) {
			String[] pts = opts.get("bounds").split(";");
			if (pts.length < 2) {
				throw new ServiceException("bounds format error");
			}
			GeoPoint[] geoPts = new GeoPoint[2];
			for (int i = 0; i < 2; ++i) {
				String[] pt = pts[i].split(",");
				if (pt.length < 2) {
					throw new ServiceException("geopoint format error");
				}
				geoPts[i] = new GeoPoint(Double.valueOf(pt[0]),
						Double.valueOf(pt[1]));
			}
		}
		if (opts.containsKey("province")) {
			provinceName = opts.get("province");
		}
		if (opts.containsKey("province_id")) {
			provinceId = Integer.valueOf(opts.get("province_id"));
		}
		if (opts.containsKey("city")) {
			cityName = opts.get("city");
		}
		if (opts.containsKey("city_id")) {
			cityId = Integer.valueOf(opts.get("city_id"));
		}
		if (opts.containsKey("district")) {
			districtName = opts.get("district");
		}
		if (opts.containsKey("district_id")) {
			districtId = Integer.valueOf(opts.get("district_id"));
		}
		if (opts.containsKey("start_date")) {
			try {
				startDate = DateFormat.getInstance().parse(opts.get("start_date"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (opts.containsKey("end_date")) {
			try {
				endDate = DateFormat.getInstance().parse(opts.get("end_date"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 初始化
	 */
	public void init() {
		name = null;
		tag = null;
		importance = -1;
		bounds = null;
		provinceId = -1;
		cityId = -1;
		districtId = -1;
		provinceName = null;
		cityName = null;
		districtName = null;
		startDate = null;
		endDate = null;
	}
	

	/**
	 * 把查询条件转化成参数键值对，用于检索请求
	 * 
	 * @return 参数键值对
	 * @throws ServiceException
	 */
	public HashMap<String, String> toParams() {
		HashMap<String, String> params = new HashMap<String, String>();
		if (null != name) {
			params.put("name", name);
		}
		if (null != tag) {
			params.put("poi_tag", tag);
		}
		if (importance >= 0) {
			params.put("poi_importance", Integer.toString(importance));
		}
		if (null != bounds) {
			params.put("bounds", bounds.toString());
		}
		if (provinceId >= 0) {
			params.put("province_id", Integer.toString(provinceId));
		}
		if (cityId >= 0) {
			params.put("city_id", Integer.toString(cityId));
		}
		if (districtId >= 0) {
			params.put("district_id", Integer.toString(districtId));
		}
		if (null != provinceName) {
			params.put("province", provinceName);
		}
		if (null != cityName) {
			params.put("city", cityName);
		}
		if (null != districtName) {
			params.put("district", districtName);
		}
		if (null != startDate) {
			params.put("start_date", DateFormat.getInstance().format(startDate));
		}
		if (null != endDate) {
			params.put("end_date", DateFormat.getInstance().format(endDate));
		}
		return params;
	}

	/**
	 * 把查询条件注入已有参数键值对中，用于检索请求
	 * 
	 * @param params 参数键值对
	 * @param info 查询信息
	 * @throws ServiceException
	 */
	public static void injectParams(Map<String, String> params,
			BasicQueryInfo info) {
		if (null != info.name) {
			params.put("name", info.name);
		}
		if (null != info.tag) {
			params.put("poi_tag", info.tag);
		}
		if (info.importance >= 0) {
			params.put("poi_importance", Integer.toString(info.importance));
		}
		if (null != info.bounds) {
			params.put("bounds", info.bounds.toString());
		}
		if (info.provinceId >= 0) {
			params.put("province_id", Integer.toString(info.provinceId));
		}
		if (info.cityId >= 0) {
			params.put("city_id", Integer.toString(info.cityId));
		}
		if (info.districtId >= 0) {
			params.put("district_id", Integer.toString(info.districtId));
		}
		if (null != info.provinceName) {
			params.put("province", info.provinceName);
		}
		if (null != info.cityName) {
			params.put("city", info.cityName);
		}
		if (null != info.districtName) {
			params.put("district", info.districtName);
		}
		if (null != info.startDate) {
			params.put("start_date",
					DateFormat.getInstance().format(info.startDate));
		}
		if (null != info.endDate) {
			params.put("end_date", DateFormat.getInstance()
					.format(info.endDate));
		}
	}
}
