package com.bcmworld.tp1.model.dtos;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<PriceListDTO> priceLists;

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

    public List<PriceListDTO> getPriceLists() {
        return priceLists;
    }

    public void setPriceLists(List<PriceListDTO> priceLists) {
        this.priceLists = priceLists;
    }
}
