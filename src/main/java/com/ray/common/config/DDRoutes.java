package com.ray.common.config;

import com.jfinal.config.Routes;
import com.ray.controller.app.AppCheckController;
import com.ray.controller.app.AppController;

public class DDRoutes extends Routes {

	@Override
	public void config() {
		this.add("/app",AppController.class);
		this.add("/app/check",AppCheckController.class);
	}

}