package com.ray.controller.admin;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;
import com.ray.common.controller.BaseController;
import com.ray.event.UserSaveEvent;

import net.dreamlu.event.EventKit;

public class LabelController extends BaseController {
	
	
	public void penghui() {
//		YwTest model = getModel(YwTest.class, "",true);
//		model.setName(get("comboValue"));
//		model.update();
		
		render("penghui.html");
	}
	
	public void fengchao() {
//		YwTest model = getModel(YwTest.class, "",true);
//		model.setName(get("comboValue"));
//		model.update();
		
		render("fengchao.html");
	}
	public void fff() {
//		YwTest model = getModel(YwTest.class, "",true);
//		model.setName(get("comboValue"));
//		model.update();
		
		renderJson("{aaa:11}");
	}
}
