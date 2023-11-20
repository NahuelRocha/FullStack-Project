package com.rocha.fullstack.controller;

import com.rocha.fullstack.models.Request;
import com.rocha.fullstack.models.Response;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentIntentController {

    @PostMapping("/create-payment-intent")
    public Response createPaymentIntent(@RequestBody Request request) throws StripeException {

        var params = PaymentIntentCreateParams.builder()
                .setAmount(request.getAmount() * 100L)
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams
                                .AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .build()
                ).build();

        var intent = PaymentIntent.create(params);

        return new Response(intent.getId(),intent.getClientSecret());
    }
}
