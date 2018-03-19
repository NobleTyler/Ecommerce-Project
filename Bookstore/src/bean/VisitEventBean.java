package bean;

import java.util.Date;

public class VisitEventBean {

	private Date day;
	private String bid, eventType;
	
	public VisitEventBean(Date day, String bid, String eventType) {
		super();
		this.day = day;
		this.bid = bid;
		this.eventType = eventType;
	}
	
	public Date getDay() {
		return day;
	}
	
	public void setDay(Date day) {
		this.day = day;
	}
	
	public String getBid() {
		return bid;
	}
	
	public void setBid(String bid) {
		this.bid = bid;
	}
	
	public String getEventType() {
		return eventType;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
}
