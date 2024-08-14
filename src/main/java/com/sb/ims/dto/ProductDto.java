package com.sb.ims.dto;

public class ProductDto {
    private String productname;
    private int quantity;
    private double originalprice;
    private double gst;
    private double finalPrice;
    public ProductDto() {}

    public ProductDto(int quantity, String productname, double originalprice, double gst, double finalPrice) {
        this.quantity = quantity;
        this.productname = productname;
        this.originalprice = originalprice;
        this.gst = gst;
        this.finalPrice = finalPrice;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(double originalprice) {
        this.originalprice = originalprice;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
