package lbsyun.service;

import java.util.ArrayList;
import java.util.Map;

import lbsyun.http.BaseHttpClient;
import lbsyun.http.DefaultHttpClient;




/**
 * 接口服务的基类信息
 * 
 * @author kuangzhijie
 *
 */

public class BaseService {
	/**
	 * 发送HTTP请求的对象
	 * 
	 */
	public BaseHttpClient invoker = null;
	
	// 上一次请求返回结果
	protected int status;
	
	/**
	 * 基于HTTP请求实例的构造
	 * 
	 * @param invoker HTTP请求实例
	 */
	public BaseService(BaseHttpClient invoker) {
		this.invoker = invoker;
	}
	/**
	 * 基于ak的构造
	 * 
	 * @param ak 用户ak
	 */
	public BaseService(String ak) {
		this.invoker = new DefaultHttpClient(ak);
	}
	
	/**
	 * 基于ak、sk的构造
	 * 
	 * @param ak 用户ak
	 * @param sk 用户sk
	 */
	public BaseService(String ak, String sk) {
		this.invoker = new DefaultHttpClient(ak, sk);
	}
	/**
	 * 基于ak、sk、host的构造
	 * 
	 * @param ak 用户ak
	 * @param sk 用户sk
	 * @param host 请求host
	 */
	public BaseService(String ak, String sk, String host) {
		this.invoker = new DefaultHttpClient(ak, sk, host);
	}
	/**
	 * 设置HTTP请求实例
	 * 
	 * @param invoker HTTP请求实例
	 */
	public void setServiceClient(BaseHttpClient invoker){
		this.invoker = invoker;
	}
	
	/**
	 * 获取上一次请求返回的状态
	 * 
	 * @return
	 */
	public int getStatus(){
		return status;
	}
	
	/**
	 * 重置返回状态
	 */
	protected void resetStatus(){
		status = -1;
	}
	
	/**
	 * 检查参数中是否都包括必选参数了
	 * @param requireParam 必选参数数组
	 * @param parameters 参数
	 * @return 包括了则返回true，否则返回false
	 */
	public static boolean checkParam(String[] requireParam, 
			Map<String, String> parameters) {
		if(null == requireParam || 0 == requireParam.length) {
			return true;
		}
		for(String param : requireParam) {
			if(!parameters.containsKey(param)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 检查参数中是否都包括必选参数了
	 * @param requireParam 必选参数数组
	 * @param parameters 参数
	 * @return 包括了则返回true，否则返回false
	 */
	public static boolean checkParam(ArrayList<String> requireParam,
			Map<String, String> parameters) {
		for (String param : requireParam) {
			if (!parameters.containsKey(param)) {
				return false;
			}
		}
		return true;
	}
}
