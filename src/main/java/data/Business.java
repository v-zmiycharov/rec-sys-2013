package data;

import java.util.List;

public class Business {
	private String business_id;
	private String full_address;
	private boolean open;
	private List<String> categories;
	private String city;
	private int review_count;
	private String name;
	private List<String> neighborhoods;
	private double longitude;
	private String state;
	private double stars;
	private double latitude;
	private String type;
	
	public String getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}
	public String getFull_address() {
		return full_address;
	}
	public void setFull_address(String full_address) {
		this.full_address = full_address;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getReview_count() {
		return review_count;
	}
	public void setReview_count(int review_count) {
		this.review_count = review_count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getNeighborhoods() {
		return neighborhoods;
	}
	public void setNeighborhoods(List<String> neighborhoods) {
		this.neighborhoods = neighborhoods;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public double getStars() {
		return stars;
	}
	public void setStars(double stars) {
		this.stars = stars;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
