package lbsyun.basestruct;

import lbsyun.ServiceException;

/**
 * 数值字段的筛选区间
 * 
 * @author kuangzhijie
 *
 */
public class FilterSection {
	/**
	 * 字段名称
	 */
	private String name;
	/**
	 * 上限
	 */
	private Number upper;
	/**
	 * 下限
	 */
	private Number lower;
	/**
	 * 获取字段名称
	 * 
	 * @return 字段名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置字段名称
	 * 
	 * @param name 字段名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取上限
	 * 
	 * @return 上限
	 */
	public Number getUpper() {
		return upper;
	}

	/**
	 * 设置上限
	 * 
	 * @param upper 上限
	 */
	public void setUpper(Number upper) {
		this.upper = upper;
	}

	/**
	 * 获取下限
	 * 
	 * @return 下限
	 */
	public Number getLower() {
		return lower;
	}

	/**
	 * 设置下限
	 * 
	 * @param lower 下限
	 */
	public void setLower(Number lower) {
		this.lower = lower;
	}

	/**
	 * 基于筛选区间三要素的构造
	 * 
	 * @param name 字段名称
	 * @param upper 上限
	 * @param lower 下限
	 * @throws ServiceException
	 */
	public FilterSection(String name, Number upper, Number lower)
			throws ServiceException {
		if (null == name || name.isEmpty()) {
			throw new ServiceException("invalid section name defined");
		}
		if (null == upper || null == lower) {
			throw new ServiceException("invalid section defined");
		}
		this.name = name;
		this.upper = upper;
		this.lower = lower;
	}
	
	@Override
	public String toString() {
		return name + "_section:" + lower + "," + upper;
	}
}
