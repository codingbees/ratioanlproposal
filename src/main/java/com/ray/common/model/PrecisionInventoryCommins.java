package com.ray.common.model;

import com.ray.common.model.base.BasePrecisionInventoryCommins;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class PrecisionInventoryCommins extends BasePrecisionInventoryCommins<PrecisionInventoryCommins> {
	//建议将dao放在Service中只用作查询 
	public static final PrecisionInventoryCommins dao = new PrecisionInventoryCommins().dao();
	//在Service中声明 可直接复制过去使用
	//private PrecisionInventoryCommins dao = new PrecisionInventoryCommins().dao();  
}