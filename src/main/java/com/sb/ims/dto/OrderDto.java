package com.sb.ims.dto;

import com.sb.ims.model.Order;
import com.sb.ims.model.OrderItems;

import java.util.List;

public class OrderDto {

    private long customerId;

    private List<OrderItems> productsData;

    public OrderDto (){}

    public OrderDto(long customerId, List<OrderItems> productsData) {
        this.customerId = customerId;
        this.productsData = productsData;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<OrderItems> getProductsData() {
        return productsData;
    }

    public void setProductsData(List<OrderItems> productsData) {
        this.productsData = productsData;
    }

    public Order convertToOrder(){
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setProductsData(productsData);
        return order;
    }
}
