package com.ray.common.model;

import com.ray.common.model.base.BasePrecisionType;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class PrecisionType extends BasePrecisionType<PrecisionType> {
	//建议将dao放在Service中只用作查询 
	public static final PrecisionType dao = new PrecisionType().dao();
	//在Service中声明 可直接复制过去使用
	//private PrecisionType dao = new PrecisionType().dao();  
}
