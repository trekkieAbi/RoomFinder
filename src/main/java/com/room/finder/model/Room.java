package com.room.finder.model;

public class Room {
	   private Integer id;
	    private String name;
	    private String imagePath;
	    private String image;
	    private boolean flag;
	    private Integer advertisementId;
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
		public String getImagePath() {
			return imagePath;
		}
		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public boolean isFlag() {
			return flag;
		}
		public void setFlag(boolean flag) {
			this.flag = flag;
		}
		public Integer getAdvertisementId() {
			return advertisementId;
		}
		public void setAdvertisementId(Integer advertisementId) {
			this.advertisementId = advertisementId;
		}
}
