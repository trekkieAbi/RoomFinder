package com.room.finder.model;

import lombok.Getter;
import lombok.Setter;


public class Landlord extends User{
    private Integer id;
    private Integer houseNumber;
    private Integer userId;
    private Integer roleId;

	public Landlord(Integer houseNumber, Integer userId, Integer roleId) {
		super();
		this.houseNumber = houseNumber;
        this.userId = userId;
        this.roleId = roleId;
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
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
