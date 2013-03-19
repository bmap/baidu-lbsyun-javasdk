package lbsyun.http;

import java.util.Map;

import lbsyun.ServiceException;
import lbsyun.utils.FileUtil;



/**
 * 发送请求的抽象类
 * 
 * @author kuangzhijie
 *
 */
public abstract class BaseHttpClient {
	/**
	 * 用户ak
	 */
	public String ak;
	/**
	 * 用户sk
	 */
	public String sk;
	/**
	 * 请求host
	 */
	public String host = "http://api.map.baidu.com";
	
	/**
	 *  get 请求常量
	 */
	public static final String METHOD_GET = "GET";
	/**
	 *  post 请求常量
	 */
	public static final String METHOD_POST = "POST";
	/**
	 * 连接超时时长
	 */
	public static int connectTimeOut = 5000;
	/**
	 * 读取超时时长
	 */
	public static int readTimeOut = 5000;
	
	private boolean enableSignature = false;
	/**
	 * 基于ak的构造，请求默认不需要sn
	 * 
	 * @param ak
	 */
	public BaseHttpClient(String ak) {
		this.ak = ak;
		disableSn();
	}
	/**
	 * 基于ak、sk的构造，请求默认需要sn
	 * 
	 * @param ak 用户ak
	 * @param sk 用户sk
	 */
	public BaseHttpClient(String ak, String sk) {
		this.ak = ak;
		this.sk = sk;
		enableSn();
	}
	/**
	 * 基于ak、sk、host的构造 ，请求默认需要sn
	 * 
	 * @param ak 用户ak
	 * @param sk 用户sk
	 * @param host 请求的host
	 */
	public BaseHttpClient(String ak, String sk, String host) {
		this.ak = ak;
		this.sk = sk;
		this.host = host;
		enableSn();
	}
	
	/**
	 * 普通的API请求方法类
	 * @param url 对应API请求的url地址
	 * @param parameters 对应API的参数键值对
	 * @param method 请求的方法"GET" | "POST"
	 * @return 返回json格式字符串信息
	 * @throws ServiceException
	 */
	abstract public String publicRequest(String url,
			Map<String, String> parameters, String method)
			throws ServiceException;
	/**
	 * 文件上传的API请求方法类
	 * @param url 对应API请求的url地址
	 * @param parameters 对应API的参数键值对
	 * @param fileParams 文件参数
	 * @return 返回json格式字符串信息
	 * @throws ServiceException
	 */
	abstract public String fileRequest(String url,
			Map<String, String> parameters, Map<String, FileUtil> fileParams)
			throws ServiceException;
	
	/**
	 * 设置请求需要sn
	 */
	public void enableSn() {
		enableSignature = true;
	}
	/**
	 * 设置请求不需要sn
	 */
	public void disableSn() {
		enableSignature = false;
	}
	
	/**
	 * 查看请求是否需要sn
	 * 
	 * @return 需要返回true，否则返回false
	 */
	public boolean isEnableSignature() {
		return enableSignature;
	}
}
