package com.rishav.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {
	@Id
	private String name;
	private int price;
	private String aname;
	private String pname;
	@Column(nullable = true , columnDefinition = "longblob")
	private byte[] image;
	@Column(nullable = true , columnDefinition = "longblob")
	private byte[] content;
	private boolean hasContent;
	
	
	
	public boolean isHasContent() {
		return hasContent;
	}
	public void setHasContent(boolean hasContent) {
		this.hasContent = hasContent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
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
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}

}
