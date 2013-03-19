package lbsyun.response;

import lbsyun.ServiceException;
import lbsyun.basestruct.PropertyInfo;
import lbsyun.constant.PropertyType;
import lbsyun.org.json.JSONException;
import lbsyun.org.json.JSONObject;


/**
 * Databox_meta信息
 * 
 * @author kuangzhijie
 *
 */
public class DataboxMeta  {
	private String id;
	private String databoxId;
	private PropertyInfo property;
	private String createTime;

	/**
	 * 基于JSON字符串获得信息
	 * 
	 * @param json 字符串
	 * @throws ServiceException
	 */
	public DataboxMeta(String json) throws ServiceException {
		init(json);
	}

	/**
	 * 基于JSONObject获得信息
	 * 
	 * @param jsonObject JSONObject
	 * @throws ServiceException
	 */
	public DataboxMeta(JSONObject jsonObject) throws ServiceException {
		init(jsonObject);
	}
	/**
	 * 基于JSON字符串获得信息
	 * 
	 * @param json 字符串
	 * @throws ServiceException
	 */
	public void init(String json) throws ServiceException {
		try {
			JSONObject jsonObject = new JSONObject(json);
			id = jsonObject.getString("id");
			databoxId = jsonObject.getString("databox_id");
			property.setName(jsonObject.getString("property_name"));
			property.setKey(jsonObject.getString("property_key"));
			property.setType(PropertyType.valueOf(jsonObject.getInt("property_type")));
			createTime = jsonObject.getString("create_time");
			property.setMagic((jsonObject.getInt("if_magic_field") == 1));
		} catch (JSONException je) {
			throw new ServiceException(je);
		}

	}

	/**
	 * 基于JSONObject获得信息
	 * 
	 * @param jsonObject JSONObject
	 * @throws ServiceException
	 */
	public void init(JSONObject jsonObject) throws ServiceException {
		try {
			id = jsonObject.getString("id");
			if (!jsonObject.isNull("databox_id")) {
				databoxId = jsonObject.getString("databox_id");
			}
			property = new PropertyInfo();
			property.setName(jsonObject.getString("property_name"));
			property.setKey(jsonObject.getString("property_key"));
			property.setType(PropertyType.valueOf(jsonObject.getInt("property_type")));
			createTime = jsonObject.getString("create_time");
			property.setMagic((jsonObject.getInt("if_magic_field") == 1));
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	
	/**
	 * 获取databox_meta的Id
	 * 
	 * @return databox_meta的Id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 获取Databox的id
	 * 
	 * @return Databox的id
	 */
	public String getDataboxId() {
		return databoxId;
	}
	
	/**
	 * 获取Databox_meta的property_name
	 * 
	 * @return 属性名称
	 */
	public String getPropertyName() {
		return property.getName();
	}
	
	/**
	 * 获取databox_meta的property_key
	 * 
	 * @return 属性关键字
	 */
	public String getPropertyKey() {
		return property.getKey();
	}
	
	/**
	 * 获取databox_meta的property_type
	 * 
	 * @return 属性类型
	 */
	public PropertyType getPropertyType() {
		return property.getType();
	}
	
	/**
	 * 获取databox_meta的创建时间
	 * 
	 * @return 创建时间
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * 查看databox_meta是否为排序字段
	 * 
	 * @return 是返回true，否则返回false
	 */
	public boolean getIfMagicField() {
		return property.isMagic();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(this.getClass().getSimpleName()).append("[")
				.append("id=").append(id).append(",")
				.append("databox_id=").append(databoxId).append(",")
				.append("property_name=").append(property.getName()).append(",")
				.append("property_key=").append(property.getKey()).append(",")
				.append("property_type=").append(property.getType().name()).append(",")
				.append("create_time=").append(createTime).append(",")
				.append("if_magic_field=").append(property.isMagic()).append("]")
				.toString();
	}
}
