package com.ray.common.model;

import com.ray.common.model.base.BasePrecisionTypeDebugging;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class PrecisionTypeDebugging extends BasePrecisionTypeDebugging<PrecisionTypeDebugging> {
	//建议将dao放在Service中只用作查询 
	public static final PrecisionTypeDebugging dao = new PrecisionTypeDebugging().dao();
	//在Service中声明 可直接复制过去使用
	//private PrecisionTypeDebugging dao = new PrecisionTypeDebugging().dao();  
}