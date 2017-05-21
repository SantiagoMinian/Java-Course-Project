package com.bcmworld.tp1.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompositePaymentMethod {

    private Map<PaymentMethodStrategy, Double> paymentMethods = new HashMap<>();

    public void charge() {
        paymentMethods.forEach(PaymentMethodStrategy::charge);
    }

    public void add(PaymentMethodStrategy paymentMethod, Double amount) {
        paymentMethods.put(paymentMethod, amount);
    }

    public void remove(PaymentMethodStrategy paymentMethod) {
        paymentMethods.remove(paymentMethod);
    }
}
