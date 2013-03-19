package lbsyun.http;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lbsyun.ServiceException;
import lbsyun.utils.BaiduUtil;
import lbsyun.utils.FileUtil;
import lbsyun.utils.HttpUtil;




/**
 * LBS云服务的默认请求类
 * 
 * @author kuangzhijie
 *
 */
public class DefaultHttpClient extends BaseHttpClient {

	/**
	 * 基于ak的构造，请求默认不需要sn.
	 * 
	 * @param ak
	 */
	public DefaultHttpClient(String ak) {
		super(ak);
	}
	/**
	 * 基于ak、sk的构造，请求默认需要sn
	 * 
	 * @param ak
	 * @param sk
	 */
	public DefaultHttpClient(String ak, String sk) {
		super(ak, sk);
	}
	
	/**
	 * 基于ak、sk、host的构造 ，请求默认需要sn
	 * 
	 * @param ak
	 * @param sk
	 * @param host
	 */
	public DefaultHttpClient(String ak, String sk, String host) {
		super(ak, sk, host);
	}
	
	@Override
	public String publicRequest(String uri, Map<String, String> parameters,
			String method) throws ServiceException {
		String response = null;
		Map<String, String> params = new HashMap<String, String>();
		if (null != parameters) {
			params.putAll(parameters);
		}
		params.put("ak", ak);
		if (isEnableSignature()) {
			Date date = new Date();
			long timestamp = date.getTime() / 1000;
			params.put("timestamp", Long.toString(timestamp));
			try {
				String sn = BaiduUtil.getSignature(uri, params, sk, method);
				params.put("sn", sn);
			}catch(IOException e){
				throw new ServiceException(e);
			}
		}
		
		String url = host + uri;
		try {
			if ("GET".equals(method)) {
				response = HttpUtil.doGet(url, params);
			} else {
				response = HttpUtil.doPost(url, params);
			}
		} catch (IOException e) {
			return null;
		}
		return response;
	}

	@Override
	public String fileRequest(String uri, Map<String, String> parameters,
			Map<String, FileUtil> fileParams) throws ServiceException {
		String response = null;
		Map<String, String> params = new HashMap<String, String>();
		if (null != parameters) {
			params.putAll(parameters);
		}
		params.put("ak", ak);
		if (isEnableSignature()) {
			Date date = new Date();
			long timestamp = date.getTime() / 1000;
			params.put("timestamp", Long.toString(timestamp));
			try {
				String sn = BaiduUtil.getSignature(uri, params, sk, METHOD_POST);
				params.put("sn", sn);
			}catch(IOException e){
				throw new ServiceException(e);
			}
		}
		
		String url = host + uri;
		try {
			response = HttpUtil.uploadFile(url, params, fileParams,
					connectTimeOut, readTimeOut);
		} catch (IOException e) {
			return null;
		}
		return response;
	}

}
