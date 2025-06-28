package com.springboot.ECommerce.dto;


//Shows what is selling more
public class WeeklyTopProductResponse {
    private String productTitle;
    private long totalQuantitySold;

    public WeeklyTopProductResponse(String productTitle, long totalQuantitySold) {
        this.productTitle = productTitle;
        this.totalQuantitySold = totalQuantitySold;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public void setTotalQuantitySold(long totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }
}
