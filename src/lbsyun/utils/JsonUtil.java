package lbsyun.utils;

import java.util.ArrayList;

import lbsyun.ServiceException;
import lbsyun.org.json.JSONArray;
import lbsyun.org.json.JSONException;
import lbsyun.response.Area;
import lbsyun.response.Databox;
import lbsyun.response.Poi;





/**
 * 关于JSON的一些工具类
 * 
 * @author kuangzhijie
 * 
 */
public final class JsonUtil {
	/**
	 * 把JSONArray对象转成Integer的List
	 * 
	 * @param jsonArray
	 * @return
	 * @throws ServiceException
	 */
	public static ArrayList<Integer> JSONArrayToIntList(JSONArray jsonArray)
			throws ServiceException {
		try {
			int length = jsonArray.length();
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < length; ++i) {
				list.add(jsonArray.getInt(i));
			}
			return list;
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 把JSONArray对象转成Double的List
	 * 
	 * @param jsonArray
	 * @return
	 * @throws ServiceException
	 */
	public static ArrayList<Double> JSONArrayToDoubleList(JSONArray jsonArray)
			throws ServiceException {
		try {
			int length = jsonArray.length();
			ArrayList<Double> list = new ArrayList<Double>();
			for (int i = 0; i < length; ++i) {
				list.add(jsonArray.getDouble(i));
			}
			return list;
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 把JSONArray对象转成String的List
	 * 
	 * @param jsonArray
	 * @return
	 * @throws ServiceException
	 */
	public static ArrayList<String> JSONArrayToStringList(JSONArray jsonArray)
			throws ServiceException {
		try {
			int length = jsonArray.length();
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < length; ++i) {
				list.add(jsonArray.getString(i));
			}
			return list;
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 把JSONArray对象转成Databox的List
	 * 
	 * @param jsonArray
	 * @return
	 * @throws ServiceException
	 */
	public static ArrayList<Databox> JSONArrayToDataboxList(JSONArray jsonArray)
			throws ServiceException {
		try {
			int length = jsonArray.length();
			ArrayList<Databox> list = new ArrayList<Databox>();
			for (int i = 0; i < length; ++i) {
				list.add(new Databox(jsonArray.getJSONObject(i)));
			}
			return list;
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 把JSONArray对象转成Poi的List
	 * 
	 * @param jsonArray
	 * @return
	 * @throws ServiceException
	 */
	public static ArrayList<Poi> JSONArrayToPoiList(JSONArray jsonArray)
			throws ServiceException {
		try {
			int length = jsonArray.length();
			ArrayList<Poi> list = new ArrayList<Poi>();
			for (int i = 0; i < length; ++i) {
				list.add(new Poi(jsonArray.getJSONObject(i)));
			}
			return list;
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 把JSONArray对象转成Area的List
	 * 
	 * @param jsonArray
	 * @return
	 * @throws ServiceException
	 */
	public static ArrayList<Area> JSONArrayToAreaList(JSONArray jsonArray)
			throws ServiceException {
		try {
			int length = jsonArray.length();
			ArrayList<Area> list = new ArrayList<Area>();
			for (int i = 0; i < length; ++i) {
				list.add(new Area(jsonArray.getJSONObject(i)));
			}
			return list;
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
	}

}
