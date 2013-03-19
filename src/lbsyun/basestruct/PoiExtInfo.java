package lbsyun.basestruct;

import java.util.HashMap;
import java.util.Map;
/**
 * Poi的扩展信息
 * 
 * @author kuangzhijie
 *
 */
public class PoiExtInfo {
	private HashMap<String, String> info;
	
	/**
	 * 无参数构造
	 */
	public PoiExtInfo() {
		info = new HashMap<String, String>();
	}

	/**
	 * 基于参数Map的构造
	 * 
	 * @param infoMap 参数Map
	 */
	public PoiExtInfo(Map<String, String> infoMap) {
		info = new HashMap<String, String>();
		info.putAll(infoMap);
	}
	
	/**
	 * 添加或者修改键值对
	 * 
	 * @param key 键
	 * @param value 值
	 */
	public void put(String key, int value) {
		info.put(key, Integer.toString(value));
	}
	
	public void put(String key, Object value) {
		info.put(key, value.toString());
	}
	
	/**
	 * 添加或者修改键值对
	 * 
	 * @param key 键
	 * @param value 值
	 */
	public void put(String key, double value) {
		info.put(key, Double.toString(value));
	}
	
	/**
	 * 添加或者修改键值对
	 * 
	 * @param key 键
	 * @param value 值
	 */
	public void put(String key, long value) {
		info.put(key, Long.toString(value));
	}
	
	/**
	 * 添加或者修改键值对
	 * 
	 * @param key 键
	 * @param value 值
	 */
	public void put(String key, float value) {
		info.put(key, Float.toString(value));
	}
	
	/**
	 * 添加或者修改键值对
	 * 
	 * @param key 键
	 * @param value 值
	 */
	public void put(String key, String value) {
		info.put(key, value);
	}
	
	public void putAll(Map<String, String> infoMap) {
		info.putAll(infoMap);
	}
	
	/**
	 * 根据键获取字符串
	 * 
	 * @param key 键
	 * @return 值
	 */
	public String get(String key) {
		return info.get(key);
	}
	
	/**
	 * 根据键获取32位整型
	 * 
	 * @param key 键
	 * @return 值
	 */
	public int getInt(String key) {
		String value = info.get(key);
		return Integer.valueOf(value);
	}
	
	/**
	 * 根据键获取单精浮点数
	 * 
	 * @param key 键
	 * @return 值
	 */
	public float getFloat(String key) {
		String value = info.get(key);
		return Float.valueOf(value);
	}
	
	/**
	 * 根据键获取64位整型
	 * 
	 * @param key 键
	 * @return 值
	 */
	public long getLong(String key) {
		String value = info.get(key);
		return Long.valueOf(value);
	}
	
	/**
	 * 根据键获取双精浮点数
	 * 
	 * @param key 键
	 * @return 值
	 */
	public double getDouble(String key) {
		String value = info.get(key);
		return Double.valueOf(value);
	}
	
	/**
	 * 根据键获取字符串
	 * 
	 * @param key 键
	 * @return 值
	 */
	public String getString(String key) {
		return get(key);
	}
	
	/**
	 * 获取参数的键值对Map
	 * 
	 * @return 键值对Map
	 */
	public HashMap<String, String> toHashMap() {
		return info;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + info.toString();
	}
}
