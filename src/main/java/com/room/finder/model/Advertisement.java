package com.room.finder.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Advertisement {
    private Integer id;
    private String title;
    private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date roomAvailableDate;
    private String address;
    private Integer rent;
    private Integer landlordId;
    private AdvertisementStatus status;
private Integer numberOfRoom;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Date getRoomAvailableDate() {
	return roomAvailableDate;
}
public void setRoomAvailableDate(Date roomAvailableDate) {
	this.roomAvailableDate = roomAvailableDate;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public Integer getRent() {
	return rent;
}
public void setRent(Integer rent) {
	this.rent = rent;
}
public Integer getLandlordId() {
	return landlordId;
}
public void setLandlordId(Integer landlordId) {
	this.landlordId = landlordId;
}
public AdvertisementStatus getStatus() {
	return status;
}
public void setStatus(AdvertisementStatus status) {
	this.status = status;
}
public Integer getNumberOfRoom() {
	return numberOfRoom;
}
public void setNumberOfRoom(Integer numberOfRoom) {
	this.numberOfRoom = numberOfRoom;
}




}
