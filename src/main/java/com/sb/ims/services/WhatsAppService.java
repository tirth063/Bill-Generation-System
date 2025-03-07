package com.sb.ims.services;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {
    
    @Value("${twilio.whatsapp.from}")
    private String whatsappFrom;
    
    @Value("${twilio.whatsapp.to}")
    private String whatsappTo;
    
    public void sendWhatsAppMessage(String customerNumber, String message) {
        String customerPhoneNumber = "+" + customerNumber;
        
        Message.creator(
                new PhoneNumber("whatsapp:" + whatsappTo),
                new PhoneNumber("whatsapp:" + whatsappFrom),
                message
        ).create();
        
        System.out.println("WhatsApp message sent to customer ID: " + customerPhoneNumber + " with message: " + message);
    }
}