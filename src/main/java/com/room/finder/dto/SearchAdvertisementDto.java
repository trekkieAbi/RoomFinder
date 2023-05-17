package com.room.finder.dto;

public class SearchAdvertisementDto {
	 private Integer advertisementId;
	    private Integer landlordId;
		public Integer getAdvertisementId() {
			return advertisementId;
		}
		public void setAdvertisementId(Integer advertisementId) {
			this.advertisementId = advertisementId;
		}
		public Integer getLandlordId() {
			return landlordId;
		}
		public void setLandlordId(Integer landlordId) {
			this.landlordId = landlordId;
		}
}
