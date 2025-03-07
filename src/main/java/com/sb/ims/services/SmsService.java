package com.sb.ims.services;

import com.sb.ims.Configration.TwilioConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public final TwilioConfiguration configuration;

    public SmsService(TwilioConfiguration configuration) {
        this.configuration = configuration;
    }

    public void sendSms(String customerNumber, String message) {
        String customerPhoneNumber = "+" + customerNumber;

        Message.creator(
                new PhoneNumber(customerPhoneNumber),
                new PhoneNumber(configuration.getTrialNumber()),
                message
        ).create();

        System.out.println("sms sent to customer : " + customerPhoneNumber + " with message " + message);

    }
}
