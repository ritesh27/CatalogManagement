package com.catlogrest.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "Catlog")
public class CatlogVO implements Serializable{

	private String catLogName;
	
	private Object _id;
	
	private String catID;
	
	private List<SubCatlogVO> subCatLog;
	
	public List<SubCatlogVO> getSubCatLog() {
		return subCatLog;
	}
	@XmlElement
	public void setSubCatLog(List<SubCatlogVO> subCatLog) {
		this.subCatLog = subCatLog;
	}
	public Object get_id() {
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

	/*public Map<String,ArrayList<Item>> getItemList() {
		return itemList;
	}
	@XmlElement(name="items")
	public void setItemList(Map<String,ArrayList<Item>> itemList) {
		this.itemList = itemList;
	}*/
	
}
