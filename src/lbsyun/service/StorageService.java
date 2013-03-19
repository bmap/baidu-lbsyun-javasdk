package lbsyun.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lbsyun.ServiceException;
import lbsyun.basestruct.BasicQueryInfo;
import lbsyun.basestruct.GeoPoint;
import lbsyun.basestruct.PoiBasicInfo;
import lbsyun.basestruct.PoiExtInfo;
import lbsyun.basestruct.PropertyInfo;
import lbsyun.constant.*;
import lbsyun.http.BaseHttpClient;
import lbsyun.org.json.JSONArray;
import lbsyun.org.json.JSONException;
import lbsyun.org.json.JSONObject;
import lbsyun.response.Databox;
import lbsyun.response.DataboxMeta;
import lbsyun.response.DataboxPage;
import lbsyun.response.Poi;
import lbsyun.response.PoiPage;
import lbsyun.utils.JsonUtil;
import lbsyun.utils.StringUtil;





/**
 * LBS云存储服务类
 * 
 * @author kuangzhijie
 * 
 */
public class StorageService extends BaseService {

	/**
	 * 请求的基础URI
	 */
	public static final String BASEURI = "/geodata";

	/**
	 * 基于HTTP请求实例的构造
	 * 
	 * @param invoker HTTP请求实例
	 */
	public StorageService(BaseHttpClient invoker) {
		super(invoker);
	}
	
	/**
	 * 基于ak的构造
	 * 
	 * @param ak 用户ak
	 */
	public StorageService(String ak) {
		super(ak);
	}
	
	/**
	 * 基于ak、sk的构造
	 * 
	 * @param ak 用户ak
	 * @param sk 用户sk
	 */
	public StorageService(String ak, String sk) {
		super(ak, sk);
	}
	/**
	 * 基于ak、sk、host的构造
	 * 
	 * @param ak 用户ak
	 * @param sk 用户sk
	 * @param host 请求host
	 */
	public StorageService(String ak, String sk, String host) {
		super(ak, sk, host);
	}

	/**
	 * 创建databox 
	 * 对应API：{@link api.map.baidu.com/geodata/databox?method=create POST请求}
	 * 
	 * @param name
	 *            Databox的名称
	 * @return 返回新建的Databox的ID
	 * @throws ServiceException
	 
	  
	 */
	public int createDatabox(String name) throws ServiceException {
		return createDatabox(name, GeoType.POINT);
	}

