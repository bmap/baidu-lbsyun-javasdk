package lbsyun.response;

import java.util.ArrayList;

import lbsyun.ServiceException;
import lbsyun.org.json.JSONArray;
import lbsyun.org.json.JSONException;
import lbsyun.org.json.JSONObject;




/**
 * Databox信息
 * 
 * @author kuangzhijie
 *
 */
public class Databox  {
	private String id;
	private String name;
	private int geotype;
	private String createTime;
	private int publishSearch;
	private int publishBus;
	private ArrayList<DataboxMeta> databoxMetaList;

	/**
	 * 基于JSON字符串的构造
	 * 
	 * @param json JSON字符串
	 * @throws ServiceException
	 */
	public Databox(String json) throws ServiceException {
		init(json);
	}

	/**
	 * 基于JSONObject的构造
	 * 
	 * @param jsonObject JSONObject实例
	 * @throws ServiceException
	 */
	public Databox(JSONObject jsonObject) throws ServiceException {
		init(jsonObject);
	}
	
	/**
	 * 基于jsonObject初始化
	 * 
	 * @param jsonObject JSONObject实例
	 * @throws ServiceException
	 */
	public void init(JSONObject jsonObject) throws ServiceException {
		try {
			JSONObject databox = null;
			if (jsonObject.isNull("databox")) {
				databox = jsonObject;
			} else {
				databox = jsonObject.getJSONObject("databox");
			}
			if (null == databox) {
				throw new ServiceException("response error");
			}
			id = databox.getString("id");
			name = databox.getString("name");
			geotype = databox.getInt("geotype");
			createTime = databox.getString("create_time");
			JSONObject publish = databox.getJSONObject("publish");
			publishSearch = publish.getInt("search");
			publishBus = publish.getInt("bus");
			databoxMetaList = new ArrayList<DataboxMeta>();
			if (!jsonObject.isNull("databox_meta")) {
				JSONArray databoxArray = jsonObject
						.getJSONArray("databox_meta");
				int length = databoxArray.length();
				for (int i = 0; i < length; ++i) {
					DataboxMeta databoxMeta = new DataboxMeta(
							databoxArray.getJSONObject(i));
					databoxMetaList.add(databoxMeta);
				}
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	/**
	 * 基于json字符串初始化
	 * 
	 * @param json json字符串
	 * @throws ServiceException
	 */
	public void init(String json) throws ServiceException {
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONObject databox = null;
			if (jsonObject.isNull("databox")) {
				databox = jsonObject;
			} else {
				databox = jsonObject.getJSONObject("databox");
			}
			if (null == databox) {
				throw new ServiceException("response error");
			}
			id = databox.getString("id");
			name = databox.getString("name");
			geotype = databox.getInt("geotype");
			createTime = databox.getString("create_time");
			JSONObject publish = databox.getJSONObject("publish");
			publishSearch = publish.getInt("search");
			publishBus = publish.getInt("bus");
			databoxMetaList = new ArrayList<DataboxMeta>();
			if (!jsonObject.isNull("databox_meta")) {
				JSONArray databoxArray = jsonObject
						.getJSONArray("databox_meta");
				int length = databoxArray.length();
				for (int i = 0; i < length; ++i) {
					DataboxMeta databoxMeta = new DataboxMeta(
							databoxArray.getJSONObject(i));
					databoxMetaList.add(databoxMeta);
				}
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
 	
	/**
	 * 获取Databox的Id
	 * @return Databox的Id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 获取Databox的名称
	 * @return Databox的名称
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 获取Databox中的Poi的类型，点线面
	 * @return Poi的类型，1：点；2：线；3：面
	 */
	public int getGeoType() {
		return geotype;
	}
	
	/**
	 * 获取Databox的创建时间
	 * @return 创建时间
	 */
		public String getCreateTime() {
		return createTime;
	}
	
	/**
	 * 获取检索发布情况
	 * 
	 * @return 发布返回true，否则返回false
	 */
	public boolean getPublishSearch() {
		return publishSearch == 1;
	}
	
	/**
	 * 获取公交发布情况
	 * 
	 * @return 发布返回true，否则返回false
	 */
	public boolean getPublishBus() {
		return publishBus == 1;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(this.getClass().getSimpleName()).append("[")
				.append("id=").append(id).append(",").append("name=")
				.append(name).append(",").append("geotype=").append(geotype)
				.append(",").append("create_time=").append(createTime)
				.append(",").append("search=").append(publishSearch)
				.append(",").append("bus=").append(publishBus).append("]")
				.append(databoxMetaList).toString();
	}
		
}
