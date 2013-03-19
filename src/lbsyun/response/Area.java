package lbsyun.response;


import lbsyun.ServiceException;
import lbsyun.org.json.JSONException;
import lbsyun.org.json.JSONObject;


/**
 * 地区信息
 * 
 * @author kuangzhijie
 *
 */
public class Area  {

	private int id;
	private String name;
	private int poiSize;
	
	/**
	 * 基于JSON字符串的构造
	 * 
	 * @param json JSON字符串
	 * @throws ServiceException
	 */
	public Area(String json) throws ServiceException {
		init(json);
	}

	/**
	 * 基于JSONObject的构造
	 * 
	 * @param jsonObject JSONObject实例
	 * @throws ServiceException
	 */
	public Area(JSONObject jsonObject) throws ServiceException {
		init(jsonObject);
	}
	
	/**
	 * 基于JSON字符串的初始化
	 * 
	 * @param json JSON字符串
	 * @throws ServiceException
	 */
	public void init(String json) throws ServiceException {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
			id = jsonObject.getInt("area_id");
			name = jsonObject.getString("area_name");
			poiSize = jsonObject.getInt("poi_size");
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
			id = jsonObject.getInt("area_id");
			name = jsonObject.getString("area_name");
			poiSize = jsonObject.getInt("poi_size");
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 获取地区ID
	 * @return 地区ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 获取地区名
	 * @return 地区名
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 获取POI的个数
	 * @return POI的个数
	 */
	public int getPoiSize() {
		return poiSize;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(this.getClass().getSimpleName()).append("[")
				.append("id=").append(id).append(",").append("name=")
				.append(name).append(",").append("poi_size=")
				.append(poiSize).append("]").toString();
	}

}