	/**
	 * 创建databox 
	 * 对应API：{@link api.map.baidu.com/geodata/databox?method=create POST请求}
	 * 
	 * @param name
	 *            Databox的名称
	 * @param geotype
	 *            Poi点的类型，枚举值GeoType.Point | GeoType.Line |
	 *            GeoType.Flat
	 * @return 返回新建的Databox的ID
	 * @throws ServiceException
	 
	 
	 */
	public int createDatabox(String name, GeoType geotype)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/databox";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", name);
		parameters.put("geotype", geotype.toString());
		parameters.put("method", "create");
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return json.getInt("id");
			} else {
				return -1;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	/**
	 * 修改Databox，只支持改Databox名称 
	 * 对应API：{@link api.map.baidu.com/geodata/databox?method=update POST请求}
	 * 
	 * @param id
	 *            Databox的ID
	 * @param name
	 *            Databox的名称
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean updateDatabox(int id, String name)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/databox";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("id", Integer.toString(id));
		parameters.put("name", name);
		parameters.put("method", "update");
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}

	/**
	 * 删除Databox 
	 * 对应API：{@link api.map.baidu.com/geodata/databox/{id}?method=delete POST请求}
	 * 
	 * @param id
	 *            Databox的ID
	 * @return 成功返回true
	 * @throws ServiceException
	 
	  
	 */
	public boolean deleteDatabox(int id) throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/databox/" + Integer.toString(id);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "delete");
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}

	/**
	 * 获取单个Databox 
	 * 对应API：{@link api.map.baidu.com/geodata/databox/{id}? GET请求}
	 * 
	 * @param id
	 *            Databox的ID
	 * @return 返回获取得的Databox信息
	 * @throws ServiceException
	 */
	public Databox getDatabox(int id)
			throws ServiceException {
		return getDatabox(id, Scope.BASIC);
	}
	
	/**
	 * 获取单个Databox 
	 * 对应API：{@link api.map.baidu.com/geodata/databox/{id}? GET请求}
	 * 
	 * @param id
	 *            Databox的ID
	 * @param scope 获取Databox的信息的范围，包括基本信息、基本信息+扩展信息
	 * @return 返回获取得的Databox信息
	 * @throws ServiceException
	 */
	public Databox getDatabox(int id, Scope scope)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/databox/" + Integer.toString(id);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("scope", scope.toString());
		String ret = invoker.publicRequest(uri, parameters, "GET");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				if(json.isNull("databox")){
					return null;
				} else {
					return new Databox(json);
				}
			} else {
				return null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 条件查询databox
	 * 对应API：{@link api.map.baidu.com/geodata/databox?method=list GET请求}
	 * @param pageIndex 检索页索引
	 * @param pageSize 检索页大小
	 * @return 返回单页信息
	 * @throws ServiceException
	 */
	public DataboxPage listDatabox(int pageIndex, int pageSize) 
			throws ServiceException{
		return listDatabox(null, pageIndex, pageSize);
	}
	
	/**
	 * 条件查询databox
	 * 对应API：{@link api.map.baidu.com/geodata/databox?method=list GET请求}
	 * @param name 查询的databox名称，当为null时，则无查询条件
	 * @param pageIndex 查询的页面索引
	 * @param pageSize 查询的页面大小
	 * @return 返回单页信息
	 * @throws ServiceException
	 */
	public DataboxPage listDatabox(String name, int pageIndex, int pageSize)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/databox";
		Map<String, String> parameters = new HashMap<String, String>();
		if (null != name){
			parameters.put("name", name);
		}
		parameters.put("page_index", Integer.toString(pageIndex));
		parameters.put("page_size", Integer.toString(pageSize));
		parameters.put("method", "list");
		String ret = invoker.publicRequest(uri, parameters, "GET");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return new DataboxPage(json, "databox");
			} else {
				return null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 条件查询databox，并返回全部结果
	 * 对应API：{@link api.map.baidu.com/geodata/databox?method=list GET请求}
	 * @param name 查询的databox名称
	 * @return Databox列表
	 * @throws ServiceException
	 */
	public ArrayList<Databox> listAllDatabox(String name)
			throws ServiceException {
		resetStatus();
		ArrayList<Databox> databoxList = new ArrayList<Databox>();
		int pageSize = 1000;
		int pageIndex = 0;
		while(true){
			DataboxPage dataPage = listDatabox(name, pageIndex, pageSize);
			for(Databox databox : dataPage.getDataboxList()) {
				databoxList.add(databox);
			}
			if(pageSize != dataPage.getSize()){
				break;
			}
			++pageIndex;
		}
		return databoxList;
	}
	
	/**
	 * 创建单个databox_meta
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta?method=create POST请求}
	 * @param databoxId Databox的Id
	 * @param propertyName 属性名
	 * @param propertyKey 属性关键字
	 * @param propertyType 属性类型，枚举值：INT32 | INT64 | FLOAT | DOUBLE | STRING 
	 * @return 返回新增的databox_meta的id，当此databox_meta之前已经创建好时，就返回0
	 * @throws ServiceException
	 */
	public int createDataboxMeta(int databoxId, String propertyName,
			String propertyKey, PropertyType propertyType) 
					throws  ServiceException {
		return createDataboxMeta(databoxId, propertyName, propertyKey, propertyType,
				false);
	}

	
	
	/**
	 * 创建单个databox_meta 
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta?method=create POST请求}
	 * 
	 * @param databoxId
	 *            Databox的Id
	 * @param propertyName
	 *            属性名
	 * @param propertyKey
	 *            属性关键字
	 * @param propertyType
	 *            属性类型，枚举值：INT32 | INT64 | FLOAT | DOUBLE | STRING
	 * @param ifMagicField
	 *            是否为排序字段
	 * @return 返回新增的databox_meta的id，当此databox_meta之前已经创建好时，就返回0，若创建不成功返回-1
	 
	 
	 * @throws ServiceException
	 */
	public int createDataboxMeta(int databoxId, String propertyName,
			String propertyKey, PropertyType propertyType,
			boolean ifMagicField)
					throws  ServiceException {
		resetStatus();
		String uri = BASEURI + "/databoxmeta";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("property_name", propertyName.trim());
		parameters.put("property_key", propertyKey.trim());
		parameters.put("property_type", propertyType.toString().trim());
		parameters.put("databox_id", Integer.toString(databoxId));
		if (ifMagicField) {
			parameters.put("if_magic_field", "1");
		} else {
			parameters.put("if_magic_field", "0");
		}
		parameters.put("method", "create");
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			
			status = json.getInt("status");
			if (status == 0) {
				JSONArray jsonArray = json.getJSONArray("ids");
				int length = jsonArray.length();
				if(length > 0) {
					return jsonArray.getInt(0);
				}else{
					return 0;
				}
			} else {
				return -1;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}	
	}
	
	/**
	 * 创建单个databox_meta
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta?method=create POST请求}
	 * @param databoxId databox_meta所属databox的id
	 * @param property databox_meta的属性参数
	 * @return 创建的databox_meta的id，当此databox_meta之前已经创建好时，就返回0
	 * @throws ServiceException
	 */
	public int createDataboxMeta(int databoxId, PropertyInfo property) throws ServiceException {
		return createDataboxMeta(databoxId, property.getName(), property.getKey(), property.getType(), property.isMagic());
	}
	
	/**
	 * 创建多个databox_meta
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta?method=create POST请求}
	 * @param databoxId Databox的Id
	 * @param propertyNameArray 属性名数组
	 * @param propertyKeyArray 属性键数组
	 * @param propertyTypeArray 属性值类型数组
	 * @return 返回新增的databox_meta的id，当此databox_meta之前都已经创建好时，就返回空数组
	 
	 
	 * @throws ServiceException
	 */
	public ArrayList<Integer> createDataboxMetaSet(int databoxId,
			String[] propertyNameArray, String[] propertyKeyArray,
			PropertyType[] propertyTypeArray) throws ServiceException {
		return createDataboxMetaSet(databoxId, propertyNameArray,
				propertyKeyArray, propertyTypeArray, null);
	}
	
	/**
	 * 创建多个databox_meta
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta?method=create POST请求}
	 * @param databoxId Databox的Id
	 * @param propertyNameArray 属性名数组
	 * @param propertyKeyArray 属性键数组
	 * @param propertyTypeArray 属性值类型数组 
	 * @param ifMagicField 是否为排序字段数组，0或1
	 * @return 返回新增的databox_meta的id集合，当此databox_meta之前都已经创建好时，就返回空数组
	 
	 
	 * @throws ServiceException
	 */
	public ArrayList<Integer> createDataboxMetaSet(int databoxId,
			String[] propertyNameArray, String[] propertyKeyArray,
			PropertyType[] propertyTypeArray, int[] ifMagicFieldArray)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/databoxmeta";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("property_name", StringUtil.join(propertyNameArray, ","));
		parameters.put("property_key", StringUtil.join(propertyKeyArray, ","));
		parameters.put("property_type", StringUtil.join(
				PropertyType.toStringArray(propertyTypeArray), ","));
		parameters.put("databox_id", Integer.toString(databoxId));
		if (null != ifMagicFieldArray) {
			parameters.put("if_magic_field", StringUtil.join(ifMagicFieldArray, ","));
		}
		parameters.put("method", "create");
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			
			status = json.getInt("status");
			if (status == 0) {
				JSONArray jsonArray = json.getJSONArray("ids");
				int length = jsonArray.length();
				if(length > 0) {
					return JsonUtil.JSONArrayToIntList(jsonArray);
				}else{
					return new ArrayList<Integer>();
				}
			} else {
				return null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}	
	}
	
	/**
	 * 创建多个databox_meta
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta?method=create POST请求}
	 * @param databoxId databox_meta所属的databox的id
	 * @param properties databox_meta的属性集合
	 * @return 返回新增的databox_meta的id集，当此databox_meta之前都已经创建好时，就返回空数组
	 * @throws ServiceException
	 */
	public ArrayList<Integer> createDataboxMetaSet(int databoxId,
			PropertyInfo[] properties) throws ServiceException {
		int len = properties.length;
		String[] propertyNames = new String[len];
		String[] propertyKeys = new String[len];
		PropertyType[] propertyTypes = new PropertyType[len];
		int[] propertyMagics = new int[len];
		for (int i = 0; i < len; ++i) {
			propertyNames[i] = properties[i].getName();
			propertyKeys[i] = properties[i].getKey();
			propertyTypes[i] = properties[i].getType();
			if (properties[i].isMagic()) {
				propertyMagics[i] = 1;
			} else {
				propertyMagics[i] = 0;
			}
		}
		return createDataboxMetaSet(databoxId, propertyNames, propertyKeys,
				propertyTypes, propertyMagics);
	}
	
	/**
	 * 修改databox_meta名称
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta/{id}?method=update POST请求}
	 * @param id databox_meta的id
	 * @param propertyName 属性名称
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean updateDataboxMetaName(int id, String propertyName) 
			throws ServiceException {
		return updateDataboxMeta(id, propertyName, false);
	}

	/**
	 * 修改databox_meta
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta/{id}?method=update POST请求}
	 * @param id databox_meta的id
	 * @param propertyName 属性名称
	 * @param magic 是否为排序字段
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean updateDataboxMeta(int id, String propertyName, boolean magic) 
			throws ServiceException  {
		String uri = BASEURI + "/databoxmeta/" + id;
		Map<String, String> parameters = new HashMap<String, String>();
		if (null != propertyName) {
			parameters.put("property_name", propertyName.trim());
		}
		if (magic) {
			parameters.put("if_magic_field", "1");
		} else {
			parameters.put("if_magic_field", "0");
		}
		parameters.put("method", "update");
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 修改databox_meta排序字段
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta/{id}?method=update POST请求}
	 * @param id databox_meta的id
	 * @param propertyName 属性名称
	 * @param magic 是否为排序字段
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean updateDataboxMetaMagic(int id, boolean magic) 
			throws ServiceException  {
		String uri = BASEURI + "/databoxmeta/" + id;
		Map<String, String> parameters = new HashMap<String, String>();
		if (magic) {
			parameters.put("if_magic_field", "1");
		} else {
			parameters.put("if_magic_field", "0");
		}
		parameters.put("method", "update");
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 修改databox_meta
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta/{id}?method=delete POST请求}
	 * @param id databox_meta的id
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean deleteDataboxMeta(int id) 
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/databoxmeta/" + id;
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "delete");
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	/**
	 * 获取databox_meta信息
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta/{id} GET请求}
	 * @param id databox_meta的id
	 * @return 返回databox_meta信息
	 * @throws ServiceException
	 */
	public DataboxMeta getDataboxMeta(int id) 
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/databoxmeta/" + id;
		Map<String, String> parameters = new HashMap<String, String>();		
		String ret = invoker.publicRequest(uri, parameters, "GET");
		try {
			JSONObject json = new JSONObject(ret);
			int status = json.getInt("status");
			if (status == 0) {
				return new DataboxMeta(json.getJSONObject("databox_meta"));
			} else {
				return null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 查询所有的databox_meta
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta?method=list GET请求}
	 * @param databoxId databox的id
	 * @return 返回databox_meta的列表数组
	 * @throws ServiceException
	 */
	public ArrayList<DataboxMeta> listDataboxMeta(int databoxId)
			throws ServiceException {
		return listDataboxMeta(databoxId, null, null);
	}
	
	/**
	 * 条件查询databox_meta
	 * 对应API：{@link api.map.baidu.com/geodata/databoxmeta?method=list GET请求}
	 * @param databoxId databox的id
	 * @param propertyName 属性名称
	 * @param propertyKey 属性关键字
	 * @return 返回databox_meta的列表数组
	 * @throws ServiceException
	 */
	public ArrayList<DataboxMeta> listDataboxMeta(int databoxId, 
			String propertyName, String propertyKey)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/databoxmeta";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("databox_id", Integer.toString(databoxId));
		if (null != propertyName){
			parameters.put("property_name", propertyName);
		}
		if (null != propertyKey) {
			parameters.put("property_key", propertyKey);
		}
		parameters.put("method", "list");
		String ret = invoker.publicRequest(uri, parameters, "GET");
		try {
			JSONObject json = new JSONObject(ret);
			int status = json.getInt("status");
			if (status == 0) {
				ArrayList<DataboxMeta> databoxMetaList = new ArrayList<DataboxMeta>();
				JSONArray databoxMetaArray = json.getJSONArray("databox_meta");
				int length = databoxMetaArray.length();
				for (int i = 0; i < length; ++i) {
					databoxMetaList.add(new DataboxMeta(databoxMetaArray
							.getJSONObject(i)));
				}
				return databoxMetaList;
			} else {
				return null;
			}
		}  catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 创建POI，只有必选参数
	 * 对应API：{@link api.map.baidu.com/geodata/poi?method=create POST请求}
	 * @param name POI名称
	 * @param lat POI纬度
	 * @param lon POI经度
	 * @param coordType POI坐标加密类型
	 * @param databoxId POI所属databox的id 
	 * @return 新建的POI的id，不成功返回-1
	 * @throws ServiceException
	 */
	public long createPoi(String name, double lat, double lon, CoordType coordType, int databoxId)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", name);
		parameters.put("original_lat", Double.toString(lat));
		parameters.put("original_lon", Double.toString(lon));
		parameters.put("original_coord_type", coordType.toString());
		parameters.put("databox_id", Integer.toString(databoxId));
		parameters.put("method", "create");
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			int status = json.getInt("status");
			if (status == 0) {
				return json.getLong("id");
			} else {
				return -1;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 创建POI，只有必选参数
	 * 对应API：{@link api.map.baidu.com/geodata/poi?method=create POST请求}
	 * @param name POI名称
	 * @param pt POI的坐标
	 * @param coordType POI的坐标加密类型，有GOV | BAIDU | NONE
	 * @param databoxId POI所在的Databox的Id
	 * @return
	 * @throws ServiceException
	 */
	public long createPoi(String name, GeoPoint pt, CoordType coordType, int databoxId)
			throws ServiceException {
		return createPoi(name, pt.getLatitude(), pt.getLongitude(), coordType, databoxId);
	}
	/**
	 * 创建POI，包括必选与可选参数
	 * 对应API：{@link api.map.baidu.com/geodata/poi?method=create POST请求}
	 * @param name POI名称
	 * @param lat POI纬度
	 * @param lon POI经度
	 * @param coordType POI坐标加密类型
	 * @param databoxId POI所属databox的id 
	 * @param opts POI的可选参数
	 * 	-address 地址
	 * 	-telephone 电话
	 * 	-zip_code 邮编
	 * 	-poi_tag POI标签，用半角逗号隔开
	 * 	-category_id POI的类别ID
	 * 	-geo_sequence 坐标序列，当为线面POI时，必须不为空，格式为：lon1,lat1|lon2,lat2
	 * 				  可通过LbsUtil.getGeoSequence获得。
	 * 	-icon POI的图标
	 * 	-baidu_uid 百度的poiuid
	 * @return 新建的POI的id，不成功返回-1
	 * @throws ServiceException
	 */
	public long createPoi(String name, double lat, double lon, 
			CoordType coordType, int databoxId, Map<String, String> opts)
			throws ServiceException{
		resetStatus();
		String uri = BASEURI + "/poi";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", name);
		parameters.put("original_lat", Double.toString(lat));
		parameters.put("original_lon", Double.toString(lon));
		parameters.put("original_coord_type", coordType.toString());
		parameters.put("databox_id", Integer.toString(databoxId));
		parameters.put("method", "create");
		
		if(opts != null) {
			parameters.putAll(opts);
		}
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			int status = json.getInt("status");
			if (status == 0) {
				return json.getLong("id");
			} else {
				return -1;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 创建POI，包括必选与可选参数
	 * 对应API：{@link api.map.baidu.com/geodata/poi?method=create POST请求}
	 * @param opts POI的可选参数
	 * 	-name 必选，POI名称
	 * 	-original_lat 必选，POI纬度
	 * 	-original_lon 必选，POI经度
	 * 	-original_coord_type 必选，POI坐标加密类型
	 * 	-databox_id 必选，POI所属databox的id
	 * 	-address 可选，地址
	 * 	-telephone 可选，电话
	 * 	-zip_code 可选，邮编
	 * 	-poi_tag 可选，POI标签，用半角逗号隔开
	 * 	-category_id 可选，POI的类别ID
	 * 	-geo_sequence 可选，坐标序列，当为线面POI时，必须不为空，格式为：lat1,lon1|lat2,lon2
	 * 	-icon 可选，POI的图标
	 * 	-baidu_uid 可选，百度的poiuid
	 * @return 新建的POI的id，不成功返回-1
	 * @throws ServiceException
	 */
	public long createPoi(Map<String, String> opts)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "create");
		
		if(opts != null) {
			parameters.putAll(opts);
		}
		if(!checkParam(new String[]{"name", "original_lat", 
				"original_lon", "original_coord_type", "databox_id"}, 
				parameters)) {
			throw new ServiceException("Required parameters is in lack!");
		}
		
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			int status = json.getInt("status");
			if (status == 0) {
				return json.getLong("id");
			} else {
				return -1;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	/**
	 * 创建POI，通过PoiBasicInfo创建
	 * 对应API：{@link api.map.baidu.com/geodata/poi?method=create POST请求}
	 * 
	 * @param info POI的基本信息
	 * @return 新建的POI的id，不成功返回-1
	 * @throws ServiceException
	 */
	public long createPoi(int databoxId, PoiBasicInfo info) throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "create");
		parameters.put("databox_id", Integer.toString(databoxId));		
		PoiBasicInfo.injectParams(parameters, info);
		
		if(!checkParam(new String[]{"name", "original_lat", 
				"original_lon", "original_coord_type", "databox_id"}, 
				parameters)) {
			throw new ServiceException("Required parameters is in lack!");
		}
		
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			int status = json.getInt("status");
			if (status == 0) {
				return json.getLong("id");
			} else {
				return -1;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 修改POI基本参数，通过Map修改
	 * 对应API：{@link api.map.baidu.com/geodata/poi?method=update POST请求}
	 * @param poiId POI的ID
	 * @param opts POI的可选参数
	 * 	-name POI名称
	 * 	-original_lat POI纬度
	 * 	-original_lon POI经度
	 * 	-original_coord_type POI坐标加密类型
	 * 	-address 地址
	 * 	-telephone 电话
	 * 	-zip_code 邮编
	 * 	-poi_tag POI标签，用半角逗号隔开
	 * 	-category_id POI的类别ID
	 * 	-geo_sequence 坐标序列，当为线面POI时，必须不为空，格式为：lat1,lon1|lat2,lon2
	 * 	-icon POI的图标
	 * 	-baidu_uid 百度的poiuid
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean updatePoi(long poiId, Map<String, String> opts)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi/" + poiId;
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "update");
		
		if(opts != null) {
			parameters.putAll(opts);
		}
		
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 修改POI基本参数，通过PoiBasicInfo修改
	 * 对应API：{@link api.map.baidu.com/geodata/poi?method=update POST请求}
	 * @param poiId POI的ID
	 * @param info 修改的POI信息
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean updatePoi(long poiId, PoiBasicInfo info)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi/" + poiId;
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "update");
		
		PoiBasicInfo.injectParams(parameters, info);
		
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	
	/**
	 * 删除单个POI
	 * 对应API：{@link api.map.baidu.com/geodata/poi/{id}?method=delete POST请求}
	 * @param poiId POI的ID
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean deletePoi(long poiId) throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi/" + poiId;
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "delete");
		
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	/**
	 * 删除多个POI
	 * 对应API：{@link api.map.baidu.com/geodata/poi?method=delete POST请求}
	 * @param ids POI的ID集合
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean deletePoiSet(long[] ids) throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("ids", StringUtil.join(ids, ","));
		parameters.put("method", "delete");
		
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	/**
	 * 获取单个POI
	 * 对应API：{@link api.map.baidu.com/poi/{id}? GET请求}
	 * @param poiId POI的ID
	 * @return 返回POI信息
	 * @throws ServiceException
	 */
	public Poi getPoi(long poiId) throws ServiceException {
		return getPoi(poiId, Scope.BASIC);
	}

	/**
	 * 获取单个POI
	 * 对应API：{@link api.map.baidu.com/geodata/poi/{id}? GET请求}
	 * @param poiId POI的ID
	 * @param scope 检索结果范围，枚举值Scope.Basic | Scope.Detail
	 * @return 返回POI信息
	 * @throws ServiceException
	 */
	public Poi getPoi(long poiId, Scope scope) throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi/" + poiId;
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("scope", scope.toString());
		
		String ret = invoker.publicRequest(uri, parameters, "GET");
		try {
			JSONObject json = new JSONObject(ret);
			int status = json.getInt("status");
			if (status == 0) {
				return new Poi(json.getJSONObject("poi"));
			} else {
				return null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	

	
	/**
	 * 条件查询POI，通过Map设定条件，检索页索引与大小需在Map中设定
	 * 对应API：{@link api.map.baidu.com/geodata/poi?method=list GET请求}
	 * @param databoxId Databox的ID
	 * @param opts
	 * 	-name POI的名称
	 * 	-poi_tag 标签，只支持一个
	 * 	-poi_importance	POI重要性
	 * 	-bounds	矩形框，格式：lon1,lat1;lon2,lat2分别代表矩形左上角与右下角
	 * 	-province_id 省份id
	 * 	-province 省份名称
	 * 	-city_id 城市id
	 * 	-city 城市名称
	 * 	-district_id 地区id
	 * 	-district 地区名称
	 * 	-start_date	开始时间
	 * 	-end_date 截止时间
	 * 	-page_index 分页索引
	 * 	-page_size 分页大小
	 * @return
	 * @throws ServiceException
	 */
	public PoiPage listPoi(int databoxId, Map<String, String> opts)
			throws ServiceException  {
		resetStatus();
		String uri = BASEURI + "/poi";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("databox_id", Integer.toString(databoxId));
		parameters.put("method", "list");
		
		if(opts != null) {
			parameters.putAll(opts);
		}		
		
		String ret = invoker.publicRequest(uri, parameters, "GET");
		try {
			JSONObject json = new JSONObject(ret);
			int status = json.getInt("status");
			if (status == 0) {
				return new PoiPage(json, "pois");
			} else {
				return null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 无条件查询POI
	 * 对应API：{@link api.map.baidu.com/geodata/poi?method=list GET请求}
	 * @param databoxId Databox的ID
	 * @param pageIndex 检索页索引
	 * @param pageSize 检索页大小
	 * @return 返回结果页
	 * @throws ServiceException
	 */
	public PoiPage listPoi(int databoxId, int pageIndex, int pageSize)
			throws ServiceException {
		return listPoi(databoxId, pageIndex, pageSize, new HashMap<String, String>());
	}
	
	/**
	 * 条件查询POI，通过Map设定查询条件
	 * 对应API：{@link api.map.baidu.com/geodata/poi?method=list GET请求}
	 * @param databoxId Databox的ID
	 * @param pageIndex 检索页索引
	 * @param pageSize 检索页大小
	 * @param opts 检索条件
	 * 	-name POI的名称
	 * 	-poi_tag 标签，只支持一个
	 * 	-poi_importance	POI重要性
	 * 	-bounds	矩形框，格式：lon1,lat1;lon2,lat2分别代表矩形左上角与右下角
	 * 	-province_id 省份id
	 * 	-province 省份名称
	 * 	-city_id 城市id
	 * 	-city 城市名称
	 * 	-district_id 地区id
	 * 	-district 地区名称
	 * 	-start_date	开始时间
	 * 	-end_date 截止时间
	 * @return 结果页
	 * @throws ServiceException
	 */
	public PoiPage listPoi(int databoxId, int pageIndex, int pageSize,
			Map<String, String> opts) throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("databox_id", Integer.toString(databoxId));
		parameters.put("method", "list");
		
		if(opts != null && !opts.isEmpty()) {
			parameters.putAll(opts);
		}
		parameters.put("page_index", Integer.toString(pageIndex));
		parameters.put("page_size", Integer.toString(pageSize));
		
		String ret = invoker.publicRequest(uri, parameters, "GET");
		try {
			JSONObject json = new JSONObject(ret);
			int status = json.getInt("status");
			if (status == 0) {
				return new PoiPage(json, "pois");
			} else {
				return null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 条件查询POI
	 * 对应API：{@link api.map.baidu.com/geodata/poi?method=list GET请求}
	 * 
	 * @param databoxId Databox的ID
	 * @param pageIndex 检索页索引
	 * @param pageSize 检索页大小
	 * @param query 查询条件
	 * @return 结果页
	 * @throws ServiceException
	 */
	public PoiPage listPoi(int databoxId, int pageIndex, int pageSize, BasicQueryInfo query) throws ServiceException {
		return listPoi(databoxId, pageIndex, pageSize, query.toParams());
	}
	
	/**
	 * 查询POI，并返回所有的POI
	 * @param databoxId
	 * @return POI列表
	 * @throws ServiceException
	 */
	public ArrayList<Poi> listAllPoi(int databoxId)
			throws ServiceException {
		return listAllPoi(databoxId, null);
	}
	
	/**
	 * 条件查询POI，并返回所有的POI
	 * @param databoxId Databox的ID
	 * @param opts 查询条件
	 * 	-name POI的名称
	 * 	-poi_tag 标签，只支持一个
	 * 	-poi_importance	POI重要性
	 * 	-bounds	矩形框，格式：lat1,lon1;lat2,lon2分别代表矩形左上角与右下角
	 * 	-province_id 省份id
	 * 	-province 省份名称
	 * 	-city_id 城市id
	 * 	-city 城市名称
	 * 	-district_id 地区id
	 * 	-district 地区名称
	 * 	-start_date	开始时间，格式如"2012-11-11"
	 * 	-end_date 截止时间，格式如"2012-11-11"
	 * @return POI列表
	 * @throws ServiceException
	 */
	public ArrayList<Poi> listAllPoi(int databoxId, Map<String, String> opts)
			throws ServiceException {
		int pageSize = 1000;
		int pageIndex = 0;
		ArrayList<Poi> poiList = new ArrayList<Poi>();
		while(true) {
			PoiPage page = listPoi(databoxId, pageIndex, pageSize, opts);
			for(Poi poi : page.getPoiList()) {
				poiList.add(poi);
			}
			if(page.getSize() != pageSize){
				break;
			}
			++pageIndex;
		}
		return poiList;
	}
	/**
	 * 创建POI中的POIEXT的数据
	 * 对应API：{@link api.map.baidu.com/geodata/poiext?method=create POST请求}
	 * @param poiId POI的ID
	 * @param ext 由databox_meta决定
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean createPoiExt(long poiId, Map<String, String> ext)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poiext";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("poi_id", Long.toString(poiId));
		parameters.put("method", "create");
		
		if(ext != null) {
			parameters.putAll(ext);
		}
		
		
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 创建POI中的POIEXT的数据
	 * 对应API：{@link api.map.baidu.com/geodata/poiext?method=create POST请求}
	 * @param poiId POI的ID
	 * @param ext 由databox_meta决定
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean createPoiExt(long poiId, PoiExtInfo ext)
			throws ServiceException {
		return createPoiExt(poiId, ext.toHashMap());
	}
	
	/**
	 * 修改POI中的POIEXT的数据
	 * 对应API：{@link api.map.baidu.com/geodata/poiext?method=update POST请求}
	 * @param poiId POI的ID
	 * @param ext 由databox_meta决定
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean updatePoiExt(long poiId, Map<String, String> ext)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poiext";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("poi_id", Long.toString(poiId));
		parameters.put("method", "update");
		
		if(ext != null) {
			parameters.putAll(ext);
		}
		
		
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 修改POI中的POIEXT的数据
	 * 对应API：{@link api.map.baidu.com/geodata/poiext?method=update POST请求}
	 * 
	 * @param poiId POI的ID
	 * @param extInfo 由databox_meta决定
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean updatePoiExt(int poiId, PoiExtInfo extInfo)
			throws ServiceException {
		return updatePoiExt(poiId, extInfo.toHashMap());
	}
	
	/**
	 * 删除POI中的单个POIEXT的数据
	 * 对应API：{@link api.map.baidu.com/geodata/poiext?method=delete POST请求}
	 * @param poiId POI的ID
	 * @param propertyKey 要删除的poiext的property_key
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean deletePoiExt(long poiId, String propertyKey)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poiext";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("poi_id", Long.toString(poiId));
		parameters.put("keys", propertyKey.trim());
		parameters.put("method", "delete");
		String ret = invoker.publicRequest(uri, parameters, "POST");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 删除POI中的多个POIEXT的数据
	 * 对应API：{@link api.map.baidu.com/geodata/poiext?method=delete POST请求}
	 * @param poiId POI的ID
	 * @param propertyKey 要删除的poiext的property_key集合
	 * @return 成功返回true
	 * @throws ServiceException
	 */
	public boolean deletePoiExt(long poiId, String[] propertyKeys)
			throws ServiceException   {
		return deletePoiExt(poiId, StringUtil.join(propertyKeys, ","));
	}

	
	
	
}
