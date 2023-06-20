package com.room.finder.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoomInformation {
    private String roomName;
    private String fullPath;
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public RoomInformation(String roomName, String fullPath) {
		super();
		this.roomName = roomName;
		this.fullPath = fullPath;
	}
    
    

    
}
