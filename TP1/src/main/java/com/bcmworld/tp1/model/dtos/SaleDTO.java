package com.bcmworld.tp1.model.dtos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sale")
public class SaleDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double total;
    private double iva;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private SellerDTO seller;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "sale")
    private List<ProductDetailDTO> products = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }

    public List<ProductDetailDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetailDTO> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public void addProductDetail(ProductDetailDTO productDetail) {
        products.add(productDetail);
    }
}
