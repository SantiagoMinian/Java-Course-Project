package com.bcmworld.tp1.model.dtos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Product")
public class ProductDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;

    private String name;
    private String type;
    private String subtype;

    private String image;

    private int stock;
    private double cost;

    private boolean onSale = false;
    private double salePercentage = 1;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product")
    private List<PriceDTO> prices = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="Client_Product",
            joinColumns={@JoinColumn(name="id")},
            inverseJoinColumns={@JoinColumn(name="cuitDNI")})
    private List<ClientDTO> observers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<PriceDTO> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceDTO> prices) {
        this.prices = prices;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public double getSalePercentage() {
        return salePercentage;
    }

    public void setSalePercentage(double salePercentage) {
        this.salePercentage = salePercentage;
    }

    public double getCurrentPrice(String list) {

        List<PriceDTO> prices = getPriceHistory(list);
        PriceDTO newest = null;
        for (PriceDTO price : prices) {

            if (newest == null) newest = price;
            if (price.getDate().after(newest.getDate())) newest = price;
        }
        return newest.getPrice();
    }

    public List<PriceDTO> getPriceHistory(String list) {
        return prices.stream()
                .filter(price -> price.getList().equals(list))
                .collect(Collectors.toList());
    }

    public List<ClientDTO> getObservers() {
        return observers;
    }

    public void setObservers(List<ClientDTO> observers) {
        this.observers = observers;
    }

    public void addObserver(ClientDTO observer) {
        observers.add(observer);
    }

    public void removeObserver(ClientDTO observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        if (!onSale) return;
        for (ClientDTO observer : observers) observer.update(this);
    }

    public void setSale(double salePercentage) {
        setOnSale(true);
        setSalePercentage(salePercentage);
        notifyObservers();
    }

    public void finishSale() {
        setOnSale(false);
        setSalePercentage(1.0);
    }
}
