package com.ray.common.model;

import com.ray.common.model.base.BaseRpLike;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class RpLike extends BaseRpLike<RpLike> {
	//建议将dao放在Service中只用作查询 
	public static final RpLike dao = new RpLike().dao();
	//在Service中声明 可直接复制过去使用
	//private RpLike dao = new RpLike().dao();  
}
