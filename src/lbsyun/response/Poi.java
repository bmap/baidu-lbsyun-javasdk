package lbsyun.response;

import java.util.Iterator;

import lbsyun.ServiceException;
import lbsyun.basestruct.GeoPoint;
import lbsyun.basestruct.PoiBasicInfo;
import lbsyun.basestruct.PoiExtInfo;
import lbsyun.org.json.JSONException;
import lbsyun.org.json.JSONObject;




/**
 * POI信息
 * 
 * @author kuangzhijie
 *
 */
public class Poi {
	/**
	 * POI所属Databox的ID
	 */
	private int databoxId;
	/**
	 * POI的ID
	 */
	private String id;
	/**
	 * POI基本信息
	 */
	private PoiBasicInfo basicInfo;

	/**
	 * POI扩展信息
	 */
	private PoiExtInfo extInfo;

	/**
	 * 基于JSON字符串的构造
	 * 
	 * @param json JSON字符串
	 * @throws ServiceException
	 */
	public Poi(String json) throws ServiceException {
		init(json);
	}

	/**
	 * 基于JSONObject的构造
	 * 
	 * @param jsonObject JSONObject实例
	 * @throws ServiceException
	 */
	public Poi(JSONObject jsonObject) throws ServiceException {
		init(jsonObject);
	}
	

