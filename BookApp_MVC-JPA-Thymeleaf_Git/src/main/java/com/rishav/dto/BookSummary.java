package com.rishav.dto;

//DTO(Data Transfer Object) class or JPA Projection or Data Class

public class BookSummary {
	private String name;
	private String aname;
	private String pname;
	private int price;
	private boolean hasContent;
	
	
	
	public BookSummary(String name, String aname, String pname, int price, boolean hasContent) {
		super();
		this.name = name;
		this.aname = aname;
		this.pname = pname;
		this.price = price;
		this.hasContent = hasContent;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}


	public boolean isHasContent() {
		return hasContent;
	}


	public void setHasContent(boolean hasContent) {
		this.hasContent = hasContent;
	}
	
	

}
