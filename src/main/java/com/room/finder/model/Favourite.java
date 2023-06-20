package com.room.finder.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Favourite {
private Integer id;
private Integer roomId;
private Integer customerId;
private Integer advertisementId;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getRoomId() {
	return roomId;
}
public void setRoomId(Integer roomId) {
	this.roomId = roomId;
}
public Integer getCustomerId() {
	return customerId;
}
public void setCustomerId(Integer customerId) {
	this.customerId = customerId;
}
public Integer getAdvertisementId() {
	return advertisementId;
}
public void setAdvertisementId(Integer advertisementId) {
	this.advertisementId = advertisementId;
}



}
