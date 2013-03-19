package lbsyun.response;

import java.util.ArrayList;

import lbsyun.ServiceException;
import lbsyun.org.json.JSONObject;
import lbsyun.utils.JsonUtil;


public class AreaPage extends Page {
	private ArrayList<Area> areaList;
	/**
	 * 基于JSON字符串的构造
	 * 
	 * @param json JSON字符串
	 * @param areaKey JSON字符串中内容列表的关键字
	 * @throws ServiceException
	 */
	public AreaPage(String json, String areaKey) throws ServiceException {
		init(json, areaKey);
	}
	
	/**
	 * 基于JSONObject的构造
	 * 
	 * @param jsonObject JSONObject实例
	 * @param areaKey JSONObject中内容列表的关键字
	 * @throws ServiceException
	 */
	public AreaPage(JSONObject jsonObject, String areaKey) throws ServiceException {
		init(jsonObject, areaKey);
	}
	

	public void init(String json, String areaKey) throws ServiceException {
		super.init(json, areaKey);
		areaList = JsonUtil.JSONArrayToAreaList(content);
	}

	public void init(JSONObject jsonObject, String areaKey) throws ServiceException {
		super.init(jsonObject, areaKey);
		areaList = JsonUtil.JSONArrayToAreaList(content);
	}
	
	/**
	 * 获取地区列表
	 * 
	 * @return 地区列表
	 */
	public ArrayList<Area> getAreaList() {
		return areaList;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(this.getClass().getSimpleName()).append("[")
				.append("total=").append(total).append(",")
				.append("size=").append(size).append(",")
				.append("area_list=").append(areaList).append("]")
				.toString();
	}
}
