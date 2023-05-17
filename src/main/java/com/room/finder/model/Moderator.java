package com.room.finder.model;

public class Moderator extends User{
    private Integer id;
    private Integer userId;
    private Integer roleId;
	public Moderator(Integer userId,Integer roleId) {
		super();
		this.userId=userId;
		this.roleId=roleId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
 }
