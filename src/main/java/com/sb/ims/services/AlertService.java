package com.sb.ims.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class AlertService {
    
    final SmsService smsService;
    
    private final JavaMailSender emailSender;
    
    @Value("${admin.email}")
    private String adminEmail;

    @Value("${twilio.whatsapp.to}")
    private String whatsappTo;

    public AlertService(SmsService smsService, JavaMailSender emailSender) {
        this.smsService = smsService;
        this.emailSender = emailSender;
    }
    
    private void notifyAdminLowStock(String productName, int inventory) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(adminEmail); // Using property placeholder
        email.setSubject("Low Stock Alert for " + productName);
        email.setText("Warning: Stock for product " + productName + " is low. Current quantity: " + inventory);
        
        emailSender.send(email);
        System.out.println("Admin notified for low stock of product: " + productName);
    }
    
    public void sendAlert(String productName, int inventory) {
        String message = String.format("Alert: The inventory for product '%s' is below the threshold. Remaining stock: %d", productName, inventory);
        smsService.sendSms(whatsappTo, message);
        notifyAdminLowStock(productName, inventory);
    }
}