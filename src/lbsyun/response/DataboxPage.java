package lbsyun.response;

import java.util.ArrayList;

import lbsyun.ServiceException;
import lbsyun.org.json.JSONObject;
import lbsyun.utils.JsonUtil;



/**
 * 查询Databox的返回页
 * 
 * @author kuangzhijie
 *
 */
public class DataboxPage extends Page {
	private ArrayList<Databox> databoxList;

	/**
	 * 基于JSON字符串的构造
	 * 
	 * @param json JSON字符串
	 * @throws ServiceException
	 */
	public DataboxPage(String json, String key) throws ServiceException {
		init(json, key);
	}

	/**
	 * 基于JSONObject的构造
	 * 
	 * @param jsonObject JSONObject实例
	 * @throws ServiceException
	 */
	public DataboxPage(JSONObject jsonObject, String key) throws ServiceException {
		init(jsonObject, key);
	}
	
	
	public void init(String json, String key) throws ServiceException {
		super.init(json, key);
		databoxList = JsonUtil.JSONArrayToDataboxList(content);
	}

	
	public void init(JSONObject jsonObject, String key) throws ServiceException {
		super.init(jsonObject, key);
		databoxList = JsonUtil.JSONArrayToDataboxList(content);
	}
	
	
	/**
	 * 获取当页的databox列表
	 * 
	 * @return databox列表
	 */
	public ArrayList<Databox> getDataboxList() {
		return databoxList;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(this.getClass().getSimpleName()).append("[")
				.append("total=").append(total).append(",")
				.append("size=").append(size).append(",")
				.append("databox_list=").append(databoxList).append("]")
				.toString();
	}
}
