package com.bcmworld.tp1.payment;

public interface PaymentMethodStrategy {

    void charge(double amount);
}