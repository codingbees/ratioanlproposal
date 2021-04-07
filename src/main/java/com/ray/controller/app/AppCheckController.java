package com.ray.controller.app;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.lang.Integer;
import com.ray.util.Commen;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.stat.ast.ForIteratorStatus;
import com.jfinal.upload.UploadFile;
import com.ray.common.model.RationalProposal;
import com.ray.common.model.RpImproveType;
import com.ray.common.model.RpLike;
import com.ray.common.model.RpLineStructure;
import com.ray.common.model.RpPrizeExchangeList;
import com.ray.common.model.RpPrizeList;
import com.ray.common.model.RpShiftLeader;
import com.ray.common.model.RpStaffQty;
import com.ray.common.model.User;
import com.ray.util.SerialNumberUtil;

public class AppCheckController extends Controller {
	public void getCheckList() {
		String auditor_userid = getPara("userid");
		List<RationalProposal> toBeChecked = RationalProposal.dao.find(
				"select * from rational_proposal WHERE is_checked = 0 and auditor_userid ='" + auditor_userid + "'");
		Record resp = new Record();
		resp.set("arraytoBeChecked", toBeChecked);
//		System.out.println(toBeChecked);
		resp.set("code", 200);
		renderJson(resp);
	}

	public void getCheckItem() {
		String no = getPara("no");
		List<RationalProposal> Item = RationalProposal.dao
				.find("select * from rational_proposal WHERE rp_no='" + no + "'");
		
		
		List<Record> supervisor = Db.find("select leader_name,leader_id from rp_line_structure where productionLine ="
				+ "(select workshop from rational_proposal WHERE rp_no='" + no + "')");
		Record resp = new Record();
		try {
			String s = Item.get(0).get("picture_of_problem");
			String[] str_pic = null;
			if (s != null) {
				String[] str = s.split(";");
				str_pic = new String[str.length];
				for (int i = 0; i < str.length; i++) {
					str_pic[i] = str[i].substring(17);
//					System.out.println(str_pic[i]);
				}
			}
			// System.out.println("picture_of_problem is");
			// System.out.println(s);
			resp.set("img", str_pic);
		} catch (Exception e) {
			resp.set("code", 0);
			resp.set("msg", "无法获取图片：" + e.getMessage());
			e.printStackTrace();
		}
		resp.set("supervisor", supervisor);
		resp.set("getCheckItem", Item);
		resp.set("code", 200);
		renderJson(resp);

	}

