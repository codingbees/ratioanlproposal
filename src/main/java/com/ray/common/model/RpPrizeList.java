package com.ray.common.model;

import com.ray.common.model.base.BaseRpPrizeList;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class RpPrizeList extends BaseRpPrizeList<RpPrizeList> {
	//建议将dao放在Service中只用作查询 
	public static final RpPrizeList dao = new RpPrizeList().dao();
	//在Service中声明 可直接复制过去使用
	//private RpPrizeList dao = new RpPrizeList().dao();  
}
