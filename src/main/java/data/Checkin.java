package data;

import java.util.List;
import java.util.Map;

public class Checkin {
	private Map<String, Integer> checkin_info;
	private String type;
	private String business_id;
	
	public Map<String, Integer> getCheckin_info() {
		return checkin_info;
	}
	public void setCheckin_info(Map<String, Integer> checkin_info) {
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
