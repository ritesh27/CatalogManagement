package com.catlogrest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.catlogrest.dao.CatalogDAO;
import com.catlogrest.dao.CatlogVO;

/**
* This is catlog service
*/
@Path("/CatalogService")
public class CatalogService {
	   public CatalogDAO catDao = new CatalogDAO();
	   @GET
	   @Path("/category")
	   @Produces(MediaType.APPLICATION_XML)
	   public List<CatlogVO> getCategory(){
	     
		 //  Response.ok().entity(new GenericEntity<List<CatlogVO>>(getAllCategory()) {}).build();
		   return catDao.getAllCategory();
	   }
	   
	   @GET
	   @Path("/category/{categoryname}")
	   @Produces(MediaType.APPLICATION_XML)
	   public List<CatlogVO> getCategory(@PathParam("categoryname") String categoryname){
	     
		 //  Response.ok().entity(new GenericEntity<List<CatlogVO>>(getAllCategory()) {}).build();
		    return catDao.getCategory(categoryname);
	   }
	   
	   @GET
	   @Path("/category/{categoryname}/{subcategoryname}")
	   @Produces(MediaType.APPLICATION_XML)
	   public List<CatlogVO> getCategory(@PathParam("categoryname") String categoryname,@PathParam("subcategoryname") String subcategoryname){
	     
		 //  Response.ok().entity(new GenericEntity<List<CatlogVO>>(getAllCategory()) {}).build();
		   return catDao.getCategory(categoryname,subcategoryname);
	   }
	   
	   /* @GET
	   @Path("/category/{categoryname}/{subcategoryname}/{id}")
	   @Produces(MediaType.APPLICATION_XML)
	   public List<CatlogVO> getCategory(@PathParam("categoryname") String categoryname,@PathParam("subcategoryname") String subcategoryname,@PathParam("id") String id){
	     
		 //  Response.ok().entity(new GenericEntity<List<CatlogVO>>(getAllCategory()) {}).build();
		   return getAllCategory();
	   }*/
	   
	   //create
	  
	   @PUT
	   @Path("/incategory/{categoryname}")
	   @Produces(MediaType.APPLICATION_XML)
	   //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	   public Response createCategory(@PathParam("categoryname") String categoryname){
	     
