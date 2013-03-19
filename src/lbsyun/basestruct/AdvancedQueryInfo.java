package lbsyun.basestruct;

import java.util.HashMap;
import java.util.Map;

import lbsyun.ServiceException;
import lbsyun.constant.Scope;
import lbsyun.constant.SortRule;

/**
 * LBS云检索的查询条件信息
 * 
 * @author kuangzhijie
 *
 */
public class AdvancedQueryInfo {
	/**
	 * 检索关键字
	 */
	public String queryWords = null;
	/**
	 * 筛选标签
	 */
	public String tag = null;
	/**
	 * 排序字段过滤条件
	 */
	public String sortName = null;
	/**
	 * 升降序设置
	 */
	public SortRule sortRule = null;
	/**
	 * 数值字段筛选区间
	 */
	public FilterSection section = null;
	/**
	 * 是否显示扩展数据
	 */
	public Scope scope = null;
	/**
	 * 回调函数
	 */
	public String callback = null;
	
	/**
	 * 获取查询关键字
	 * 
	 * @return 查询关键字
	 */
	public String getQueryWords() {
		return queryWords;
	}

	/**
	 * 设置查询关键字
	 * 
	 * @param queryWords 查询关键字
	 */
	public void setQueryWords(String queryWords) {
		this.queryWords = queryWords;
	}

	/**
	 * 获取筛选标签
	 * 
	 * @return 标签
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * 设置筛选标签
	 * 
	 * @param tag 筛选标签
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * 获取排序字段过滤条件
	 * 
	 * @return 排序字段过滤条件
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * 设置排序字段过滤条件
	 * 
	 * @param sortName 排序字段过滤条件
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	/**
	 * 获取排序规则
	 * 
	 * @return 排序规则
	 */
	public SortRule getSortRule() {
		return sortRule;
	}

	/**
	 * 设置排序规则
	 * 
	 * @param sortRule 排序规则
	 */
	public void setSortRule(SortRule sortRule) {
		this.sortRule = sortRule;
	}

	/**
	 * 获取数值字段筛选区间
	 * 
	 * @return 数值字段筛选区间
	 */
	public FilterSection getSection() {
		return section;
	}

	/**
	 * 设置数值字段筛选区间
	 * 
	 * @param section 数值字段筛选区间
	 */
	public void setSection(FilterSection section) {
		this.section = section;
	}

	/**
	 * 获取返回信息范围
	 * 
	 * @return 返回信息范围
	 */
	public Scope getScope() {
		return scope;
	}

	/**
	 * 设置返回信息范围
	 * 
	 * @param scope 返回信息范围
	 */
	public void setScope(Scope scope) {
		this.scope = scope;
	}

	/**
	 * 获取回调函数
	 * 
	 * @return 回调函数
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * 设置回调函数
	 * 
	 * @param callback 回调函数
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}

	/**
	 * 无参数构造
	 */
	public AdvancedQueryInfo() {
		init();
	}
	
	public AdvancedQueryInfo(Map<String, String> info) throws ServiceException {
		init();
		if (null == info || info.isEmpty()) {
			return;
		}
		if (info.containsKey("q")) {
			queryWords = info.get("q");
		}
		if (info.containsKey("tag")) {
			tag = info.get("tag");
		}
		if (info.containsKey("scope")) {
			scope = Scope.valueOf(Integer.valueOf(info.get("scope")));
		}
		if (info.containsKey("callback")) {
			callback = info.get("callback");
		}
		if (info.containsKey("sort_name")) {
			sortName = info.get("sort_name");
		}
		if (info.containsKey("sort_rule")) {
			sortRule = SortRule.valueOf(info.get("sort_rule"));
		}
		if (info.containsKey("section")) {
			String[] parts = info.get("section").split(":");
			if (parts.length < 2) {
				throw new ServiceException("section format error");
			}
			String name = parts[0];
			String[] nums = parts[1].split(",");
			if (nums.length < 2) {
				throw new ServiceException("section format error");
			}
			section = new FilterSection(name, Double.valueOf(nums[0]),
					Double.valueOf(nums[1]));
		}
	}
	
	/**
	 * 初始化
	 */
	public void init() {
		queryWords = null;
		tag = null;
		sortName = null;
		sortRule = null;
		section = null;
		scope = null;
		callback = null;
	}
	/**
	 * 把查询条件转化成参数键值对，用于检索请求
	 * 
	 * @return 参数键值对
	 * @throws ServiceException
	 */
	public HashMap<String, String> toParams()
			throws ServiceException {
		HashMap<String, String> params = new HashMap<String, String>();
		injectParams(params, this);
		return params;
	}
	
	/**
	 * 把查询条件注入已有参数键值对中，用于检索请求
	 * 
	 * @param params 参数键值对
	 * @param info 查询信息
	 * @throws ServiceException
	 */
	public static void injectParams(Map<String, String> params,
			AdvancedQueryInfo info) throws ServiceException {
		if (null != info) {
			if (null != info.queryWords) {
				params.put("q", info.queryWords);
			}
			if (null != info.tag) {
				params.put("tag", info.tag);
			}
			if (null != info.scope) {
				params.put("scope", info.scope.toString());
			}
			if (null != info.callback) {
				params.put("callback", info.callback);
			}
		}
	}
	
	
	
	/**
	 * 生成filter字符串
	 * @return filter字符串
	 * @throws ServiceException 
	 */
	public static String getFilter(int databoxId, AdvancedQueryInfo info) throws ServiceException {
		if (databoxId < 0) {
			throw new ServiceException("invalid databox_id");
		}
		StringBuilder filter = new StringBuilder();
		filter.append("databox:");
		filter.append(databoxId);
		
		if (null != info) {
			if (null != info.sortName) {
				filter.append("|sort_name:");
				filter.append(info.sortName);
			}
			if (null != info.sortRule) {
				filter.append("|sort_rule:");
				filter.append(info.sortRule.toString());
			}
			if (null != info.section) {
				filter.append("|");
				filter.append(info.section);
			}
		}
		return filter.toString();
	}
	
	
}
