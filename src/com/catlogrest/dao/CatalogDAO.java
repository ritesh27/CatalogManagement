package com.catlogrest.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class CatalogDAO {
	MongoClient mongo;
	DBCollection table = null;
	DBCollection itemtable = null;
	DB db;

	private DBCollection getDBCollection() {

		try {
			if (table == null) {
				mongo = new MongoClient("localhost", 27017);
				db = mongo.getDB("testdb");
				table = db.getCollection("catlog");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return table;
	}
	
	private DBCollection getDBCollectionItem() {

		try {
			if (itemtable == null) {
				mongo = new MongoClient("localhost", 27017);
				db = mongo.getDB("testdb");
				itemtable = db.getCollection("items");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return itemtable;
	}
	

	public Map<String, List<Item>> getAllCatalog() {

		DBCollection table = getDBCollection();

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("item", new BasicDBObject("$ne", null));
		// searchQuery.put("name", "mkyong");

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}

		return null;
	}

	public int createCategory(String catName) {
		int insertStatus = 0;
		try {
			DBCollection dbtable = getDBCollection();
			BasicDBObject document = new BasicDBObject();
			document.put("catid", new Random().nextInt());
			document.put("categoryName", catName);
			// document.put("createdDate", new Date());
			dbtable.insert(document);
		} catch (Exception e) {

			insertStatus = 1;
		}
		return insertStatus;
	}

	public int createSubCategory(String catName, String subCatName) {
		int insertStatus = 0;
		try {
			DBCollection dbtable = getDBCollection();
			BasicDBObject document = new BasicDBObject();
			document.put("catid", getCategoryID(catName));
			document.put("categoryName", catName);
			// subCatID
			document.put("subCatID", new Random().nextInt());
			document.put("subCatName", subCatName);
			// document.put("createdDate", new Date());
			dbtable.insert(document);
		} catch (Exception e) {

			insertStatus = 1;
		}
		return insertStatus;
	}

/*	public int insertItem(String catName, String subCatName, String filePath) {
		int insertStatus = 0;
		try {
			DBCollection dbtable = getDBCollection();
			BasicDBObject document = new BasicDBObject();
			int catid = getCategoryID(catName);
			document.put("catid", catid);
			document.put("categoryName", catName);
			// subCatID
			document.put("subCatID", getSubCategoryID(catid, catName, subCatName));
			document.put("subCatName", subCatName);

			File imageFile = new File("C:\\images\\boys\\top1.jpg");
			GridFS gfsPhoto = new GridFS(db);
			GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
			gfsFile.put("itemID", new Random().nextInt());
			gfsFile.put("itemName", "whiteshirt");
			document.put("item", gfsFile);

			dbtable.insert(document);
		} catch (Exception e) {

			insertStatus = 1;
		}
		return insertStatus;
	}*/
	
	public int insertItem(String catName, String subCatName, String itemName,String itemURL) {
		int insertStatus = 0;
		try {
			//DBCollection dbtable = getDBCollection();
			int catid = getCategoryID(catName);
			int subCatID = getSubCategoryID(catid, catName, subCatName);
			
			
			DBCollection dbitem = getDBCollectionItem();
			BasicDBObject document = new BasicDBObject();
			
			document.put("ItemID", new Random().nextInt());
			document.put("ItemName",itemName);
			document.put("ItemURL",itemURL);
			document.put("catid", catid);
			// subCatID
			document.put("subCatID", subCatID);
			dbitem.insert(document);
		} catch (Exception e) {

			insertStatus = 1;
		}
		return insertStatus;
	}

	public List<CatlogVO> getAllCategory() {
		Map<String, Map<String, ArrayList<Item>>> allCategory = new HashMap<String, Map<String, ArrayList<Item>>>();
		DBCollection table = getDBCollection();
		DBCollection Itemtable = getDBCollectionItem();
		BasicDBObject searchQuery = new BasicDBObject();
		DBCursor cursor = table.find(searchQuery);
		int catID = 0;
		while (cursor != null && cursor.hasNext()) {
			DBObject theObj = cursor.next();
			String categoryName = (String) theObj.get("categoryName");
			String subcategory = (String) theObj.get("subCatName");
			
			Integer categoryID = (Integer) theObj.get("catid");
			Integer subcategoryID = (Integer) theObj.get("subCatID");
			

			if (categoryName != null && allCategory.get(allCategory) == null) {
				Map<String, ArrayList<Item>> submap = new HashMap<String, ArrayList<Item>>();
				if (subcategory != null) {
					ArrayList<Item> items = new ArrayList<Item>();

					BasicDBList id = (BasicDBList) theObj.get("items");
					/*if(id != null){
					for (int count = 0; count < id.size(); count++) {
						Item item = new Item();
						BasicDBObject object = (BasicDBObject) id.get(count);
						int itemID = (Integer) object.get("itemID");
						item.setSku_ID(itemID + "");

						String itemName = (String) object.get("itemName");
						item.setItemName(itemName);

						String filename = (String)((BasicDBObject) object.get("itemImage")).get("filename");
						GridFS gfsPhoto = new GridFS(db);
						GridFSDBFile imageForOutput = gfsPhoto.findOne(filename);
						InputStream inputStream = imageForOutput.getInputStream();
						item.setImageStream(inputStream);
						items.add(item);
					}
					}*/
					
					BasicDBObject searchItems = new BasicDBObject();
					searchItems.put("catid", categoryID);
					searchItems.put("subCatID", subcategoryID);
					
					DBCursor itemCusor = Itemtable.find(searchItems);
					while (itemCusor != null && itemCusor.hasNext()) {
						Item item = new Item();
						DBObject itemObj = itemCusor.next();
						Integer itemID = (Integer) itemObj.get("ItemID");
						item.setSku_ID(itemID + "");
						String itemName = (String) itemObj.get("ItemName");
						item.setItemName(itemName);
						String itemURL = (String) itemObj.get("ItemURL");
						item.setImageURL(itemURL);
						items.add(item);
					}
					submap.put(subcategory, items);
				}
				allCategory.put(categoryName, submap);

			} else {

				Map<String, ArrayList<Item>> subcatlist = allCategory.get(categoryName);
				if (subcatlist.get(subcategory) != null && subcatlist.size() == 0) {

					ArrayList<Item> items = new ArrayList<Item>();


					BasicDBObject searchItems = new BasicDBObject();
					searchItems.put("catid", categoryID);
					searchItems.put("subCatID", subcategoryID);
					
					DBCursor itemCusor = Itemtable.find(searchItems);
					while (itemCusor != null && itemCusor.hasNext()) {
						Item item = new Item();
						DBObject itemObj = itemCusor.next();
						Integer itemID = (Integer) itemObj.get("ItemID");
						item.setSku_ID(itemID + "");
						String itemName = (String) itemObj.get("ItemName");
						item.setItemName(itemName);
						String itemURL = (String) itemObj.get("ItemURL");
						item.setImageURL(itemURL);
						items.add(item);
					}

					subcatlist.put(subcategory, items);

					allCategory.put(categoryName, subcatlist);
				} else {

					ArrayList<Item> items = new ArrayList<Item>();


					BasicDBObject searchItems = new BasicDBObject();
					searchItems.put("catid", categoryID);
					searchItems.put("subCatID", subcategoryID);
					
					DBCursor itemCusor = Itemtable.find(searchItems);
					while (itemCusor != null && itemCusor.hasNext()) {
						Item item = new Item();
						DBObject itemObj = itemCusor.next();
						Integer itemID = (Integer) itemObj.get("ItemID");
						item.setSku_ID(itemID + "");
						String itemName = (String) itemObj.get("ItemName");
						item.setItemName(itemName);
						String itemURL = (String) itemObj.get("ItemURL");
						item.setImageURL(itemURL);
						items.add(item);
					}

					subcatlist.put(subcategory, items);

					allCategory.put(categoryName, subcatlist);

				}
			}
		}
		
		
		// return catID;
		return buildCatlog(allCategory);

	}
	
	
	public List<CatlogVO> getCategory(String category) {
		Map<String, Map<String, ArrayList<Item>>> allCategory = new HashMap<String, Map<String, ArrayList<Item>>>();
		DBCollection table = getDBCollection();
		DBCollection Itemtable = getDBCollectionItem();
		BasicDBObject searchQuery = new BasicDBObject();
		BasicDBObject fields = new BasicDBObject();
		fields.put("categoryName", category);
		DBCursor cursor = table.find(fields);
		while (cursor != null && cursor.hasNext()) {
			DBObject theObj = cursor.next();
			String categoryName = (String) theObj.get("categoryName");
			String subcategory = (String) theObj.get("subCatName");
			
			Integer categoryID = (Integer) theObj.get("catid");
			Integer subcategoryID = (Integer) theObj.get("subCatID");
			

			if (categoryName != null && allCategory.get(category) == null) {
				Map<String, ArrayList<Item>> submap = new HashMap<String, ArrayList<Item>>();
				if (subcategory != null) {
					ArrayList<Item> items = new ArrayList<Item>();

					BasicDBList id = (BasicDBList) theObj.get("items");
					BasicDBObject searchItems = new BasicDBObject();
					searchItems.put("catid", categoryID);
					searchItems.put("subCatID", subcategoryID);
					
					DBCursor itemCusor = Itemtable.find(searchItems);
					while (itemCusor != null && itemCusor.hasNext()) {
						Item item = new Item();
						DBObject itemObj = itemCusor.next();
						Integer itemID = (Integer) itemObj.get("ItemID");
						item.setSku_ID(itemID + "");
						String itemName = (String) itemObj.get("ItemName");
						item.setItemName(itemName);
						String itemURL = (String) itemObj.get("ItemURL");
						item.setImageURL(itemURL);
						items.add(item);
					}
					submap.put(subcategory, items);
				}
				allCategory.put(categoryName, submap);

			} else {

				Map<String, ArrayList<Item>> subcatlist = allCategory.get(categoryName);
				if (subcatlist.get(subcategory) != null && subcatlist.size() == 0) {

					ArrayList<Item> items = new ArrayList<Item>();


					BasicDBObject searchItems = new BasicDBObject();
					searchItems.put("catid", categoryID);
					searchItems.put("subCatID", subcategoryID);
					
					DBCursor itemCusor = Itemtable.find(searchItems);
					while (itemCusor != null && itemCusor.hasNext()) {
						Item item = new Item();
						DBObject itemObj = itemCusor.next();
						Integer itemID = (Integer) itemObj.get("ItemID");
						item.setSku_ID(itemID + "");
						String itemName = (String) itemObj.get("ItemName");
						item.setItemName(itemName);
						String itemURL = (String) itemObj.get("ItemURL");
						item.setImageURL(itemURL);
						items.add(item);
					}

					subcatlist.put(subcategory, items);

					allCategory.put(categoryName, subcatlist);
				} else {

					ArrayList<Item> items = new ArrayList<Item>();


					BasicDBObject searchItems = new BasicDBObject();
					searchItems.put("catid", categoryID);
					searchItems.put("subCatID", subcategoryID);
					
					DBCursor itemCusor = Itemtable.find(searchItems);
					while (itemCusor != null && itemCusor.hasNext()) {
						Item item = new Item();
						DBObject itemObj = itemCusor.next();
						Integer itemID = (Integer) itemObj.get("ItemID");
						item.setSku_ID(itemID + "");
						String itemName = (String) itemObj.get("ItemName");
						item.setItemName(itemName);
						String itemURL = (String) itemObj.get("ItemURL");
						item.setImageURL(itemURL);
						items.add(item);
					}

					subcatlist.put(subcategory, items);

					allCategory.put(categoryName, subcatlist);

				}
			}
		}
		
		
		// return catID;
		return buildCatlog(allCategory);

	}
	
	public List<CatlogVO> getCategory(String category,String subCategory) {
		Map<String, Map<String, ArrayList<Item>>> allCategory = new HashMap<String, Map<String, ArrayList<Item>>>();
		DBCollection table = getDBCollection();
		DBCollection Itemtable = getDBCollectionItem();
		BasicDBObject searchQuery = new BasicDBObject();
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("categoryName", category);
		fields.put("subCatName", subCategory);
		DBCursor cursor = table.find(fields);
		while (cursor != null && cursor.hasNext()) {
			DBObject theObj = cursor.next();
			String categoryName = (String) theObj.get("categoryName");
			String subcategory = (String) theObj.get("subCatName");
			
			Integer categoryID = (Integer) theObj.get("catid");
			Integer subcategoryID = (Integer) theObj.get("subCatID");
			

			if (categoryName != null && allCategory.get(category) == null) {
				Map<String, ArrayList<Item>> submap = new HashMap<String, ArrayList<Item>>();
				if (subcategory != null) {
					ArrayList<Item> items = new ArrayList<Item>();

					BasicDBList id = (BasicDBList) theObj.get("items");
					BasicDBObject searchItems = new BasicDBObject();
					searchItems.put("catid", categoryID);
					searchItems.put("subCatID", subcategoryID);
					
					DBCursor itemCusor = Itemtable.find(searchItems);
					while (itemCusor != null && itemCusor.hasNext()) {
						Item item = new Item();
						DBObject itemObj = itemCusor.next();
						Integer itemID = (Integer) itemObj.get("ItemID");
						item.setSku_ID(itemID + "");
						String itemName = (String) itemObj.get("ItemName");
						item.setItemName(itemName);
						String itemURL = (String) itemObj.get("ItemURL");
						item.setImageURL(itemURL);
						items.add(item);
					}
					submap.put(subcategory, items);
				}
				allCategory.put(categoryName, submap);

			} else {

				Map<String, ArrayList<Item>> subcatlist = allCategory.get(categoryName);
				if (subcatlist.get(subcategory) != null && subcatlist.size() == 0) {

					ArrayList<Item> items = new ArrayList<Item>();


					BasicDBObject searchItems = new BasicDBObject();
					searchItems.put("catid", categoryID);
					searchItems.put("subCatID", subcategoryID);
					
					DBCursor itemCusor = Itemtable.find(searchItems);
					while (itemCusor != null && itemCusor.hasNext()) {
						Item item = new Item();
						DBObject itemObj = itemCusor.next();
						Integer itemID = (Integer) itemObj.get("ItemID");
						item.setSku_ID(itemID + "");
						String itemName = (String) itemObj.get("ItemName");
						item.setItemName(itemName);
						String itemURL = (String) itemObj.get("ItemURL");
						item.setImageURL(itemURL);
						items.add(item);
					}

					subcatlist.put(subcategory, items);

					allCategory.put(categoryName, subcatlist);
				} else {

					ArrayList<Item> items = new ArrayList<Item>();


					BasicDBObject searchItems = new BasicDBObject();
					searchItems.put("catid", categoryID);
					searchItems.put("subCatID", subcategoryID);
					
					DBCursor itemCusor = Itemtable.find(searchItems);
					while (itemCusor != null && itemCusor.hasNext()) {
						Item item = new Item();
						DBObject itemObj = itemCusor.next();
						Integer itemID = (Integer) itemObj.get("ItemID");
						item.setSku_ID(itemID + "");
						String itemName = (String) itemObj.get("ItemName");
						item.setItemName(itemName);
						String itemURL = (String) itemObj.get("ItemURL");
						item.setImageURL(itemURL);
						items.add(item);
					}

					subcatlist.put(subcategory, items);

					allCategory.put(categoryName, subcatlist);

				}
			}
		}
		
		
		// return catID;
		return buildCatlog(allCategory);

	}
	
	public int updateCategory(String catID,String category) {
		int insertStatus =0;
		
		DBCollection table = getDBCollection();
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("catid", new Integer(catID));
		//searchQuery.put("subCatID", null);
		
		BasicDBObject fields = new BasicDBObject();
		try{
		if(category != null )
			fields.put("$set",new BasicDBObject().append("categoryName", category));
		
		WriteResult reslut = table.updateMulti(searchQuery,fields);
		System.out.print(reslut);
		
		} catch (Exception e) {

			insertStatus = 1;
		}
		
		return insertStatus;
		
	}
	
	public int updatesubCategory(String catID,String category) {
		int insertStatus =0;
		
		DBCollection table = getDBCollection();
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("subCatID", new Integer(catID));
		//searchQuery.put("subCatID", null);
		
		BasicDBObject fields = new BasicDBObject();
		try{
		if(category != null )
			fields.put("$set",new BasicDBObject().append("subCatName", category));
		
		WriteResult reslut = table.updateMulti(searchQuery,fields);
		System.out.print(reslut);
		
		} catch (Exception e) {
			insertStatus = 1;
		}
		return insertStatus;
		
	}
	
	
	public int updateItem(String itemID,String itemUrl,String itemName) {
		int insertStatus =0;
		
		DBCollection table = getDBCollectionItem();
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("ItemID", itemID);
		
		BasicDBObject fields = new BasicDBObject();
		try{
		if(itemUrl != null )
			fields.put("ItemURL", itemUrl);
		if(itemName != null )
			fields.put("ItemName", itemName);
		table.update(searchQuery,fields);
		
		} catch (Exception e) {

			insertStatus = 1;
		}
		
		return insertStatus;
		
	}
	

	private List<CatlogVO>  buildCatlog(Map<String, Map<String, ArrayList<Item>>> allCategory ){
		ArrayList<CatlogVO> list = new ArrayList<CatlogVO>(); 
		
		  for(Map.Entry<String, Map<String, ArrayList<Item>>> entry : allCategory.entrySet()){
	            System.out.printf("Key : %s and Value: %s %n", entry.getKey(),entry.getValue());
	            
	            CatlogVO catlogVO = new CatlogVO();
	            catlogVO.setCatLogName(entry.getKey());
	           // catlogVO.setItemList(entry.getValue());
	            Map<String, ArrayList<Item>> subcatlog = entry.getValue();
	            ArrayList<SubCatlogVO> subCatList = new ArrayList<SubCatlogVO>();
	           
	            Set<String>subset = subcatlog.keySet();
	            Iterator<String> itorter = subset.iterator();
	            while(itorter.hasNext()){
	            	String subcat = itorter.next();
	            	SubCatlogVO subCatlogVo = new SubCatlogVO();
	            	subCatlogVo.setSubCatlogName(subcat);
	            	subCatlogVo.setItemList(subcatlog.get(subcat));
	            	subCatList.add(subCatlogVo);
	            }
	            catlogVO.setSubCatLog(subCatList);
	            list.add(catlogVO);
	            
	        }
		  return list;
	}
	private int getCategoryID(String catName) {

		DBCollection table = getDBCollection();

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("categoryName", catName);
		searchQuery.put("subCatName", null);
		DBCursor cursor = table.find(searchQuery);
		int catID = 0;
		while (cursor != null && cursor.hasNext()) {
			DBObject theObj = cursor.next();
			catID = (Integer) theObj.get("catid");
		}
		return catID;
	}

	private int getSubCategoryID(int catid, String catName, String subCatName) {

		DBCollection table = getDBCollection();

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("catid", catid);
		searchQuery.put("categoryName", catName);
		searchQuery.put("subCatName", subCatName);
		searchQuery.put("item", null);
		DBCursor cursor = table.find(searchQuery);
		int catID = 0;
		while (cursor != null && cursor.hasNext()) {
			DBObject theObj = cursor.next();
			catID = (Integer) theObj.get("subCatID");
		}
		return catID;
	}
	
	
	public int deleteCategory(String catid) {
		int insertStatus =0;
		try{
		DBCollection table = getDBCollection();
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("catid", new Integer(catid));
		
		table.remove(searchQuery);
		
		} catch (Exception e) {

			insertStatus = 1;
		}
		
		return insertStatus;
	}
	
	public int deletesubCategory(String catid) {
		int insertStatus =0;
		try{
		DBCollection table = getDBCollection();
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("subCatID", new Integer(catid));
		
		table.remove(searchQuery);
		
		} catch (Exception e) {

			insertStatus = 1;
		}
		
		return insertStatus;
	}
	
	public int deleteItem(String itemId) {
		int insertStatus =0;
		try{
		DBCollection table = getDBCollectionItem();
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("subCatID", new Integer(itemId));
		
		table.remove(searchQuery);
		
		} catch (Exception e) {

			insertStatus = 1;
		}
		
		return insertStatus;
	}
	
	

}
