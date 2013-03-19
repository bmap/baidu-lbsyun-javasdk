/**
 * 
 */
package lbsyun.service;

import java.util.HashMap;
import java.util.Map;

import lbsyun.ServiceException;
import lbsyun.basestruct.AdvancedQueryInfo;
import lbsyun.basestruct.Bounds;
import lbsyun.basestruct.GeoPoint;
import lbsyun.constant.*;
import lbsyun.http.BaseHttpClient;
import lbsyun.org.json.JSONException;
import lbsyun.org.json.JSONObject;
import lbsyun.response.AreaPage;
import lbsyun.response.Page;
import lbsyun.response.Poi;
import lbsyun.response.PoiPage;





/**
 * LBS云检索服务类
 * 
 * @author kuangzhijie
 * 
 */
public class SearchService extends BaseService {
	/**
	 * 请求的基础URI
	 */
	public static final String BASEURI = "/geosearch";

	/**
	 * 基于HTTP请求实例的构造
	 * 
	 * @param invoker HTTP请求实例
	 */
	public SearchService(BaseHttpClient invoker) {
		super(invoker);
	}
	
	/**
	 * 基于ak的构造
	 * 
	 * @param ak 用户ak
	 */
	public SearchService(String ak) {
		super(ak);
	}
	
	/**
	 * 基于ak、sk的构造
	 * 
	 * @param ak 用户ak
	 * @param sk 用户sk
	 */
	public SearchService(String ak, String sk) {
		super(ak, sk);
	}
	/**
	 * 基于ak、sk、host的构造
	 * 
	 * @param ak 用户ak
	 * @param sk 用户sk
	 * @param host 请求host
	 */
	public SearchService(String ak, String sk, String host) {
		super(ak, sk, host);
	}

	/**
	 * 通过省市区ID检索POI
	 * 对应API {@link api.map.baidu.com/geosearch/poi? GET请求}
	 * @param databoxId 检索databox的id
	 * @param regionId 省市区ID
	 * @param pageIndex 检索页索引
	 * @param pageSize 检索页大小
	 * @param query 检索条件
	 * @return 检索结果
	 * @throws ServiceException
	 */
	public Page searchRegion(int databoxId, int regionId, int pageIndex,
			int pageSize, AdvancedQueryInfo query) throws ServiceException {
		return searchRegion(databoxId, Integer.toString(regionId), pageIndex, pageSize, query);
	}

	/**
	 * 通过省市区名称检索POI
	 * 对应API {@link api.map.baidu.com/geosearch/poi? GET请求}
	 * @param databoxId 检索databox的id
	 * @param regionName 检索省市区的名称
	 * @param pageIndex 检索页索引
	 * @param pageSize 检索页大小
	 * @param query 检索条件
	 * @return 检索结果
	 * @throws ServiceException
	 */
	public Page searchRegion(int databoxId, String regionName,
			int pageIndex, int pageSize, AdvancedQueryInfo query)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi";

		Map<String, String> parameters = new HashMap<String, String>();
		AdvancedQueryInfo.injectParams(parameters, query);
		parameters.put("filter", AdvancedQueryInfo.getFilter(databoxId, query));
		parameters.put("page_index", Integer.toString(pageIndex));
		parameters.put("page_size", Integer.toString(pageSize));
		parameters.put("region", regionName);
		

