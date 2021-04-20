package com.ray.job;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.quartz.JobExecutionContext;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
//import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
//import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jfinal.core.NotAction;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ray.common.ding.AccessTokenUtil;
import com.ray.common.model.SerialNumber;
import com.ray.common.quartz.AbsJob;
import com.taobao.api.ApiException;

/**
 * 每天执行
 *
 * @author Ray
 * @date 2010-08-03
 */
public class EveryDayJob extends AbsJob {

//	@Override
//	protected void process(JobExecutionContext context) {
////		System.out.println("每日任务,自增编号归零");
////		SerialNumber sn = SerialNumber.dao.findById(1);
////		sn.delete();
////		sn = new SerialNumber();
////		sn.setId(1);
////		sn.save();
//		
//	}
	@Override
	protected void process(JobExecutionContext context) {
		System.out.println("每日任务已启动");
		DateFormat df3 = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
		DateFormat df7 = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
		String date3 = df3.format(new Date());
		String time3 = df7.format(new Date());
		System.out.println("启动时间：" + date3 + " " + time3);
		try {
			findUsers();
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}
	public void findUsers() throws ApiException {

		//查询每个有待关闭问题的车间
		String sql = "SELECT workshop,COUNT(*) as qty FROM rational_proposal WHERE is_checked = 0  GROUP BY workshop HAVING qty>0";
		List<Record> workshopList = Db.find(sql);

		for (Record record : workshopList) {
			String workshop = record.getStr("workshop");
			Integer qty = record.getInt("qty");
			String msg = "合理化建议系统提示：您负责的车间\""+workshop+"\"还有"+qty+"条建议未审核，请及时登录钉钉中的合理化建议系统完成审核。";
			
			String sql2 = "SELECT productionLine,phone_number FROM rp_line_structure WHERE productionLine  = '"+workshop+"'";
			List<Record> listPhone = Db.find(sql2);	
			for (Record r : listPhone) {
				if(r.getStr("phone_number")!=null) {
					String userid = getUserIdByPhone(r.getStr("phone_number"));
					JSONObject jsonObj = JSON.parseObject(userid);
					if ((Integer) jsonObj.get("errcode") == 0) {
						JSONObject jsonObj2 = JSON.parseObject(jsonObj.get("result").toString());
						String id = jsonObj2.get("userid").toString();
						// 发送消息
						sendMessage(id, msg);

					}
				}
			}
		}
	}
	
	public void sendMessage(String id,String message) throws ApiException {

		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		request.setAgentId(Long.valueOf(PropKit.get("AGENT_ID")));
		request.setUseridList(id);
		request.setToAllUser(false);

		OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		msg.setMsgtype("text");
		msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
		msg.getText().setContent(message);
		request.setMsg(msg);

		String accessToken = AccessTokenUtil.getToken();
		OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, accessToken);
//		System.out.println("rspBody in Ding send MSG API:"+rsp.getBody());


	}

	//根据手机号查询userid
	
	public static String getUserIdByPhone(String mobile){
		Record record = new Record();
		String resp = "";
		try {
			String accessToken = AccessTokenUtil.getToken();
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
			OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
			req.setMobile(mobile);
			OapiV2UserGetbymobileResponse rsp = client.execute(req, accessToken);
			resp = rsp.getBody();
		} catch (ApiException e) {
			e.printStackTrace();
			record.set("resp",e);
			resp = "no data from getUserIdByPhone.";
		}
		return resp;
	}

}
