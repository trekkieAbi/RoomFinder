package com.room.finder.dto;
import com.room.finder.model.Landlord;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


public class AdvertisementDto {
    private Integer id;
    private String title;
    private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date roomAvailableDate;
    private String address;
    private Integer rent;
	private Landlord landlord;

	private List<Integer> roomId;

	public List<Integer> getRoomId() {
		return roomId;
	}

	public void setRoomId(List<Integer> roomId) {
		this.roomId = roomId;
	}

	private List<RoomDto> roomDtoList;
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
	public List<RoomDto> getRoomDtoList() {
		return roomDtoList;
	}
	public void setRoomDtoList(List<RoomDto> roomDtoList) {
		this.roomDtoList = roomDtoList;
	}

	public Landlord getLandlord() {
		return landlord;
	}

	public void setLandlord(Landlord landlord) {
		this.landlord = landlord;
	}
}
