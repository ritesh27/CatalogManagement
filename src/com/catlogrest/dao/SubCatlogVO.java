package com.catlogrest.dao;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "subcatlog")
public class SubCatlogVO implements Serializable{

	private String subCatlogName;
	
	private Object _id;
	
	private String catID;
	
	public String getSubCatlogName() {
		return subCatlogName;
	}
	@XmlElement
	public void setSubCatlogName(String subCatlogName) {
		this.subCatlogName = subCatlogName;
	}

	public Object get_id() {
		return _id;
	}
	@XmlElement
	public void set_id(Object _id) {
		this._id = _id;
	}

	public String getCatID() {
		return catID;
	}
	@XmlElement
	public void setCatID(String catID) {
		this.catID = catID;
	}

	public List<Item> getItemList() {
		return itemList;
	}
	@XmlElement
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	private List<Item> itemList;
	
	/*public Object get_id() {
		return _id;
	}
	public void set_id(Object _id) {
		this._id = _id;
	}
	public String getCatID() {
		return catID;
	}
	@XmlElement
	public void setCatID(String catID) {
		this.catID = catID;
	}


	public String getCatLogName() {
		return catLogName;
	}
	@XmlElement
	public void setCatLogName(String catLogName) {
		this.catLogName = catLogName;
	}

	public Map<String,ArrayList<Item>> getItemList() {
		return itemList;
	}
	@XmlElement(name="items")
	public void setItemList(Map<String,ArrayList<Item>> itemList) {
		this.itemList = itemList;
	}*/
	
}
