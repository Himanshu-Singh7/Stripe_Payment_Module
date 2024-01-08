package com.payment.example.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${stripe.api.secretKey}")
    private String stripApiKey;

    public String createPaymentIntent(double amount) {
        Stripe.apiKey = stripApiKey;

        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setAmount((long) (amount * 100))
                .setCurrency("USD")
                .build();

        try {
            PaymentIntent intent = PaymentIntent.create(createParams);
            return intent.getId();
        }catch (StripeException e){
            throw new RuntimeException("Error creating payment intent" , e);
        }
     }

//    public boolean confirmPayment(String paymentIntentId) throws StripeException {
//        Stripe.apiKey = stripApiKey;
//
//        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
//        intent.confirm();
//
//        return "succeeded".equals(intent.getStatus());
//    }
}
