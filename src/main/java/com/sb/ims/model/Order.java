package com.sb.ims.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "order_tbl")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private long id;

    private long customerId;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "order")
    @JsonManagedReference
    private List<OrderItems> productsData;

    private LocalDate orderDate;

    public Order() {
        this.orderDate = LocalDate.now();
    }

    public Order(long id, long customerId, List<OrderItems> productsData ) {
        this.id = id;
        this.customerId = customerId;
        this.productsData = productsData;
        this.orderDate = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate() {
        this.orderDate = LocalDate.now();
    }
}
