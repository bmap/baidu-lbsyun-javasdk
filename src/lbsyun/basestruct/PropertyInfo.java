package lbsyun.basestruct;

import java.util.HashMap;
import java.util.Map;

import lbsyun.ServiceException;
import lbsyun.constant.PropertyType;




/**
 * Databox_meta的信息
 * 
 * @author kuangzhijie
 *
 */
public class PropertyInfo {
	/**
	 * 属性中文名称
	 */
	public String name;
	/**
	 * 属性的KEY，与属性唯一对应
	 */
	public String key;
	/**
	 * 属性的类型，包括INT32 | INT64 | FLOAT | DOUBLE | STRING
	 */
	public PropertyType type;
	/**
	 * 数值类型时，是否为排序字段
	 */
	public boolean magic;
	
	/**
	 * 获取属性名称
	 * 
	 * @return 属性名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置属性名称
	 * 
	 * @param name 属性名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取属性关键字
	 * 
	 * @return 属性关键字
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 设置属性关键字
	 * 
	 * @param key 属性关键字
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 获取属性字段类型
	 * 
	 * @return 属性字段类型
	 */
	public PropertyType getType() {
		return type;
	}

	/**
	 * 设置属性字段类型
	 * 
	 * @param type 属性字段类型
	 */
	public void setType(PropertyType type) {
		this.type = type;
	}

	/**
	 * 获取是否排序
	 * 
	 * @return 排序与否
	 */
	public boolean isMagic() {
		return magic;
	}

	/**
	 * 设置是否排序
	 * 
	 * @param magic 排序与否
	 */
	public void setMagic(boolean magic) {
		this.magic = magic;
	}

	/**
	 * 无参数的构造
	 */
	public PropertyInfo() {
		init();
	}
	
	/**
	 * 基于属性名、关键字、类型的构造
	 * 
	 * @param name 属性名
	 * @param key 属性关键字
	 * @param type 属性类型
	 */
	public PropertyInfo(String name, String key, PropertyType type) {
		this.name = name;
		this.key = key;
		this.type = type;
		this.magic = false;
	}
	
	/**
	 * 基于属性名、关键字、类型、排序与否的构造
	 * 
	 * @param name 属性名
	 * @param key 属性关键字
	 * @param type 属性类型
	 * @param magic 排序与否
	 */
	public PropertyInfo(String name, String key, PropertyType type, boolean magic) {
		this.name = name;
		this.key =key;
		this.type = type;
		this.magic = magic;
	}
	
	/**
	 * 初始化
	 */
	public void init() {
		name = null;
		key = null;
		type = null;
		magic = false;
	}
	
	/**
	 * 把查询条件转化成参数键值对，用于检索请求
	 * 
	 * @return 参数键值对
	 * @throws ServiceException
	 */
	public HashMap<String, String> toParams() {
		HashMap<String, String> params = new HashMap<String, String>();
		injectParams(params, this);
		return params;
	}
	
	/**
	 * 把查询条件注入已有参数键值对中，用于检索请求
	 * 
	 * @param params 参数键值对
	 * @param info 查询信息
	 * @throws ServiceException
	 */
	public static void injectParams(Map<String, String> params, PropertyInfo info) {
		if (null != info.name) {
			params.put("property_name", info.name);
		}
		if (null != info.key) {
			params.put("property_key", info.key);
		}
		if (null != info.type){
			params.put("property_type", info.type.toString());
		}
		if (info.magic) {
			params.put("if_magic_field", "1");
		} else {
			params.put("if_magic_field", "0");
		}
	}

}