	/**
	 * 基于JSON字符串的初始化
	 * 
	 * @param json JSON字符串
	 * @throws ServiceException
	 */
	public void init(String json) throws ServiceException {
		try {
			JSONObject jsonObject = new JSONObject(json);
			if (!jsonObject.isNull("id")){
				// 云存储中查询poi的id字段
				id = jsonObject.getString("id");
			} else {
				// 云检索中查询poi的id字段
				id = jsonObject.getString("uid");
			}
			basicInfo = new PoiBasicInfo();
			basicInfo.name = jsonObject.getString("name");
			databoxId = jsonObject.getInt("databox_id");
			basicInfo.location = new GeoPoint(jsonObject.getDouble("latitude"),
					jsonObject.getDouble("longitude"));
			
			if (!jsonObject.isNull("create_time")) {
				basicInfo.createTime = jsonObject.getString("create_time");
			} else {
				basicInfo.createTime = null;
			}
			if (!jsonObject.isNull("address")) {
				basicInfo.address = jsonObject.getString("address");
			} else if(!jsonObject.isNull("addr")) {
				basicInfo.address = jsonObject.getString("addr");
			} else {
				basicInfo.address = null;
			}
			if (!jsonObject.isNull("telephone")) {
				basicInfo.telephone = jsonObject.getString("telephone");
			} else {
				basicInfo.telephone = null;
			}
			if (!jsonObject.isNull("zipCode")) {
				basicInfo.zipCode = jsonObject.getString("zipCode");
			} else if(!jsonObject.isNull("tel")) {
				basicInfo.address = jsonObject.getString("tel");
			} else {
				basicInfo.zipCode = null;
			}
			if (!jsonObject.isNull("province_id")) {
				basicInfo.provinceId = jsonObject.getInt("province_id");
			}else {
				basicInfo.provinceId = -1;
			}
			if (!jsonObject.isNull("city_id")) {
				basicInfo.cityId = jsonObject.getInt("city_id");
			}else {
				basicInfo.cityId = -1;
			}
			if (!jsonObject.isNull("district_id")) {
				basicInfo.districtId = jsonObject.getInt("district_id");
			}else {
				basicInfo.districtId = -1;
			}
			if (!jsonObject.isNull("province")) {
				basicInfo.province = jsonObject.getString("province");
			}else {
				basicInfo.province = null;
			}
			if (!jsonObject.isNull("city")) {
				basicInfo.city = jsonObject.getString("city");
			}else {
				basicInfo.city = null;
			}
			if (!jsonObject.isNull("district")) {
				basicInfo.district = jsonObject.getString("district");
			}else {
				basicInfo.district = null;
			}
			
			if (!jsonObject.isNull("poi_tag")) {
				String poiTags = jsonObject.getString("poi_tag");
				basicInfo.poiTag = poiTags.split(",");
			} else if(!jsonObject.isNull("tag")) {
				basicInfo.address = jsonObject.getString("tag");
			} else {
				basicInfo.poiTag = null;
			}
			if (!jsonObject.isNull("category_id")) {
				basicInfo.categoryId = jsonObject.getInt("category_id");
			}else {
				basicInfo.categoryId = -1;
			}
			if (!jsonObject.isNull("poi_importance")) {
				basicInfo.poiImportance = jsonObject.getInt("poi_importance");
			}else {
				basicInfo.poiImportance = -1;
			}
			
			if (!jsonObject.isNull("icon")) {
				basicInfo.icon = jsonObject.getString("icon");
			}else {
				basicInfo.icon = null;
			}
			if (!jsonObject.isNull("icon")) {
				basicInfo.baiduUid = jsonObject.getLong("baidu_uid");
			}else {
				basicInfo.baiduUid = null;
			}
			if (!jsonObject.isNull("distance")) {
				basicInfo.distance = jsonObject.getDouble("distance");
			}else {
				basicInfo.distance = 0;
			}
			
			if (!jsonObject.isNull("ext")) {
				JSONObject extMap = jsonObject.getJSONObject("ext");
				Iterator<String> keys = extMap.keys();
				extInfo = new PoiExtInfo();
				while (keys.hasNext()) {
					String key = keys.next();
					extInfo.put(key, extMap.get(key));
				}
			} else {
				extInfo = null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}

	/**
	 * 基于JSONObject的初始化
	 * 
	 * @param jsonObject JSONObject实例
	 * @throws ServiceException
	 */
	public void init(JSONObject jsonObject) throws ServiceException {
		try {
			if (!jsonObject.isNull("id")){
				// 云存储中查询poi的id字段
				id = jsonObject.getString("id");
			} else {
				// 云检索中查询poi的id字段
				id = jsonObject.getString("uid");
			}
			basicInfo = new PoiBasicInfo();
			basicInfo.name = jsonObject.getString("name");
			databoxId = jsonObject.getInt("databox_id");
			basicInfo.location = new GeoPoint(jsonObject.getDouble("latitude"),
					jsonObject.getDouble("longitude"));
			
			if (!jsonObject.isNull("create_time")) {
				basicInfo.createTime = jsonObject.getString("create_time");
			} else {
				basicInfo.createTime = null;
			}
			if (!jsonObject.isNull("address")) {
				basicInfo.address = jsonObject.getString("address");
			} else if(!jsonObject.isNull("addr")) {
				basicInfo.address = jsonObject.getString("addr");
			} else {
				basicInfo.address = null;
			}
			if (!jsonObject.isNull("telephone")) {
				basicInfo.telephone = jsonObject.getString("telephone");
			} else {
				basicInfo.telephone = null;
			}
			if (!jsonObject.isNull("zipCode")) {
				basicInfo.zipCode = jsonObject.getString("zipCode");
			} else if(!jsonObject.isNull("tel")) {
				basicInfo.address = jsonObject.getString("tel");
			} else {
				basicInfo.zipCode = null;
			}
			if (!jsonObject.isNull("province_id")) {
				basicInfo.provinceId = jsonObject.getInt("province_id");
			}else {
				basicInfo.provinceId = -1;
			}
			if (!jsonObject.isNull("city_id")) {
				basicInfo.cityId = jsonObject.getInt("city_id");
			}else {
				basicInfo.cityId = -1;
			}
			if (!jsonObject.isNull("district_id")) {
				basicInfo.districtId = jsonObject.getInt("district_id");
			}else {
				basicInfo.districtId = -1;
			}
			if (!jsonObject.isNull("province")) {
				basicInfo.province = jsonObject.getString("province");
			}else {
				basicInfo.province = null;
			}
			if (!jsonObject.isNull("city")) {
				basicInfo.city = jsonObject.getString("city");
			}else {
				basicInfo.city = null;
			}
			if (!jsonObject.isNull("district")) {
				basicInfo.district = jsonObject.getString("district");
			}else {
				basicInfo.district = null;
			}
			
			if (!jsonObject.isNull("poi_tag")) {
				String poiTags = jsonObject.getString("poi_tag");
				basicInfo.poiTag = poiTags.split(",");
			} else if(!jsonObject.isNull("tag")) {
				basicInfo.address = jsonObject.getString("tag");
			} else {
				basicInfo.poiTag = null;
			}
			if (!jsonObject.isNull("category_id")) {
				basicInfo.categoryId = jsonObject.getInt("category_id");
			}else {
				basicInfo.categoryId = -1;
			}
			if (!jsonObject.isNull("poi_importance")) {
				basicInfo.poiImportance = jsonObject.getInt("poi_importance");
			}else {
				basicInfo.poiImportance = -1;
			}
			
			if (!jsonObject.isNull("icon")) {
				basicInfo.icon = jsonObject.getString("icon");
			}else {
				basicInfo.icon = null;
			}
			if (!jsonObject.isNull("icon")) {
				basicInfo.baiduUid = jsonObject.getLong("baidu_uid");
			}else {
				basicInfo.baiduUid = null;
			}
			if (!jsonObject.isNull("distance")) {
				basicInfo.distance = jsonObject.getDouble("distance");
			}else {
				basicInfo.distance = 0;
			}
			
			if (!jsonObject.isNull("ext")) {
				JSONObject extMap = jsonObject.getJSONObject("ext");
				Iterator<String> keys = extMap.keys();
				extInfo = new PoiExtInfo();
				while (keys.hasNext()) {
					String key = keys.next();
					extInfo.put(key, extMap.get(key));
				}
			} else {
				extInfo = null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 获取poi的id
	 * 
	 * @return poi的id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 获取poi的名称
	 * 
	 * @return poi的名称
	 */
	public String getName() {
		return basicInfo.name;
	}
	
	/**
	 * 获取databox的id
	 * 
	 * @return databox的id
	 */
	public int getDataboxId() {
		return databoxId;
	}
	
	/**
	 * 获取地址
	 * 
	 * @return 地址
	 */
	public String getAddress() {
		return basicInfo.address;
	}
	
	/**
	 * 获取电话
	 * 
	 * @return 电话
	 */
	public String getTelephone() {
		return basicInfo.telephone;
	}
	/**
	 * 获取邮编
	 * 
	 * @return 邮编
	 */
	public String getZipCode() {
		return basicInfo.zipCode;
	}
	
	/**
	 * 获取省份ID
	 * 
	 * @return 省份ID
	 */
	public int getProvinceId() {
		return basicInfo.provinceId; 
	}
	
	/**
	 * 获取城市ID
	 * 
	 * @return 城市ID
	 */
	public int getCityId() {
		return basicInfo.cityId;
	}
	
	/**
	 * 获取地区ID
	 * 
	 * @return 地区ID
	 */
	public int getDistrictId() {
		return basicInfo.districtId;
	}
	
	/**
	 * 获取省份名称
	 * 
	 * @return 省份名称
	 */
	public String getProvince() {
		return basicInfo.province;
	}
	
	/**
	 * 获取城市名称
	 * 
	 * @return 城市名称
	 */
	public String getCity() {
		return basicInfo.city;
	}
	
	/**
	 * 获取地区名称
	 * 
	 * @return 地区名称
	 */
	public String getDistrict() {
		return basicInfo.district;
	}
	
	/**
	 * 获取纬度
	 * 
	 * @return 纬度
	 */
	public double getLatitude() {
		return basicInfo.location.getLatitude();
	}
	
	/**
	 * 获取经度
	 * 
	 * @return 经度
	 */
	public double getLongitude() {
		return basicInfo.location.getLongitude();
	}
	
	/**
	 * 获取坐标
	 * 
	 * @return 坐标
	 */
	public GeoPoint getLocation() {
		return basicInfo.location;
	}
	
	/**
	 * 获取Poi标签
	 * 
	 * @return Poi标签
	 */
	public String[] getPoiTag() {
		return basicInfo.poiTag;
	}
	
	/**
	 * 获取类型ID
	 * 
	 * @return 类型ID
	 */
	public int getCategoryId() {
		return basicInfo.categoryId;
	}
	
	/**
	 * 获取Poi的重要性
	 * 
	 * @return Poi的重要性
	 */
	public int getPoiImportance() {
		return basicInfo.poiImportance;
	}
	
	/**
	 * 获取POI创建时间
	 * 
	 * @return 创建时间
	 */
	public String getCreateTime() {
		return basicInfo.createTime;
	}
	
	/**
	 * 获取POI图标
	 * 
	 * @return POI图标
	 */
	public String getIcon() {
		return basicInfo.icon;
	}
	
	/**
	 * 获取POI的百度UID
	 * 
	 * @return 百度UID
	 */
	public Long getBaiduUid() {
		return basicInfo.baiduUid;
	}
	
	/**
	 * 获取POI扩展信息
	 * 
	 * @return POI扩展信息
	 */
	public PoiExtInfo getExt() {
		return extInfo;
	}
	/**
	 * 获取POI基础信息
	 * 
	 * @return POI基础信息
	 */
	public PoiBasicInfo getBasic() {
		return basicInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName()).append("[")
				.append("id=").append(id).append(",").append("basicInfo=")
				.append(basicInfo).append(",").append("extInfo=");
		if(null != extInfo) {
			sb.append(extInfo.toString()).append("]");
		} else {
			sb.append("null]");
		}
		return sb.toString();
	}
}
