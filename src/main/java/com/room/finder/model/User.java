package com.room.finder.model;

import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor

public class User {
    private Integer id;
	@NotBlank(message = "Invalid Name:Empty name")
	@NotNull(message = "Invalid Name:Name is NULL")
	@Size(min = 3,max = 30,message = "Invalid Name:Must be of 3-30 characters")
    private String name;
	@Email(message = "Invalid email")
    private String email;
@Size(min = 5,max = 15,message = "Invalid Password:Must be of 5-15 characters")
    private String password;
    private boolean isEnabled;
    private String roleName;
    public User(String name, String email, String password, boolean isEnabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
    }

    public User(Integer id, String name, String email, String password, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
    }

	public User() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
