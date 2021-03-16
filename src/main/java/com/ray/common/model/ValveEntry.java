package com.ray.common.model;

import com.ray.common.model.base.BaseValveEntry;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class ValveEntry extends BaseValveEntry<ValveEntry> {
	//建议将dao放在Service中只用作查询 
	public static final ValveEntry dao = new ValveEntry().dao();
	//在Service中声明 可直接复制过去使用
	//private ValveEntry dao = new ValveEntry().dao();  
}