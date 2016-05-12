package data;

import java.util.List;

public class Checkin {
	private List<String> checkin_info;
	private String type;
	private String business_id;
	
	public List<String> getCheckin_info() {
		return checkin_info;
	}
	public void setCheckin_info(List<String> checkin_info) {
		this.checkin_info = checkin_info;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}
}
