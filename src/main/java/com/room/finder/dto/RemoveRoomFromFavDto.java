package com.room.finder.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RemoveRoomFromFavDto {
    private Integer roomId;
    private Integer customerId;
    private Integer advertisementId;
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
