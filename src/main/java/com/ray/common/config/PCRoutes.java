package com.ray.common.config;

import com.jfinal.config.Routes;
import com.ray.controller.admin.LabelController;
import com.ray.controller.admin.TestController;
import com.ray.controller.admin.ValveController;

public class PCRoutes extends Routes {

	@Override
	public void config() {
		this.setBaseViewPath("/page");
		this.add("/valve",ValveController.class);
		this.add("/test",TestController.class);
		this.add("/label",LabelController.class);
	}

}