		String ret = invoker.publicRequest(uri, parameters, "GET");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				if (json.isNull("type")) {
					return new Page();
				}
				if (json.getInt("type") == 1) {
					return new PoiPage(json, "content");
				} else {
					return new AreaPage(json, "content");
				}
			} else {
				return null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}

	/**
	 * 周边检索，范围为1000米
	 * 对应API {@link api.map.baidu.com/geosearch/poi? GET请求}
	 * @param databoxId 检索的databox的id
	 * @param location 检索点的坐标
	 * @param pageIndex 检索页索引
	 * @param pageSize 检索页大小
	 * @param query 检索条件
	 * @return 检索结果
	 * @throws ServiceException
	 */
	public Page searchNearby(int databoxId, GeoPoint location, int pageIndex,
			int pageSize, AdvancedQueryInfo query) throws ServiceException {
		return searchNearby(databoxId, location.getLatitude(), location.getLongitude(),
				pageIndex, pageSize, query);
	}
	
	/**
	 * 周边检索，范围为1000米
	 * 对应API {@link api.map.baidu.com/geosearch/poi? GET请求}
	 * @param databoxId 检索databox的id
	 * @param lat 经度
	 * @param lon 纬度
	 * @param pageIndex 检索页索引
	 * @param pageSize 检索页大小
	 * @param query 检索条件
	 * @return 检索结果
	 * @throws ServiceException
	 */
	public Page searchNearby(int databoxId, double lat, double lon,
			int pageIndex, int pageSize, AdvancedQueryInfo query)
			throws ServiceException {
		return searchNearby(databoxId, lat, lon, -1, pageIndex, pageSize, query);
	}

	/**
	 * 周边检索，范围自定义
	 * 对应API {@link api.map.baidu.com/geosearch/poi? GET请求}
	 * @param databoxId 检索databox的id
	 * @param radius 范围
	 * @param pageIndex 检索页索引
	 * @param pageSize 检索页大小
	 * @param query 检索条件
	 * @return 检索结果
	 * @throws ServiceException
	 */
	public Page searchNearby(int databoxId, GeoPoint location, int radius,
			int pageIndex, int pageSize, AdvancedQueryInfo query)
			throws ServiceException {
		return searchNearby(databoxId, location.getLatitude(), location.getLongitude(),
				radius, pageIndex, pageSize, query);
	}
	
	/**
	 * 周边检索，范围自定义
	 * 对应API {@link api.map.baidu.com/geosearch/poi? GET请求}
	 * @param databoxId 检索databox的id
	 * @param lat 经度
	 * @param lon 纬度
	 * @param radius 范围
	 * @param pageIndex 检索页索引
	 * @param pageSize 检索页大小
	 * @param query 检索条件
	 * @return 检索结果
	 * @throws ServiceException
	 */
	public Page searchNearby(int databoxId, double lat, double lon,
			int radius, int pageIndex, int pageSize, AdvancedQueryInfo query)
			throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi";

		Map<String, String> parameters = new HashMap<String, String>();
		AdvancedQueryInfo.injectParams(parameters, query);
		parameters.put("filter", AdvancedQueryInfo.getFilter(databoxId, query));
		parameters.put("page_index", Integer.toString(pageIndex));
		parameters.put("page_size", Integer.toString(pageSize));
		parameters.put("location", lat + "," + lon);
		if (radius > 0) {
			parameters.put("radius", Integer.toString(radius));
		}
		
		String ret = invoker.publicRequest(uri, parameters, "GET");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				if (json.isNull("type")) {
					return new Page();
				}
				if (json.getInt("type") == 1) {
					return new PoiPage(json, "content");
				} else {
					return new AreaPage(json, "content");
				}
			} else {
				return null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}


	/**
	 * 矩形框内检索
	 * 对应API {@link api.map.baidu.com/geosearch/poi? GET请求}
	 * @param databoxId 检索databox的id
	 * @param bounds 检索的矩形框
	 * @param pageIndex 检索页索引
	 * @param pageSize 检索页大小
	 * @param query 检索条件
	 * @return 检索结果
	 * @throws ServiceException
	 */
	public Page searchBounds(int databoxId, Bounds bounds, int pageIndex, int pageSize,
			AdvancedQueryInfo query) throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi";

		Map<String, String> parameters = new HashMap<String, String>();
		AdvancedQueryInfo.injectParams(parameters, query);
		parameters.put("filter", AdvancedQueryInfo.getFilter(databoxId, query));
		parameters.put("page_index", Integer.toString(pageIndex));
		parameters.put("page_size", Integer.toString(pageSize));
		parameters.put("bounds", bounds.toString());
		

		String ret = invoker.publicRequest(uri, parameters, "GET");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				if (json.isNull("type")) {
					return new Page();
				}
				if (json.getInt("type") == 1) {
					return new PoiPage(json, "content");
				} else {
					return new AreaPage(json, "content");
				}
			} else {
				return null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}
	
	/**
	 * 矩形框内检索
	 * 对应API {@link api.map.baidu.com/geosearch/poi? GET请求}
	 * @param databoxId 检索databox的id
	 * @param lon1 左下角的经度
	 * @param lat1 左下角的纬度
	 * @param lon2 右上角的经度
	 * @param lat2 右上角的纬度
	 * @param pageIndex 检索页索引
	 * @param pageSize 检索页大小
	 * @param query 检索条件
	 * @return 检索结果
	 * @throws ServiceException
	 */
	public Page searchBounds(int databoxId, double lat1, double lon1,
			double lat2, double lon2, int pageIndex, int pageSize,
			AdvancedQueryInfo query) throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/poi";

		Map<String, String> parameters = new HashMap<String, String>();
		AdvancedQueryInfo.injectParams(parameters, query);
		parameters.put("filter", AdvancedQueryInfo.getFilter(databoxId, query));
		parameters.put("page_index", Integer.toString(pageIndex));
		parameters.put("page_size", Integer.toString(pageSize));
		parameters.put("bounds", Bounds.toString(lat1, lon1, lat2, lon2));
		

		String ret = invoker.publicRequest(uri, parameters, "GET");
		try {
			JSONObject json = new JSONObject(ret);
			status = json.getInt("status");
			if (status == 0) {
				if (json.isNull("type")) {
					return new Page();
				}
				if (json.getInt("type") == 1) {
					return new PoiPage(json, "content");
				} else {
					return new AreaPage(json, "content");
				}
			} else {
				return null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}

	/**
	 * 详情查询
	 * 对应API {@link api.map.baidu.com/geosearch/detail? GET请求}
	 * @param poiId POI的ID
	 * @return POI信息
	 * @throws ServiceException
	 */
	public Poi searchDetail(long poiId, Scope scope) throws ServiceException {
		resetStatus();
		String uri = BASEURI + "/detail";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("id", Long.toString(poiId));
		if(null != scope){
			parameters.put("scope", scope.toString());
		}
		
		String ret = invoker.publicRequest(uri, parameters, "GET");
		try {
			JSONObject json = new JSONObject(ret);
			int status = json.getInt("status");
			if (status == 0) {
				return new Poi(json.getJSONObject("content"));
			} else {
				return null;
			}
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}

	

}
