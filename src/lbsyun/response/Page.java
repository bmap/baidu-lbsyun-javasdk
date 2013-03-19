package lbsyun.response;


import lbsyun.ServiceException;
import lbsyun.org.json.JSONArray;
import lbsyun.org.json.JSONException;
import lbsyun.org.json.JSONObject;



public class Page {
	protected int size;
	protected int total;
	protected JSONArray content;
	
	public Page() {
		size = 0;
		total = 0;
		content = null;
	}
	/**
	 * 基于JSON字符串的初始化
	 * @param json JSON字符串
	 * @param contentKey JSON字符串中内容列表的关键字
	 * @throws ServiceException
	 */
	public void init(String json, String contentKey) throws ServiceException {
		try {
			JSONObject jsonObject = new JSONObject(json);
			size = jsonObject.getInt("size");
			total = jsonObject.getInt("total");
			content = jsonObject.getJSONArray(contentKey);
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}

	/**
	 * 基于JSONObject的初始化
	 * 
	 * @param jsonObject JSONObject实例
	 * @param contentKey JSONObject中内容列表的关键字
	 * @throws ServiceException
	 */
	public void init(JSONObject jsonObject, String contentKey) throws ServiceException {
		try {
			size = jsonObject.getInt("size");
			total = jsonObject.getInt("total");
			content = jsonObject.getJSONArray(contentKey);
		} catch (JSONException je) {
			throw new ServiceException(je);
		}
	}

	/**
	 * 获取检索页面的大小
	 * 
	 * @return 检索页面的大小
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 获取所有结果的数量
	 * @return 所有结果的数量
	 */
	public int getTotal() {
		return total;
	}
	
	/**
	 * 获取内容部分的JSONArray
	 * 
	 * @return 内容部分的JSONArray
	 */
	public JSONArray getContent() {
		return content;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(this.getClass().getSimpleName()).append("[size=")
				.append(size).append(",total=").append(total)
				.append(",content=").append(content).append("]").toString();
	}
	
}
