package com.ray.common.model.base;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePrecisionInventory<M extends BasePrecisionInventory<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 产品二维码
	 */
	public M setQrcode(java.lang.String qrcode) {
		set("qrcode", qrcode);
		return (M)this;
	}
	
	/**
	 * 产品二维码
	 */
	public java.lang.String getQrcode() {
		return getStr("qrcode");
	}

	/**
	 * 缺陷类型
	 */
	public M setDefectType(java.lang.String defectType) {
		set("defect_type", defectType);
		return (M)this;
	}
	
	/**
	 * 缺陷类型
	 */
	public java.lang.String getDefectType() {
		return getStr("defect_type");
	}

	/**
	 * 产品名称
	 */
	public M setPartName(java.lang.String partName) {
		set("part_name", partName);
		return (M)this;
	}
	
	/**
	 * 产品名称
	 */
	public java.lang.String getPartName() {
		return getStr("part_name");
	}

	/**
	 * 入库时间
	 */
	public M setEntryDate(java.util.Date entryDate) {
		set("entry_date", entryDate);
		return (M)this;
	}
	
	/**
	 * 入库时间
	 */
	public java.util.Date getEntryDate() {
		return get("entry_date");
	}

	/**
	 * 入库操作人
	 */
	public M setEntryPerson(java.lang.String entryPerson) {
		set("entry_person", entryPerson);
		return (M)this;
	}
	
	/**
	 * 入库操作人
	 */
	public java.lang.String getEntryPerson() {
		return getStr("entry_person");
	}

	/**
	 * 出库时间
	 */
	public M setDeliveryDate(java.util.Date deliveryDate) {
		set("delivery_date", deliveryDate);
		return (M)this;
	}
	
	/**
	 * 出库时间
	 */
	public java.util.Date getDeliveryDate() {
		return get("delivery_date");
	}

	/**
	 * 出库负责人
	 */
	public M setDeliveryPerson(java.lang.String deliveryPerson) {
		set("delivery_person", deliveryPerson);
		return (M)this;
	}
	
	/**
	 * 出库负责人
	 */
	public java.lang.String getDeliveryPerson() {
		return getStr("delivery_person");
	}

	/**
	 * 返修负责人
	 */
	public M setRepairPerson(java.lang.String repairPerson) {
		set("repair_person", repairPerson);
		return (M)this;
	}
	
	/**
	 * 返修负责人
	 */
	public java.lang.String getRepairPerson() {
		return getStr("repair_person");
	}

	/**
	 * 返修结果：0=返修不合格，1=返修合格
	 */
	public M setRepairResult(java.lang.Integer repairResult) {
		set("repair_result", repairResult);
		return (M)this;
	}
	
	/**
	 * 返修结果：0=返修不合格，1=返修合格
	 */
	public java.lang.Integer getRepairResult() {
		return getInt("repair_result");
	}

	/**
	 * 返修完成时间
	 */
	public M setRepairFinishDate(java.util.Date repairFinishDate) {
		set("repair_finish_date", repairFinishDate);
		return (M)this;
	}
	
	/**
	 * 返修完成时间
	 */
	public java.util.Date getRepairFinishDate() {
		return get("repair_finish_date");
	}

	/**
	 * 新二维码
	 */
	public M setNewQrcode(java.lang.String newQrcode) {
		set("new_qrcode", newQrcode);
		return (M)this;
	}
	
	/**
	 * 新二维码
	 */
	public java.lang.String getNewQrcode() {
		return getStr("new_qrcode");
	}

}
