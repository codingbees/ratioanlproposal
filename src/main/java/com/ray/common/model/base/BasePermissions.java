package com.ray.common.model.base;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePermissions<M extends BasePermissions<M>> extends Model<M> implements IBean {

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
	 * 权限名称
	 */
	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	/**
	 * 权限名称
	 */
	public java.lang.String getName() {
		return getStr("name");
	}

	/**
	 * 权限表达式
	 */
	public M setPermissionName(java.lang.String permissionName) {
		set("permission_name", permissionName);
		return (M)this;
	}
	
	/**
	 * 权限表达式
	 */
	public java.lang.String getPermissionName() {
		return getStr("permission_name");
	}

	/**
	 * 权限类型：1菜单，2按钮，3数据
	 */
	public M setType(java.lang.Integer type) {
		set("type", type);
		return (M)this;
	}
	
	/**
	 * 权限类型：1菜单，2按钮，3数据
	 */
	public java.lang.Integer getType() {
		return getInt("type");
	}

	/**
	 * 关联菜单ID
	 */
	public M setGlId(java.lang.Integer glId) {
		set("gl_id", glId);
		return (M)this;
	}
	
	/**
	 * 关联菜单ID
	 */
	public java.lang.Integer getGlId() {
		return getInt("gl_id");
	}

}