	public void getHandlers() {
		String no = getPara("no");
		List<RpShiftLeader> list = RpShiftLeader.dao.find(
				"select * from rp_shift_leader where pid =(select id from rp_line_structure where productionLine =( select workshop from rational_proposal WHERE rp_no='"
						+ no + "'))");
		Record resp = new Record();
		String[] arrayShiftLeader = new String[list.size()];
		String[] arrayShiftLeaderid = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arrayShiftLeader[i] = list.get(i).getShiftLeader();
			arrayShiftLeaderid[i] = list.get(i).getShiftLeaderJobNo();
		}
		resp.set("handlers", arrayShiftLeader);
		resp.set("handlersid", arrayShiftLeaderid);
//		System.out.println("getHandlers works!");
//		System.out.println(arrayShiftLeader);
		resp.set("code", 200);
		renderJson(resp);

	}

	public void submitCheck() {

		Record resp = new Record();
		try {
			RationalProposal qp = getModel(RationalProposal.class, "");
			
			String auditor_userid = qp.getAuditorUserid() ;
			String auditor_username = qp.getAuditorUsername();
			
			Integer is_excellent = qp.getIsExcellent();
			Integer is_difficult = qp.getIsDifficult();
			String handler_username = qp.getHandlerUsername();
			String handler_userid = qp.getHandlerUserid();
			String rp_no = qp.getRpNo();
			String audit_opinion = qp.getAuditOpinion();
			
			Integer scores = qp.getScores();
			
			
			String commit_finish_date = qp.getCommitFinishDate().toString();
			Integer addScore = qp.getAddScore();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(new Date());
			Integer is_checked = qp.getIsChecked();
			
			Db.update("update rational_proposal set auditor_userid='"+auditor_userid+"',auditor_username='"+auditor_username+"', audit_opinion='" + audit_opinion + "',handler_userid='"
					+ handler_userid + "',handler_username='" + handler_username + "',scores=" + scores + ", addScore="
					+ addScore + " ,commit_finish_date='" + commit_finish_date + "',is_excellent=" + is_excellent
					+ ",is_difficult=" + is_difficult + "  WHERE rp_no ='" + rp_no + "' ;");

			Db.update("update rational_proposal set is_checked="+is_checked+",audit_result="+is_checked+",approve_date='" + date
					+ "' WHERE rp_no ='" + rp_no + "'");

			resp.set("code", 200);
			resp.set("msg", "数据提交成功");
		} catch (Exception e) {
			resp.set("code", 0);
			resp.set("msg", "数据提交失败，原因：" + e.getMessage());
			e.printStackTrace();
		}
		renderJson(resp);

	}

	public void submitReject() {
//		String no = getPara("no");
		Record resp = new Record();
		try {
			RationalProposal qp = getModel(RationalProposal.class, "");
			String no = qp.getRpNo();
			String audit_opinion = qp.getAuditOpinion();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(new Date());

			Db.update("update rational_proposal set audit_opinion='" + audit_opinion + "' WHERE rp_no ='" + no + "' ;");
			Db.update("update rational_proposal set is_checked=1,audit_result=0,approve_date='" + date
					+ "' WHERE rp_no ='" + no + "'");

			resp.set("code", 200);
			resp.set("msg", "请求成功！");
		} catch (Exception e) {
			resp.set("code", 0);
			resp.set("msg", "请求失败，原因：" + e.getMessage());
			e.printStackTrace();
		}
		renderJson(resp);

	}

	public void getHandleList() {
		String handler_userid = getPara("handler_userid");
		List<RationalProposal> toBeChecked = RationalProposal.dao.find(
				"select * from rational_proposal WHERE is_checked = 1 AND audit_result =1 AND handle_result!=1 and handler_userid='"
						+ handler_userid + "'");
		Record resp = new Record();
		resp.set("arraytoBeChecked", toBeChecked);

		resp.set("code", 200);
		renderJson(resp);
	}

	public void getHandleItem() {
		Record resp = new Record();

//		RationalProposal rp = getModel(RationalProposal.class,"");
//		Object Item =rp.findFirst("select * from rational_proposal WHERE rp_no='" + getPara("rp_no") + "'");
//		resp.set("getCheckItem", Item);

		List<RationalProposal> Item = RationalProposal.dao
				.find("select * from rational_proposal WHERE rp_no='" + get("rp_no") + "'");
		try {
			String s = Item.get(0).get("picture_of_problem");
			String[] str_pic = null;
			// 将储存图片名称的字符串转换为数组以便前台使用
			if (s != null) {
				String[] str = s.split(";");
				str_pic = new String[str.length];
				for (int i = 0; i < str.length; i++) {
					str_pic[i] = str[i].substring(17);
				}
			}

			resp.set("img", str_pic);
		} catch (Exception e) {
			resp.set("code", 0);
			resp.set("msg", "发起问题失败，原因：" + e.getMessage());
			e.printStackTrace();
		}
		resp.set("getCheckItem", Item.get(0));

		resp.set("code", 200);
		renderJson(resp);
	}

	// 20200118 新增中止建议方法
	public void toSuspend() {
		Record resp = new Record();
		RationalProposal qp = getModel(RationalProposal.class, "");
		String no = qp.getRpNo();
		String comment = qp.getComment();
		Db.update("update rational_proposal set audit_result = 0 ,handle_result = 0,comment = '" + comment
				+ "' WHERE rp_no ='" + no + "' ;");
		resp.set("code", 200);
		renderJson(resp);
	}

	public void submitHandle() {
		Record resp = new Record();
		try {
			RationalProposal qp = getModel(RationalProposal.class, "");
			String no = qp.getRpNo();
			String picture_after_improve = qp.getPictureAfterImprove();
			String actural_finish_date = qp.getActuralFinishDate().toString();
			String description_after_improve = qp.getDescriptionAfterImprove();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(new Date());

			// 避免当picture_after_improve == null时，将‘null’存入数据库
			if (picture_after_improve == null) {
				Db.update("update rational_proposal set picture_after_improve=" + picture_after_improve
						+ ",actural_finish_date='" + actural_finish_date + "',description_after_improve='"
						+ description_after_improve + "' WHERE rp_no ='" + no + "' ;");
			} else {
				Db.update("update rational_proposal set picture_after_improve='" + picture_after_improve
						+ "',actural_finish_date='" + actural_finish_date + "',description_after_improve='"
						+ description_after_improve + "' WHERE rp_no ='" + no + "' ;");
			}
			Db.update("update rational_proposal set handle_result=1,handle_date='" + date + "' WHERE rp_no ='" + no
					+ "'");
			resp.set("code", 200);
		} catch (Exception e) {
			resp.set("code", 0);
			e.printStackTrace();
		}
		renderJson(resp);

	}

	public void uploadFilehandle() {
		String picName = UUID.randomUUID().toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = sdf.format(new Date());
		UploadFile file = getFile("file", "rationalproposal");
		Record rsp = new Record();
		file.getFile().renameTo(new File("D:\\apache-tomcat-8.0.26\\webapps\\rationalproposal\\" + picName + ".jpg"));
		String fileName = "" + d + "_handl_" + picName + ".jpg;";
		rsp.set("fileName", fileName);
		renderJson(rsp);
	}

	public void getMyList() {
		String jobnumber = getPara("jobnumber");
		Record[] records = new Record[5];
		// 全部数据
		Record resp0 = new Record();
		try {
			List<RationalProposal> list = RationalProposal.dao
					.find("select * from rational_proposal WHERE find_userid ='" + jobnumber + "'");
			resp0.set("title", "全部");
			resp0.set("number", list.size());
			resp0.set("showBadge", true);
			resp0.set("data", list);
		} catch (Exception e) {
			resp0.set("code", 0);
			e.printStackTrace();
		}
		records[0] = resp0;

		// 已发起，未审核， is_checked = 0
		Record resp1 = new Record();//
		try {
			List<RationalProposal> list1 = RationalProposal.dao
					.find("select * from rational_proposal WHERE find_userid ='" + jobnumber + "' AND is_checked = 0");

			resp1.set("title", "待审核");
			resp1.set("number", list1.size());
			resp1.set("showBadge", true);
			resp1.set("data", list1);
		} catch (Exception e) {
			resp1.set("code", 0);
			e.printStackTrace();
		}
		records[1] = resp1;

		// 已审核，同意， is_checked = 1 check_result = 1 handle_result = 0
		Record resp2 = new Record();//
		try {
			List<RationalProposal> list2 = RationalProposal.dao
					.find("select * from rational_proposal WHERE find_userid ='" + jobnumber
							+ "' AND is_checked = 1 AND audit_result = 1 AND handle_result = 0 ");
			resp2.set("title", "已审核");
			resp2.set("number", list2.size());
			resp2.set("showBadge", true);
			resp2.set("data", list2);
		} catch (Exception e) {
			resp2.set("code", 0);
			e.printStackTrace();
		}
		records[2] = resp2;

		// 已审核，拒绝， is_checked = 1 check_result = 0
		Record resp3 = new Record();//
		try {
			List<RationalProposal> list3 = RationalProposal.dao
					.find("select * from rational_proposal WHERE find_userid ='" + jobnumber
							+ "' AND is_checked = 1 AND audit_result = 0 ");

			resp3.set("title", "已拒绝");
			resp3.set("number", list3.size());
			resp3.set("showBadge", true);
			resp3.set("data", list3);
		} catch (Exception e) {
			resp3.set("code", 0);
			e.printStackTrace();
		}
		records[3] = resp3;

		// 已处理 is_checked = 1 audit_result = 1 handle_result = 1
		Record resp4 = new Record();//
		try {
			List<RationalProposal> list4 = RationalProposal.dao
					.find("select * from rational_proposal WHERE find_userid ='" + jobnumber
							+ "' AND is_checked = 1 AND audit_result = 1 AND handle_result = 1 ");
			resp4.set("title", "已处理");
			resp4.set("number", list4.size());
			resp4.set("showBadge", true);
			resp4.set("data", list4);
		} catch (Exception e) {
			resp4.set("code", 0);
			e.printStackTrace();
		}
		records[4] = resp4;
		renderJson(records);

	}

	public void tocancle() {
		String rpno = getPara("rpno");
		Record resp = new Record();
		try {
			int Item = Db.update("delete from rational_proposal WHERE rp_no='" + rpno + "'");
			resp.set("isdelete", Item);
			resp.set("code", 200);
		} catch (Exception e) {
			resp.set("code", 0);
			e.printStackTrace();
		}

		renderJson(resp);

	}

	public void getListItems() {
		String no = getPara("no");
		List<RationalProposal> Item = RationalProposal.dao
				.find("select * from rational_proposal WHERE rp_no='" + no + "'");

		Record resp = new Record();
		// 获取问题图片
		try {
			String s = Item.get(0).get("picture_of_problem");
			String[] str_pic = null;
			if (s != null) {
				String[] str = s.split(";");
				str_pic = new String[str.length];
				for (int i = 0; i < str.length; i++) {
					str_pic[i] = str[i].substring(17);
				}
			}
			resp.set("img", str_pic);
		} catch (Exception e) {
			resp.set("code", 0);
			resp.set("msg", "获取图片失败，原因：" + e.getMessage());
			e.printStackTrace();
		}
		// 获取整改后图片

		String s2 = Item.get(0).get("picture_after_improve");

		String[] str_pic2 = null;
		if (s2 != null) {
			String[] str2 = s2.split(";");
			str_pic2 = new String[str2.length];
			for (int i = 0; i < str2.length; i++) {
				str_pic2[i] = str2[i].substring(17);
			}
		}
		resp.set("img2", str_pic2);

		resp.set("getCheckItem", Item);
		resp.set("code", 200);
		renderJson(resp);

	}

	public void getRankingList() {
		String selectedMonth = getPara("selectedMonth");
		String sql_RankingListByName = "";
		String sql_RankingListByWorkshop = "";
		String sql_RankingListByDept = "";

		Record[] records = new Record[3];
		if ("".equals(selectedMonth) || selectedMonth == null) {
			sql_RankingListByName = "SELECT SUM(scores+addScore) total_scores,COUNT(*) total_items,find_user_part_name, find_userid,find_username FROM rational_proposal WHERE audit_result <> 0 GROUP BY find_userid,find_username having total_scores>0 ORDER BY SUM(scores+addScore) DESC";
			sql_RankingListByWorkshop = "SELECT SUM(scores+addScore) total_scores,COUNT(*) total_items, find_user_part_name,workshop FROM rational_proposal WHERE audit_result <> 0 GROUP BY workshop having total_scores>0 ORDER BY SUM(scores+addScore) DESC";
			sql_RankingListByDept = "SELECT SUM(scores+addScore) total_scores,COUNT(*) total_items, find_user_part_name,department FROM rational_proposal WHERE audit_result <> 0 GROUP BY department having total_scores>0 ORDER BY SUM(scores+addScore) DESC";
		} else {
			sql_RankingListByName = "SELECT SUM(scores+addScore) total_scores,COUNT(*) total_items,find_user_part_name, find_userid,find_username  FROM rational_proposal WHERE audit_result <> 0 AND create_time like '"
					+ selectedMonth
					+ "%'  GROUP BY find_userid,find_username having total_scores>0 ORDER BY SUM(scores+addScore) DESC limit 50";
			sql_RankingListByWorkshop = "SELECT SUM(scores+addScore) total_scores,COUNT(*) total_items,find_user_part_name, workshop FROM rational_proposal WHERE audit_result <> 0 AND create_time like '"
					+ selectedMonth + "%' GROUP BY workshop having total_scores>0 ORDER BY SUM(scores+addScore) DESC";
			sql_RankingListByDept = "SELECT SUM(scores+addScore) total_scores,COUNT(*) total_items,find_user_part_name, department FROM rational_proposal WHERE audit_result <> 0 AND create_time like '"
					+ selectedMonth + "%' GROUP BY department having total_scores>0 ORDER BY SUM(scores+addScore) DESC";
		}
		List<RationalProposal> RankingListByName = RationalProposal.dao.find(sql_RankingListByName);
		Record resp0 = new Record();
		resp0.set("RankingList", RankingListByName);
		resp0.set("code", 200);
		resp0.set("showBadge", true);
		resp0.set("number", RankingListByName.size());
		resp0.set("title", "个人排名");
		records[0] = resp0;

		List<RationalProposal> RankingListByWorkshop = RationalProposal.dao.find(sql_RankingListByWorkshop);
		Record resp1 = new Record();
		resp1.set("RankingList", RankingListByWorkshop);
		resp1.set("code", 200);
		resp1.set("showBadge", true);
		resp1.set("number", RankingListByWorkshop.size());
		resp1.set("title", "车间排名");
		records[1] = resp1;

		List<RationalProposal> RankingListByDept = RationalProposal.dao.find(sql_RankingListByDept);
		Record resp2 = new Record();
		resp2.set("RankingList", RankingListByDept);
		resp2.set("code", 200);
		resp2.set("showBadge", true);
		resp2.set("number", RankingListByDept.size());
		resp2.set("title", "部门排名");
		records[2] = resp2;

		renderJson(records);
	}

	public void getPrizeList() {
		List<RpPrizeList> PrizeList = RpPrizeList.dao.find("select * from rp_prize_list order by cost_score ");
		Record resp = new Record();

		// 获取总分
		String userid = getPara("userid");
		List<RationalProposal> total_score = RationalProposal.dao.find(
				"select ifnull(SUM(scores+addScore),0) total from rational_proposal WHERE audit_result <> 0 and find_userid = '"
						+ userid + "'");
		Object totalScore = total_score.get(0).get("total");
		int total = Integer.parseInt(String.valueOf(totalScore));

		// 获取已使用的分数
		List<RpPrizeExchangeList> used_score = RpPrizeExchangeList.dao.find(
				"select ifnull(SUM(score),0) total from rp_prize_exchange_list WHERE apply_userid = '" + userid + "'");

		Object usedScore = used_score.get(0).get("total");
		int used = Integer.parseInt(String.valueOf(usedScore)) | 0;

		resp.set("PrizeList", PrizeList);
		resp.set("totalScore", total);
		resp.set("usedScore", used);
		resp.set("code", 200);
		renderJson(resp);

	}

	public void toExchangePrize() throws Exception {
		RpPrizeExchangeList rp = getModel(RpPrizeExchangeList.class, "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		rp.set("apply_date", sdf.format(new Date()));
		rp.save();
		Record resp = new Record();
		resp.set("status", 200);
//		String requestUserId = getPara("requestUserId");
//		String requestUserName = getPara("requestUserName");
//
//		
//		String selectPrizeName = getPara("selectPrizeName");
//
//		
//		String selectPrizeIndex = getPara("selectPrizeIndex");
//
//		String requestScore = getPara("requestScore");
//		int score = Integer.parseInt(requestScore);
//
//		
//		总分
//		List<RationalProposal> total_score = RationalProposal.dao
//				.find("select ifnull(SUM(scores+addScore),0) total from rational_proposal WHERE audit_result <> 0 and find_userid = '" + requestUserId
//						+ "'");
//		Object totalScore = total_score.get(0).get("total");
//		int total = Integer.parseInt(String.valueOf(totalScore));
//		
//		已使用分值
//		List<RpPrizeExchangeList> used_score = RpPrizeExchangeList.dao.find(
//				"select SUM(score) total from rp_prize_exchange_list WHERE apply_userid = '" + requestUserId + "'");
//		Object usedScore = used_score.get(0).get("total");
//
//		int used = 0;
//		if (usedScore != null) {
//			used = Integer.parseInt(String.valueOf(usedScore));
//		}
//
//		if ((total - score - used) < 0) {// 剩余分值不足所需分值的情况
//			resp.set("code", 0);
//			resp.set("restscore", (total - used));
//		} else {// 剩余分值足够
//			Db.update("insert into rp_prize_exchange_list values(null,'" + requestUserId + "','" + requestUserName
//					+ "','" + date + "'," + score + ",'" + selectPrizeName + "'," + selectPrizeIndex + ")");
//			resp.set("code", 200);
//			resp.set("restscore", (total - score - used));
//		}
//		;
//		resp.set("score", score);
//		resp.set("total", total);
//		resp.set("used", used);
		renderJson(resp);

	}

	public void getMyMessage() {
		Record resp = new Record();
		String userid = getPara("userid");

		String sql = "SELECT SUM(scores+addScore) total_scores,COUNT(*) total_items FROM rational_proposal WHERE audit_result <> 0 and find_userid = '"
				+ userid + "' ";
		List<RationalProposal> scores = RationalProposal.dao.find(sql);

		String sql2 = "select SUM(score) total from rp_prize_exchange_list WHERE apply_userid = '" + userid + "'";
		List<RpPrizeExchangeList> used_score = RpPrizeExchangeList.dao.find(sql2);

		String sql3 = "select * from rp_prize_exchange_list WHERE apply_userid = '" + userid + "'";
		List<RpPrizeExchangeList> myExchangeList = RpPrizeExchangeList.dao.find(sql3);

		resp.set("scores", scores);
		resp.set("used_score", used_score);
		resp.set("myExchangeList", myExchangeList);
		resp.set("code", 200);
		renderJson(resp);

	}

	public void getDoubleAuditList() {
		Record resp = new Record();
		List<RationalProposal> DoubleAuditList = RationalProposal.dao.find(
				"select * from rational_proposal where is_checked=1 And audit_result=1 and (is_excellent+is_difficult>=1) and desc_aft_db_audit is NULL order by create_time desc ");
		
		
		
		String sql2 = "SELECT * FROM `user` WHERE id IN (" + 
				"SELECT user_id FROM user_role WHERE role_id = 2)";
		List<User> leanManagerList = User.dao.find(sql2);
		resp.set("leanManagerList", leanManagerList);
		resp.set("DoubleAuditList", DoubleAuditList);
		resp.set("code", 200);
		renderJson(resp);

	}

	public void submitDoubleAudit() {
//	String no = getPara("no");
		Record resp = new Record();
		try {
			RationalProposal qp = getModel(RationalProposal.class, "");
			qp.update();


			resp.set("code", 200);
		} catch (Exception e) {
			resp.set("code", 0);
			e.printStackTrace();
		}
		renderJson(resp);
	}

	public void getExcellentList() {
		List<RationalProposal> ExcellentList = RationalProposal.dao.find(
				"select * from rational_proposal where is_checked=1 And audit_result=1 and is_excellent=1 and is_excellent_aft_ck=1 order by create_time desc ");
		Record resp = new Record();
		resp.set("ExcellentList", ExcellentList);
		resp.set("code", 200);
		renderJson(resp);

	}

	public void getCommunityDataList() {
		Integer pageSize = getInt("pageSize");
		Integer pageNum = getInt("pageNum");
		String sql1 = "(SELECT *,SUM(isLike) AS sumlike,GROUP_CONCAT(userid) AS likeuserid "
				+ "FROM rational_proposal r LEFT JOIN rp_like l ON r.rp_no=l.`rp_num` "
				+ "WHERE is_checked=1 AND audit_result=1 GROUP BY r.`rp_no`) AS liketable";
//		String y = "\"";
//		String s = " '["+y+"',l.`id`,'"+y+",','"+y+"',l.`comment_userid`,'"+y+",','"+y+"',l.`comment_username`,'"+y+",','"+y+"',l.`comment_content`,'"+y+"]'";
//		String sql2 = "(SELECT r.rp_no,l.`comment_userid`,l.`comment_username`,GROUP_CONCAT(CONCAT("+s+")) AS newcontent,l.`isComment`,COUNT(isComment) AS sumcomment,GROUP_CONCAT(comment_userid) AS comment_userids FROM rational_proposal r LEFT JOIN rp_comment l ON r.rp_no=l.`rp_numb` WHERE is_checked=1 AND audit_result=1 GROUP BY r.`rp_no` ) AS commenttable";
		String sql2 = "(SELECT r.rp_no,l.`comment_userid`,l.`comment_username`,"
				+ "GROUP_CONCAT(CONCAT(l.`id`,'$^$',l.`comment_userid`,'$^$',l.`comment_username`,'$^$',l.`comment_content`,'*&^')) "
				+ "AS newcontent,l.`isComment`,COUNT(isComment) AS sumcomment,GROUP_CONCAT(comment_userid) "
				+ "AS comment_userids FROM rational_proposal r LEFT JOIN rp_comment l ON r.rp_no=l.`rp_numb` "
				+ "WHERE is_checked=1 AND audit_result=1 GROUP BY r.`rp_no` ) AS commenttable";

		String sql = "SELECT * FROM " + sql1 + " left join " + sql2
				+ " ON liketable.rp_no=commenttable.rp_no ORDER BY liketable.`rp_no` DESC LIMIT "+((pageNum-1)*pageSize)+","+pageSize+"";
		List<RationalProposal> CommunityDataList = RationalProposal.dao.find(sql);
		Record resp = new Record();
		resp.set("CommunityDataList", CommunityDataList);
		resp.set("code", 200);
		renderJson(resp);

	}

	public void like() {

		String likeuserid = getPara("likeuserid");
		String rp_num = getPara("rpno");
		Record resp = new Record();

		if (rp_num != null && likeuserid != null) {
			String sql = "INSERT INTO rp_like (rp_num,userid,isLike) Values ('" + rp_num + "','" + likeuserid + "',1)";
			Db.update(sql);
			resp.set("code", 200);
		} else {
			resp.set("code", 0);
		}
		renderJson(resp);

	}

	public void submitcomment() {

		String rp_num = getPara("rpno");
		String commentuserid = getPara("commentuserid");
		String commentusername = getPara("commentusername");
		String comment_content = getPara("comment_content");

		Record resp = new Record();

		if (rp_num != null && commentuserid != null) {
			String sql = "INSERT INTO rp_comment (rp_numb,comment_userid,comment_username,comment_content,isComment) Values ('"
					+ rp_num + "','" + commentuserid + "','" + commentusername + "','" + comment_content + "',1)";
			Db.update(sql);
			List<Record> maxId = Db.find("select * from rp_comment");
			String id = maxId.get(maxId.size() - 1).getStr("id");
//			System.out.println(id);
			resp.set("code", 200);
			resp.set("maxId", id);

		} else {
			resp.set("code", 0);
		}
		renderJson(resp);

	}

	public void deleteComment() {

		String id = getPara("id");

		Record resp = new Record();
//		System.out.println("id item is");
//		System.out.println(id);
		if (id != null) {
			Record deleteItem = Db.findById("rp_comment", id);
//			System.out.println("delete item is");
//			System.out.println(deleteItem);
			String rpnum = deleteItem.getStr("rp_numb");
			Db.deleteById("rp_comment", id);
			resp.set("rpnum", rpnum);
			resp.set("code", 200);
		} else {
			resp.set("code", 0);
		}
		renderJson(resp);

	}

	public void getMonthKpi() throws ParseException {
		Record record = new Record();
		String[] selectDates = get("checkDate").split("-");
		int curMonth = Integer.parseInt(selectDates[1]);
		int year = Integer.parseInt(selectDates[0]);
		// 获取所选月的天数
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = fmt.parse(get("checkDate") + "-01");
		int day_qty = Commen.getDaysOfMonth(date);
		// 获取每个车间的月度数据
		String sql1 = "SELECT year_t1.id AS id,year_t1.pid AS pid,  " + 
				"IFNULL(year_t1.`month_total`,0) AS month_total,  " + 
				"IFNULL(year_t1.`month_accept`,0) AS month_accept,  " + 
				"IFNULL(year_t1.`month_checked`,0) AS month_checked,  " + 
				"IFNULL(year_t1.`month_unclosed`,0) AS month_unclosed,  " + 
				"IFNULL(year_t2.`month_closed`,0) AS month_closed,  " + 
				"IFNULL(year_t2.`year_total_accept`,0) AS year_total_accept, " + 
				"year_t1.`productionLine` AS productionLine, " + 
				"year_t1.`staff_qty` AS staff_qty,  " + 
				"year_t1.`workshop` AS w2   " + 
				"FROM(SELECT * FROM(SELECT * FROM(SELECT * FROM " + 
				"(SELECT * FROM rp_line_structure WHERE pid<10 AND id>6 AND pid<>6) AS rp LEFT JOIN  			 " + 
				" (SELECT * FROM ( " + 
				"SELECT create_time,COUNT(*) AS month_total,workshop " + 
				"FROM rational_proposal  WHERE create_time >= '"+year+"-"+curMonth+"-01' AND create_time <= '" + get("checkDate") + "-" + day_qty + "' GROUP BY  workshop) tt " + 
				"LEFT JOIN (SELECT create_time AS c,SUM(audit_result) AS month_accept,SUM(is_checked) AS month_checked,workshop ww2 " + 
				"FROM rational_proposal  WHERE create_time >= '"+year+"-"+curMonth+"-01' AND create_time <= '" + get("checkDate") + "-" + day_qty + "' AND approve_date <= '" + get("checkDate") + "-" + day_qty + "' GROUP BY  ww2	) tt2 " + 
				"ON tt.workshop = tt2.ww2 )AS rmonth  	ON rmonth.`workshop` = rp.`productionLine`) rp_monthly  		 " + 
				"LEFT JOIN (SELECT IFNULL((COUNT(*)),0) AS month_unclosed ,workshop  w  " + 
				"FROM rational_proposal  WHERE create_time >= '"+year+"-01-01' AND create_time <= '" + get("checkDate") + "-" + day_qty + "'  " + 
				"AND audit_result='1' AND (handle_date IS NULL OR handle_date> '" + get("checkDate") + "-" + day_qty + "') GROUP BY  w ) AS ryear " + 
				" ON ryear.`w` = rp_monthly.`productionLine` ) rp_yearly LEFT JOIN " + 
				"  (SELECT staff_qty,workshop_id FROM rp_staff_qty WHERE moth_date>= '" + get("checkDate") + "-01' AND moth_date <=  '" + get("checkDate") + "-" + day_qty + "' ) s " + 
				"  ON rp_yearly.`id` = s.`workshop_id` ORDER BY rp_yearly.`id`) year_t1  " + 
				"  LEFT JOIN ( SELECT year_total.month_closed AS month_closed, year_total.w1 AS w1 ,year_total_ac.year_total_accept AS year_total_accept " + 
				"  FROM (SELECT IFNULL((COUNT(*)),0) AS month_closed, workshop  w1 FROM rational_proposal " + 
				"  WHERE create_time >= '"+year+"-01-01' AND create_time <= '" + get("checkDate") + "-" + day_qty + "' AND  handle_date <= '" + get("checkDate") + "-" + day_qty + "' GROUP BY  w1) year_total " + 
				"LEFT JOIN (SELECT  IFNULL((COUNT(*)),0) AS year_total_accept ,workshop  w2  FROM rational_proposal  " + 
				" WHERE create_time >= '"+year+"-01-01' AND create_time <= '" + get("checkDate") + "-" + day_qty + "' AND audit_result = '1' AND approve_date <= '" + get("checkDate") + "-" + day_qty + "'  GROUP BY  w2) year_total_ac " + 
				"ON year_total.w1 = year_total_ac.w2) year_t2 ON year_t1.productionLine = year_t2.w1 " + 
				"";
//		String sql = "SELECT year_t1.id AS id,year_t1.pid AS pid, " + 
//				"IFNULL(year_t1.`month_total`,0) AS month_total, " + 
//				"IFNULL(year_t1.`month_accept`,0) AS month_accept, " + 
//				"IFNULL(year_t1.`month_checked`,0) AS month_checked, " + 
//				"IFNULL(year_t1.`year_total`,0) AS year_total, " + 
//				"IFNULL(year_t2.`year_total_closed`,0) AS year_total_closed, " + 
//				"IFNULL(year_t2.`year_total_accept`,0) AS year_total_accept,year_t1.`productionLine` productionLine, year_t1.`staff_qty` staff_qty, year_t1.`workshop` w2  " + 
//				"FROM(SELECT * FROM  " + 
//				"	(SELECT * FROM  " + 
//				"		(SELECT * FROM " + 
//				"			(SELECT * FROM rp_line_structure WHERE pid<10 AND id>6 AND pid<>6) AS rp  " + 
//				"			LEFT JOIN  " + 
//				"			(SELECT create_time,COUNT(*) AS month_total,SUM(audit_result) AS month_accept,SUM(is_checked) AS month_checked,workshop  " + 
//				"			FROM rational_proposal  WHERE create_time >= '"+year+"-"+curMonth+"-01' AND create_time <= '" + get("checkDate") + "-" + day_qty + "' GROUP BY  workshop )AS rmonth  " + 
//				"			ON rmonth.`workshop` = rp.`productionLine`) rp_monthly  " + 
//				"		LEFT JOIN  " + 
//				"		(SELECT COUNT(*) AS year_total,workshop  w  " + 
//				"		FROM rational_proposal  WHERE create_time >= '"+year+"-01-01' AND create_time <= '" + get("checkDate") + "-" + day_qty + "' AND is_checked = '1' GROUP BY  w ) AS ryear  " + 
//				"		ON ryear.`w` = rp_monthly.`productionLine` ) rp_yearly  " + 
//				"	LEFT JOIN  " + 
//				"	(SELECT staff_qty,workshop_id FROM rp_staff_qty WHERE moth_date>= '"+year+"-"+curMonth+"-01' AND moth_date <=  '" + get("checkDate") + "-" + day_qty + "' ) s  " + 
//				"	ON rp_yearly.`id` = s.`workshop_id` ORDER BY rp_yearly.`id`) year_t1 " + 
//				"LEFT JOIN ( " + 
//				"	SELECT year_total.year_total_closed year_total_closed, year_total.w1 w1 ,year_total_ac.year_total_accept year_total_accept  " + 
//				"	FROM (SELECT SUM(handle_result) AS year_total_closed, workshop  w1 FROM rational_proposal   " + 
//				"		WHERE create_time >= '"+year+"-01-01' AND create_time <= '" + get("checkDate") + "-" + day_qty + "' AND is_checked = '1' AND actural_finish_date <= '" + get("checkDate") + "-" + day_qty + "' " + 
//				"		GROUP BY  w1) year_total  " + 
//				"	LEFT JOIN  " + 
//				"		(SELECT  SUM(audit_result) AS year_total_accept ,workshop  w2  " + 
//				"		FROM rational_proposal   " + 
//				"		WHERE create_time >= '"+year+"-01-01' AND create_time <= '" + get("checkDate") + "-" + day_qty + "' AND is_checked = '1' AND approve_date <= '" + get("checkDate") + "-" + day_qty + "' " + 
//				"		GROUP BY  w2) year_total_ac " + 
//				"	ON year_total.w1 = year_total_ac.w2) year_t2 " + 
//				"ON year_t1.productionLine = year_t2.w1";
		List<Record> monthyList = Db.find(sql1);
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		Date cur_date = new Date(System.currentTimeMillis());
		String today = formatter.format(cur_date);
		//获取年度数据
//		String sqlyear = "SELECT rp_yearly.id AS id,rp_yearly.pid AS pid,IFNULL(rp_yearly.`year_total`,0) AS year_total,IFNULL(rp_yearly.`year_total_checked`,0) AS year_total_checked, " + 
//				"IFNULL(rp_yearly.`year_total_closed`,0) AS year_total_closed,IFNULL(rp_yearly.`year_total_accept`,0) AS year_total_accept, " + 
//				"rp_yearly.`productionLine` productionLine, s.`staff_qty` year_total_staff_qty FROM  " + 
//				"(SELECT * FROM(SELECT * FROM rp_line_structure WHERE pid<10 AND id>6 AND pid<>6)  AS rp LEFT JOIN  " + 
//				"(SELECT COUNT(*) AS year_total,SUM(handle_result) AS year_total_closed, SUM(audit_result) AS year_total_accept , SUM(is_checked) as year_total_checked , workshop  w  " + 
//				"FROM rational_proposal  WHERE create_time >= '"+year+"-01-01' AND create_time <= '" + today + "' GROUP BY  w ) AS ryear  " + 
//				"ON ryear.`w` = rp.`productionLine` ) rp_yearly  " + 
//				"LEFT JOIN (SELECT workshop_id,IFNULL(SUM(staff_qty),0) AS staff_qty FROM rp_staff_qty WHERE moth_date>= '"+year+"-01-01' AND moth_date <= '" + today + "'  " + 
//				"GROUP BY workshop_id) s  ON rp_yearly.`id` = s.`workshop_id` ORDER BY rp_yearly.`id`";
		String sqlyear = "SELECT year_t1.id AS id,year_t1.pid AS pid, " + 
				"IFNULL(year_t1.`year_total`,0) AS year_total, " + 
				"IFNULL(year_t2.`year_total_checked`,0) AS year_total_checked, " + 
				"IFNULL(year_t2.`year_total_closed`,0) AS year_total_closed, " + 
				"IFNULL(year_t2.`year_total_accept`,0) AS year_total_accept, " + 
				"year_t1.`productionLine` productionLine, year_t1.`staff_qty` year_total_staff_qty  FROM " + 
				"(SELECT * FROM (SELECT * FROM " + 
				"(SELECT * FROM rp_line_structure WHERE pid<10 AND id>6 AND pid<>6)  AS rp LEFT JOIN  " + 
				"(SELECT COUNT(*) AS year_total, workshop  w  " + 
				"FROM rational_proposal  WHERE create_time >= '"+year+"-01-01' AND create_time <= '"+year+"-12-31' GROUP BY  w ) AS ryear  " + 
				"ON ryear.`w` = rp.`productionLine` ) rp_yearly  " + 
				"LEFT JOIN  " + 
				"(SELECT workshop_id,IFNULL(SUM(staff_qty),0) AS staff_qty FROM rp_staff_qty WHERE moth_date>= '"+year+"-01-01' AND moth_date <= '"+year+"-12-31'  " + 
				"GROUP BY workshop_id) s   " + 
				"ON rp_yearly.`id` = s.`workshop_id`  " + 
				"ORDER BY rp_yearly.`id` " + 
				") year_t1 " + 
				"LEFT JOIN ( " + 
				"	SELECT year_total.year_total_closed year_total_closed, year_total.w1 w1 ,year_total_ac.year_total_accept year_total_accept,year_total_ac.year_total_checked year_total_checked  " + 
				"	FROM (SELECT SUM(handle_result) AS year_total_closed, workshop  w1 FROM rational_proposal   " + 
				"		WHERE create_time >= '"+year+"-01-01' AND create_time <= '"+year+"-12-31' AND audit_result = '1' AND  handle_date <= '"+year+"-12-31' " + 
				"		GROUP BY  w1) year_total  " + 
				"	LEFT JOIN  " + 
				"		(SELECT  SUM(audit_result) AS year_total_accept ,SUM(is_checked) AS year_total_checked ,workshop  w2  " + 
				"		FROM rational_proposal   " + 
				"		WHERE create_time >= '"+year+"-01-01' AND create_time <= '"+year+"-12-31' AND is_checked = '1' AND approve_date <= '"+year+"-12-31' "+ 
				"		GROUP BY  w2) year_total_ac " + 
				"	ON year_total.w1 = year_total_ac.w2) year_t2 " + 
				"ON year_t1.productionLine = year_t2.w1";
		List<Record> yearlyList = Db.find(sqlyear);

		// 获取事业部的名单，事业部的各月数据由前端各车间数据按事业部相加得到
		String sql_departs = "select * from rp_line_structure WHERE id<10 and id <>6 order by id ";
		List<Record> monthyDepartsList = Db.find(sql_departs);
		
		record.set("monthyDepartsList", monthyDepartsList);
		record.set("monthyList", monthyList);
		record.set("yearlyList", yearlyList);
		renderJson(record);
	}
	
	public void getWorkshopData(){
		Record record = new Record();
		List<RpLineStructure> total = RpLineStructure.dao.find("select * from rp_line_structure where pid<10 and pid > 0 and id <>6 and pid<>6 ");
		record.set("total",total);
		renderJson(record);
	}

	public void saveStaffQty(){
		RpStaffQty s6sStaffQty = getModel(RpStaffQty.class,"");

		s6sStaffQty.save();
		Record record = new Record();
		record.set("status",200);
		renderJson(record);
	}
	public void getKPITargetByYear() {
		List<Record> kpiList = Db.find("select * from rp_kpi_target where year = '"+get("year")+"';");
		Record record = new Record();
		record.set("status",200);
		record.set("kpiList", kpiList);
		renderJson(record);
	}

}
