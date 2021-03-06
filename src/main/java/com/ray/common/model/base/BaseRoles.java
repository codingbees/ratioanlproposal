package com.ray.common.model.base;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseRoles<M extends BaseRoles<M>> extends Model<M> implements IBean {

	/**
	 * ID
	 */
	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	/**
	 * ID
	 */
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 角色
	 */
	public M setRoleName(java.lang.String roleName) {
		set("role_name", roleName);
		return (M)this;
	}
	
	/**
	 * 角色
	 */
	public java.lang.String getRoleName() {
		return getStr("role_name");
	}

	/**
	 * 角色名称
	 */
	public M setRoleNickName(java.lang.String roleNickName) {
		set("role_nick_name", roleNickName);
		return (M)this;
	}
	
	/**
	 * 角色名称
	 */
	public java.lang.String getRoleNickName() {
		return getStr("role_nick_name");
	}

	/**
	 * 具体描述
	 */
	public M setRoleDesc(java.lang.String roleDesc) {
		set("role_desc", roleDesc);
		return (M)this;
	}
	
	/**
	 * 具体描述
	 */
	public java.lang.String getRoleDesc() {
		return getStr("role_desc");
	}

}
