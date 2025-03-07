package com.sb.ims.services;

import com.sb.ims.Const.Status;
import com.sb.ims.dto.BillDto;
import com.sb.ims.dto.OrderDto;
import com.sb.ims.dto.ProductDto;
import com.sb.ims.model.Customer;
import com.sb.ims.model.Order;
import com.sb.ims.model.OrderItems;
import com.sb.ims.model.Product;
import com.sb.ims.repository.CustomerRepository;
import com.sb.ims.repository.OrderRepo;
import com.sb.ims.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service

public class OrderService {

    private final OrderRepo orderRepo;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final Random random = new Random();
    private final SmsService smsService;
    private final AlertService alertService;

    @Autowired
    public OrderService(OrderRepo orderRepo , CustomerRepository CustomerRepository, ProductRepository ProductRepository
    , SmsService smsService , AlertService alertService) {
        this.orderRepo = orderRepo;
        this.customerRepository = CustomerRepository;
        this.productRepository = ProductRepository;
        this.smsService = smsService;
        this.alertService = alertService;
    }

    @Transactional
    public ResponseEntity<?> save(OrderDto orderDto) {
        try {
            Order order = orderDto.convertToOrder();
            // Initialize bill DTO
            BillDto billDto = new BillDto();
            billDto.setBillId(order.getId());

            Customer customer = customerRepository.findById(order.getCustomerId()).orElse(null);
            if (customer == null) {
                return handlePaymentFailure("Customer not found.");
            }
            billDto.setCustomer(customer);

            // Initialize the productDtoList to avoid null pointer exception
            billDto.setProductDtoList(new ArrayList<>());

            double totalPrice = 0;
            for (OrderItems p : order.getProductsData()) {
                Product product = productRepository.findById(p.getProductId()).orElse(null);
                if (product == null) {
                    return handlePaymentFailure("Product not found.");
                }

                ProductDto productDto = new ProductDto();
                productDto.setProductname(product.getName());
                productDto.setQuantity(p.getQuantity());
                productDto.setGst(product.getGst());
                productDto.setOriginalprice(product.getPrice());

                // Calculate final price with GST
                double finalPrice = product.getPrice() + (product.getPrice() * product.getGst() / 100);
                productDto.setFinalPrice(finalPrice);
                totalPrice += finalPrice * p.getQuantity();

                // Reduce stock quantity
                int updatedStock = product.getStockQuantity() - p.getQuantity();
                if (updatedStock < 0) {
                    return handlePaymentFailure("Insufficient stock for product: " + product.getName());
                }
                product.setStockQuantity(updatedStock);

                // Check if stock is below the threshold
                if (updatedStock < 10) {
                    alertService.sendAlert(product.getName(), updatedStock);
                }

                // Add the product to the bill
                billDto.getProductDtoList().add(productDto);
            }

            // Set the total amount and payment status
            billDto.setAmount(totalPrice);
            //here change it according to need
            Status paymentStatus = simulatePaymentStatus();
            billDto.setBillStatus(paymentStatus);
            billDto.setDate(LocalDate.now());

            if (paymentStatus == Status.FAIL) {
                return handlePaymentFailure("Payment failed. Please try again.");
            }

            // Save the order and associated data only if payment is successful
            orderRepo.save(order);

            // Return success response
            return new ResponseEntity<>(billDto, HttpStatus.OK);

        } catch (Exception e) {
            return handlePaymentFailure("An unexpected error occurred: " + e.getMessage());
        }
    }

    private ResponseEntity<?> handlePaymentFailure(String message) {
        // Logic to send SMS or notification to the customer/admin
        smsService.sendSms("91", message); 
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private Status simulatePaymentStatus() {
        int paymentOutcome = random.nextInt(10); // Generates a number between 0 and 9
        return (paymentOutcome < 3) ? Status.FAIL : Status.SUCCESS;
    }

    public List<Order> saveAllOrders(List<Order> orders) {
        return orderRepo.saveAll(orders);
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public Order findById(long id) {
        return orderRepo.findById(id).orElse(new Order());
    }
}
