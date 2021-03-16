package com.ray.controller.admin;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ray.common.model.RationalProposal;
import com.ray.common.model.RpLineStructure;
import com.ray.common.model.UserRole;
import com.ray.common.model.ValveInventory;
import com.ray.common.model.ValveParts;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.taobao.api.ApiException;
import com.ray.common.ding.*;
import com.ray.util.Commen;
import com.ray.util.SerialNumberUtil;
import com.ray.util.ServiceResult;

public class ValveController extends Controller {
	//建号页面
	public void createPart() {
		render("/page/valve/createPart.html");
	}
	
	//建号方法
	public void create() {
		Record rec = new Record();
		ValveParts vp = getModel(ValveParts.class,"");
		vp.setQrCode(vp.getPartNo()+","+vp.getPartName()+","+vp.getBatchNo()+","+vp.getInitQty());
		//检查零件编号是否重复
		List<Record> l= Db.find("select part_no from valve_parts ");
		boolean flag = false;
		for (Record record : l) {
			if(vp.get("part_no").equals(record.get("part_no"))) {	
				flag = true;
			}
		};
		if(flag) {
			rec.set("status", 400);
			 rec.set("message", "part_no重复");
			
		}else {
			vp.save();
			rec.set("status", 200);
			 rec.set("message", "设置成功");
			
		}

		renderJson(rec);
	}
	//出入库
	public void inventory() {
		render("/page/valve/inventory.html");
	}
	//入库方法
	public void handleInventory() {
		ValveInventory vp = getModel(ValveInventory.class,"");
		Record res = new Record();
		String qrCode = vp.getQrCode();
		//检查库存件表中是否存在此二维码。  若不存在则提醒用户先建号
		List<Record> d = Db.find("select count(*) as count from valve_parts where qr_code = '"+qrCode+"' ");
		System.out.println(d.get(0).get("count").toString());
		
		if("1".equals(d.get(0).get("count").toString())) {
			try {			
				vp.save();
				res.set("s", 200);
			}catch(Exception e) {
				System.out.println(e);
				res.set("s", e);
			}
		}else {
			res.set("s", 0);
		}

		renderJson(res);
	}
	public void getTypeList() {
		Record res = new Record();
		List<Record> l= Db.find("select * from valve_part_type ");
		res.set("typeList", l.toArray());
		renderJson(res);
	}

}
