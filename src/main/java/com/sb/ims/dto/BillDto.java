package com.sb.ims.dto;

import com.sb.ims.Const.Status;
import com.sb.ims.model.Customer;

import java.time.LocalDate;
import java.util.List;

public class BillDto {

    private long billId;

    private Customer customer;

    private LocalDate date;

    private List<ProductDto> productDtoList;

    private Status billStatus;

    private double amount;

    public BillDto() {
    }

    public BillDto(long billId, Customer customer, LocalDate date, List<ProductDto> productDtoList, Status billStatus , double amount) {
        this.billId = billId;
        this.customer = customer;
        this.date = date;
        this.productDtoList = productDtoList;
        this.billStatus = billStatus;
        this.amount = amount;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<ProductDto> getProductDtoList() {
        return productDtoList;
    }

    public void setProductDtoList(List<ProductDto> productDtoList) {
        this.productDtoList = productDtoList;
    }

    public Status getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Status billStatus) {
        this.billStatus = billStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
