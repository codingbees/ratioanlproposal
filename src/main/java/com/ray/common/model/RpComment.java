package com.ray.common.model;

import com.ray.common.model.base.BaseRpComment;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class RpComment extends BaseRpComment<RpComment> {
	//建议将dao放在Service中只用作查询 
	public static final RpComment dao = new RpComment().dao();
	//在Service中声明 可直接复制过去使用
	//private RpComment dao = new RpComment().dao();  
}
