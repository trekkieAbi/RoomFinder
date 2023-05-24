package com.room.finder.model;


public class Customer extends User {
    private Integer id;
    private String phoneNumber;
    private Integer userId;
    private Integer roleid;

    public Customer(String phoneNumber, Integer userId, Integer roleid) {
    	super();
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.roleid = roleid;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}


    

}
