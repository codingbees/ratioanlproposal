package com.ray.common.model;

import com.ray.common.model.base.BasePrecisionTypeCommins;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class PrecisionTypeCommins extends BasePrecisionTypeCommins<PrecisionTypeCommins> {
	//建议将dao放在Service中只用作查询 
	public static final PrecisionTypeCommins dao = new PrecisionTypeCommins().dao();
	//在Service中声明 可直接复制过去使用
	//private PrecisionTypeCommins dao = new PrecisionTypeCommins().dao();  
}