		   int insertStatus = catDao.createCategory(categoryname);
		   if(insertStatus == 0){
			   return Response.ok().entity(new GenericEntity<String>("Success") {}).build();
		   }else {
			   return Response.status(Status.EXPECTATION_FAILED).entity(new GenericEntity<String>("Failed") {}).build();
		   }
		 
	   }
	   //Create 
	   
	   
	   @PUT
	   @Path("/incategory/{categoryname}/{subcategoryname}")
	   @Produces(MediaType.APPLICATION_XML)
	   public Response createSubCategory(@PathParam("categoryname") String categoryname,@PathParam("subcategoryname") String subcategoryname){
	     
		   int insertStatus = catDao.createSubCategory(categoryname,subcategoryname);
		   if(insertStatus == 0){
			   return Response.ok().entity(new GenericEntity<String>("Success") {}).build();
		   }else {
			   return Response.status(Status.EXPECTATION_FAILED).entity(new GenericEntity<String>("Failed") {}).build();
		   }
	   }
	   //create
	   @PUT
	   @Path("/incategory/{categoryname}/{subcategoryname}/{itemName}")
	   @Produces(MediaType.APPLICATION_XML)
	public Response addItemSubCategory(@PathParam("categoryname") String categoryname,
			@PathParam("subcategoryname") String subcategoryname, @PathParam("itemName") String itemName,
			@QueryParam("itemURL") String itemURL) {
		   
		   int insertStatus = catDao.insertItem(categoryname,subcategoryname,itemName,itemURL);
		   if(insertStatus == 0){
			   return Response.ok().entity(new GenericEntity<String>("Success") {}).build();
		   }else {
			   return Response.status(Status.EXPECTATION_FAILED).entity(new GenericEntity<String>("Failed") {}).build();
		   }
	   }
	   
	       //Update
	   @POST
	   @Path("/updatecategory/{categoryid}")
	   @Produces(MediaType.APPLICATION_XML)
	   public Response  updateCategory(@PathParam("categoryid") String categoryid,@QueryParam("newCategoryName") String newCategoryName){
	     
		 //  Response.ok().entity(new GenericEntity<List<CatlogVO>>(getAllCategory()) {}).build();
		   int insertStatus = catDao.updateCategory(categoryid,newCategoryName);
		    if(insertStatus == 0){
				   return Response.ok().entity(new GenericEntity<String>("Success") {}).build();
			   }else {
				   return Response.status(Status.EXPECTATION_FAILED).entity(new GenericEntity<String>("Failed") {}).build();
			   }
	   }
	   
	   @POST
	   @Path("/updatesubcategory/{subcategoryid}")
	   @Produces(MediaType.APPLICATION_XML)
	   public Response  updateSubCategory(@PathParam("subcategoryid") String subcategoryid,@QueryParam("newsubCategoryName") String newsubCategoryName){
	     
		 //  Response.ok().entity(new GenericEntity<List<CatlogVO>>(getAllCategory()) {}).build();
		   int insertStatus = catDao.updateCategory(subcategoryid,newsubCategoryName);
		    if(insertStatus == 0){
				   return Response.ok().entity(new GenericEntity<String>("Success") {}).build();
			   }else {
				   return Response.status(Status.EXPECTATION_FAILED).entity(new GenericEntity<String>("Failed") {}).build();
			   }
	   }
	   
	   
	   // UPDATE
	   @POST
	   @Path("/updateitem/{itemid}")
	   @Produces(MediaType.APPLICATION_XML)
	   public Response updateSubCategory(@PathParam("itemid") String itemID,@QueryParam("itemurl") String itemurl,@QueryParam("itemname") String itemname){
	     
		   int insertStatus = catDao.updateItem(itemID,itemurl,itemname);
		    if(insertStatus == 0){
				   return Response.ok().entity(new GenericEntity<String>("Success") {}).build();
			   }else {
				   return Response.status(Status.EXPECTATION_FAILED).entity(new GenericEntity<String>("Failed") {}).build();
			   }
	   }
	   
	 /*  @POST
	   @Path("/category/{categoryname}/{subcategoryname}/{id}")
	   @Produces(MediaType.APPLICATION_XML)
	   public List<CatlogVO> addItemSubCategory(@PathParam("categoryname") String categoryname,@PathParam("subcategoryname") String subcategoryname,@PathParam("id") String id){
	     
		 //  Response.ok().entity(new GenericEntity<List<CatlogVO>>(getAllCategory()) {}).build();
		   return getAllCategory();
	   }
	   */
	   
	   
	   //Delete 
	   @DELETE
	   @Path("/deleteCategory/{catid}")
	   @Produces(MediaType.APPLICATION_XML)
	   public Response deleteCategory(@PathParam("catid") String catid){
	     
		   int insertStatus = catDao.deleteCategory(catid);
		    if(insertStatus == 0){
				   return Response.ok().entity(new GenericEntity<String>("Success") {}).build();
			   }else {
				   return Response.status(Status.EXPECTATION_FAILED).entity(new GenericEntity<String>("Failed") {}).build();
			   }
	   }
	 
	   @DELETE
	   @Path("/deleteSubCategory/{subcatid}")
	   @Produces(MediaType.APPLICATION_XML)
	   public Response deleteSubCategory(@PathParam("subcatid") String subcatid){
	     
		   int insertStatus = catDao.deletesubCategory(subcatid);
		    if(insertStatus == 0){
				   return Response.ok().entity(new GenericEntity<String>("Success") {}).build();
			   }else {
				   return Response.status(Status.EXPECTATION_FAILED).entity(new GenericEntity<String>("Failed") {}).build();
			   }
	   }
	   
	   @DELETE
	   @Path("/deleteitem/{itemid}")
	   @Produces(MediaType.APPLICATION_XML)
	   public Response deleteItem(@PathParam("itemid") String itemid){
	     
		   int insertStatus = catDao.deleteItem(itemid);
		    if(insertStatus == 0){
				   return Response.ok().entity(new GenericEntity<String>("Success") {}).build();
			   }else {
				   return Response.status(Status.EXPECTATION_FAILED).entity(new GenericEntity<String>("Failed") {}).build();
			   }
	   }
}
