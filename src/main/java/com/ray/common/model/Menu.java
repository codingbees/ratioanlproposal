package com.ray.common.model;

import com.ray.common.model.base.BaseMenu;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class Menu extends BaseMenu<Menu> {
	//建议将dao放在Service中只用作查询 
	public static final Menu dao = new Menu().dao();
	//在Service中声明 可直接复制过去使用
	//private Menu dao = new Menu().dao();  
}
