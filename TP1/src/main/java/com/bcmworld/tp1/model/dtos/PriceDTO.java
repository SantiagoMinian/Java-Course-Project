package com.bcmworld.tp1.model.dtos;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Price")
public class PriceDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double price;
    private String list;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}