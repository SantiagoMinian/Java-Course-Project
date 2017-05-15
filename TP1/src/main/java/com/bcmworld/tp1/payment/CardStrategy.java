package com.bcmworld.tp1.payment;

import java.util.Date;

public class CardStrategy implements  PaymentMethodStrategy{

    public enum Type {
        CREDIT, DEBIT
    };

    private String number;
    private Date expirationDate;
    private int cvc;
    private String cardHolder;
    private Type type;

    @Override
    public void charge(double amount) {
        System.out.println("Charged: " + amount);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
