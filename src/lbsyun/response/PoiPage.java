package lbsyun.response;

import java.util.ArrayList;

import lbsyun.ServiceException;
import lbsyun.org.json.JSONObject;
import lbsyun.utils.JsonUtil;




/**
 * 查询POI的返回页
 * 
 * @author kuangzhijie
 *
 */
public class PoiPage extends Page {
	private ArrayList<Poi> poiList;

	/**
	 * 基于JSON字符串的构造
	 * 
	 * @param json JSON字符串
	 * @param poiKey SON字符串中内容列表的关键字
	 * @throws ServiceException
	 */
	public PoiPage(String json, String poiKey) throws ServiceException {
		init(json, poiKey);
	}

	/**
	 * 基于JSONObject的构造
	 * 
	 * @param jsonObject JSONObject实例
	 * @param poiKey JSONObject中内容列表的关键字
	 * @throws ServiceException
	 */
	public PoiPage(JSONObject jsonObject, String poiKey) throws ServiceException {
		init(jsonObject, poiKey);
	}
	
	
	public void init(String json, String poiKey) throws ServiceException {
		super.init(json, poiKey);
		poiList = JsonUtil.JSONArrayToPoiList(content);
	}

	
	public void init(JSONObject jsonObject, String poiKey) throws ServiceException {
		super.init(jsonObject, poiKey);
		poiList = JsonUtil.JSONArrayToPoiList(content);
	}
	
	
	
	/**
	 * 获取当前面的POI列表
	 * 
	 * @return POI列表
	 */
	public ArrayList<Poi> getPoiList() {
		return poiList;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(this.getClass().getSimpleName()).append("[")
				.append("total=").append(total).append(",")
				.append("size=").append(size).append(",")
				.append("poi_list=").append(poiList).append("]")
				.toString();
	}
	
}
