package com.ray.common.model.base;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseRationalProposal<M extends BaseRationalProposal<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 创建时间
	 */
	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	/**
	 * 编号
	 */
	public M setRpNo(java.lang.String rpNo) {
		set("rp_no", rpNo);
		return (M)this;
	}
	
	/**
	 * 编号
	 */
	public java.lang.String getRpNo() {
		return getStr("rp_no");
	}

	/**
	 * 发布日期
	 */
	public M setReleaseDate(java.util.Date releaseDate) {
		set("release_date", releaseDate);
		return (M)this;
	}
	
	/**
	 * 发布日期
	 */
	public java.util.Date getReleaseDate() {
		return get("release_date");
	}

	/**
	 * 问题第一发现人员工ID
	 */
	public M setFindUserid(java.lang.String findUserid) {
		set("find_userid", findUserid);
		return (M)this;
	}
	
	/**
	 * 问题第一发现人员工ID
	 */
	public java.lang.String getFindUserid() {
		return getStr("find_userid");
	}

	/**
	 * 问题第一发现人姓名
	 */
	public M setFindUsername(java.lang.String findUsername) {
		set("find_username", findUsername);
		return (M)this;
	}
	
	/**
	 * 问题第一发现人姓名
	 */
	public java.lang.String getFindUsername() {
		return getStr("find_username");
	}

	/**
	 * 问题第一发现人部门ID
	 */
	public M setFindUserPartId(java.lang.String findUserPartId) {
		set("find_user_part_id", findUserPartId);
		return (M)this;
	}
	
	/**
	 * 问题第一发现人部门ID
	 */
	public java.lang.String getFindUserPartId() {
		return getStr("find_user_part_id");
	}

	/**
	 * 问题第一发现人部门名称
	 */
	public M setFindUserPartName(java.lang.String findUserPartName) {
		set("find_user_part_name", findUserPartName);
		return (M)this;
	}
	
	/**
	 * 问题第一发现人部门名称
	 */
	public java.lang.String getFindUserPartName() {
		return getStr("find_user_part_name");
	}

	/**
	 * 问题所在事业部
	 */
	public M setDepartment(java.lang.String department) {
		set("department", department);
		return (M)this;
	}
	
	/**
	 * 问题所在事业部
	 */
	public java.lang.String getDepartment() {
		return getStr("department");
	}

	/**
	 * 问题所在车间
	 */
	public M setWorkshop(java.lang.String workshop) {
		set("workshop", workshop);
		return (M)this;
	}
	
	/**
	 * 问题所在车间
	 */
	public java.lang.String getWorkshop() {
		return getStr("workshop");
	}

	/**
	 * 问题所在产线
	 */
	public M setProdcutionLine(java.lang.String prodcutionLine) {
		set("prodcutionLine", prodcutionLine);
		return (M)this;
	}
	
	/**
	 * 问题所在产线
	 */
	public java.lang.String getProdcutionLine() {
		return getStr("prodcutionLine");
	}

	/**
	 * 改善建议
	 */
	public M setProposal(java.lang.String proposal) {
		set("proposal", proposal);
		return (M)this;
	}
	
	/**
	 * 改善建议
	 */
	public java.lang.String getProposal() {
		return getStr("proposal");
	}

	/**
	 * 问题描述
	 */
	public M setDescription(java.lang.String description) {
		set("description", description);
		return (M)this;
	}
	
	/**
	 * 问题描述
	 */
	public java.lang.String getDescription() {
		return getStr("description");
	}

	/**
	 * 问题图片
	 */
	public M setPictureOfProblem(java.lang.String pictureOfProblem) {
		set("picture_of_problem", pictureOfProblem);
		return (M)this;
	}
	
	/**
	 * 问题图片
	 */
	public java.lang.String getPictureOfProblem() {
		return getStr("picture_of_problem");
	}

	/**
	 * 改善类型
	 */
	public M setImproveType(java.lang.String improveType) {
		set("improve_type", improveType);
		return (M)this;
	}
	
	/**
	 * 改善类型
	 */
	public java.lang.String getImproveType() {
		return getStr("improve_type");
	}

	/**
	 * 是否已审核，0=未审核，1=已审核
	 */
	public M setIsChecked(java.lang.Integer isChecked) {
		set("is_checked", isChecked);
		return (M)this;
	}
	
	/**
	 * 是否已审核，0=未审核，1=已审核
	 */
	public java.lang.Integer getIsChecked() {
		return getInt("is_checked");
	}

	/**
	 * 审核人ID
	 */
	public M setAuditorUserid(java.lang.String auditorUserid) {
		set("auditor_userid", auditorUserid);
		return (M)this;
	}
	
	/**
	 * 审核人ID
	 */
	public java.lang.String getAuditorUserid() {
		return getStr("auditor_userid");
	}

	/**
	 * 审核人姓名
	 */
	public M setAuditorUsername(java.lang.String auditorUsername) {
		set("auditor_username", auditorUsername);
		return (M)this;
	}
	
	/**
	 * 审核人姓名
	 */
	public java.lang.String getAuditorUsername() {
		return getStr("auditor_username");
	}

	/**
	 * 审核日期
	 */
	public M setApproveDate(java.util.Date approveDate) {
		set("approve_date", approveDate);
		return (M)this;
	}
	
	/**
	 * 审核日期
	 */
	public java.util.Date getApproveDate() {
		return get("approve_date");
	}

	/**
	 * 审核结果，0=拒绝，1=同意
	 */
	public M setAuditResult(java.lang.Integer auditResult) {
		set("audit_result", auditResult);
		return (M)this;
	}
	
	/**
	 * 审核结果，0=拒绝，1=同意
	 */
	public java.lang.Integer getAuditResult() {
		return getInt("audit_result");
	}

	/**
	 * 审核意见
	 */
	public M setAuditOpinion(java.lang.String auditOpinion) {
		set("audit_opinion", auditOpinion);
		return (M)this;
	}
	
	/**
	 * 审核意见
	 */
	public java.lang.String getAuditOpinion() {
		return getStr("audit_opinion");
	}

	/**
	 * 分值
	 */
	public M setScores(java.lang.Integer scores) {
		set("scores", scores);
		return (M)this;
	}
	
	/**
	 * 分值
	 */
	public java.lang.Integer getScores() {
		return getInt("scores");
	}

	/**
	 * 预计完成时间
	 */
	public M setCommitFinishDate(java.util.Date commitFinishDate) {
		set("commit_finish_date", commitFinishDate);
		return (M)this;
	}
	
	/**
	 * 预计完成时间
	 */
	public java.util.Date getCommitFinishDate() {
		return get("commit_finish_date");
	}

	/**
	 * 问题完成情况描述
	 */
	public M setDescriptionAfterImprove(java.lang.String descriptionAfterImprove) {
		set("description_after_improve", descriptionAfterImprove);
		return (M)this;
	}
	
	/**
	 * 问题完成情况描述
	 */
	public java.lang.String getDescriptionAfterImprove() {
		return getStr("description_after_improve");
	}

	/**
	 * 处理人姓名
	 */
	public M setHandlerUsername(java.lang.String handlerUsername) {
		set("handler_username", handlerUsername);
		return (M)this;
	}
	
	/**
	 * 处理人姓名
	 */
	public java.lang.String getHandlerUsername() {
		return getStr("handler_username");
	}

	/**
	 * 处理人ID
	 */
	public M setHandlerUserid(java.lang.String handlerUserid) {
		set("handler_userid", handlerUserid);
		return (M)this;
	}
	
	/**
	 * 处理人ID
	 */
	public java.lang.String getHandlerUserid() {
		return getStr("handler_userid");
	}

	/**
	 * 实际完成时间
	 */
	public M setActuralFinishDate(java.util.Date acturalFinishDate) {
		set("actural_finish_date", acturalFinishDate);
		return (M)this;
	}
	
	/**
	 * 实际完成时间
	 */
	public java.util.Date getActuralFinishDate() {
		return get("actural_finish_date");
	}

	/**
	 * 处理结果：0=未处理，1=已处理, 默认为0
	 */
	public M setHandleResult(java.lang.Integer handleResult) {
		set("handle_result", handleResult);
		return (M)this;
	}
	
	/**
	 * 处理结果：0=未处理，1=已处理, 默认为0
	 */
	public java.lang.Integer getHandleResult() {
		return getInt("handle_result");
	}

	/**
	 * 处理完成日期（填表日期）
	 */
	public M setHandleDate(java.util.Date handleDate) {
		set("handle_date", handleDate);
		return (M)this;
	}
	
	/**
	 * 处理完成日期（填表日期）
	 */
	public java.util.Date getHandleDate() {
		return get("handle_date");
	}

	/**
	 * 整改后照片
	 */
	public M setPictureAfterImprove(java.lang.String pictureAfterImprove) {
		set("picture_after_improve", pictureAfterImprove);
		return (M)this;
	}
	
	/**
	 * 整改后照片
	 */
	public java.lang.String getPictureAfterImprove() {
		return getStr("picture_after_improve");
	}

	/**
	 * 备注
	 */
	public M setComment(java.lang.String comment) {
		set("comment", comment);
		return (M)this;
	}
	
	/**
	 * 备注
	 */
	public java.lang.String getComment() {
		return getStr("comment");
	}

}
