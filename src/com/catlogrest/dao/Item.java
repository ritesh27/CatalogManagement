package com.catlogrest.dao;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "Item")
public class Item implements Serializable{
	
	private String sku_ID;
	
	private String itemName;
	
	private String imageURL;
	

	public String getImageURL() {
		return imageURL;
	}
	@XmlElement
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	public String getSku_ID() {
		return sku_ID;
	}
	@XmlElement
	public void setSku_ID(String sku_ID) {
		this.sku_ID = sku_ID;
	}

	public String getItemName() {
		return itemName;
	}
	@XmlElement
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	

}
