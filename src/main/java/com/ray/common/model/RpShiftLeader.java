package com.ray.common.model;

import com.ray.common.model.base.BaseRpShiftLeader;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class RpShiftLeader extends BaseRpShiftLeader<RpShiftLeader> {
	//建议将dao放在Service中只用作查询 
	public static final RpShiftLeader dao = new RpShiftLeader().dao();
	//在Service中声明 可直接复制过去使用
	//private RpShiftLeader dao = new RpShiftLeader().dao();  
}