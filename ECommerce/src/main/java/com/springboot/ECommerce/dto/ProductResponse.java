package com.springboot.ECommerce.dto;



import com.springboot.ECommerce.model.Seller;
import java.util.List;

public class ProductResponse {

    private int id;
    private String title;
    private String description;
    private int mrpPrice;
    private int sellingPrice;
    private String imageUrl;
    private String color;
    private Seller seller;
    private List<String> categoryNames;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMrpPrice() {
		return mrpPrice;
	}
	public void setMrpPrice(int mrpPrice) {
		this.mrpPrice = mrpPrice;
	}
	public int getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public List<String> getCategoryNames() {
		return categoryNames;
	}
	public void setCategoryNames(List<String> categoryNames) {
		this.categoryNames = categoryNames;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	



}

