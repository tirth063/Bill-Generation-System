package com.sb.ims.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
@Service
public class AlertService {

    final
    SmsService smsService;

    private final JavaMailSender emailSender;

    @Autowired
    public AlertService(SmsService smsService, JavaMailSender emailSender) {
        this.smsService = smsService;
        this.emailSender = emailSender;
    }

    private void notifyAdminLowStock(String productName , int inventory) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("admin@gmail.com"); // Admin email
        email.setSubject("Low Stock Alert for " + productName);
        email.setText("Warning: Stock for product " + productName + " is low. Current quantity: " + inventory);

        emailSender.send(email);
        System.out.println("Admin notified for low stock of product: " + productName);
    }

    public void sendAlert(String productName , int inventory) {
        String message = String.format("Alert: The inventory for product '%s' is below the threshold. Remaining stock: %d", productName, inventory);
        smsService.sendSms("9100000000", message);
        notifyAdminLowStock(productName , inventory);
    }
}
