package com.ray.common.model;

import com.ray.common.model.base.BaseDicts;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class Dicts extends BaseDicts<Dicts> {
	//建议将dao放在Service中只用作查询 
	public static final Dicts dao = new Dicts().dao();
	//在Service中声明 可直接复制过去使用
	//private Dicts dao = new Dicts().dao();  
}
