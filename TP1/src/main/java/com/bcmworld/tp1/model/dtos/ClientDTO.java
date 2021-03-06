package com.bcmworld.tp1.model.dtos;

import com.bcmworld.tp1.telegram.SaclierBot;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Client")
public class ClientDTO {

    @Id
    private String cuitDNI;

    private String name;
    private String surname;
    private String legalName;

    private String phone;
    private String mail;
    private Long telegramId = null;

    private double longitude;
    private double latitude;
    private String address;

    private String priceList;

    private String type;

    private boolean deleted = false;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "client")
    private List<ContactDTO> contacts = new ArrayList<>();

    public void update(ProductDTO product) {
        String sale = "Hello " + name + "! " + product.getName() + "s are now "
                + (100 - (product.getSalePercentage() * 100.0)) + "% off on our website";
        if(telegramId != null) SaclierBot.getInstance().sendMessage(sale, telegramId);
    }

    public List<Object> toObjectList() {
        List<Object> attributes = new ArrayList<>();

        attributes.add(cuitDNI);
        attributes.add(name);
        attributes.add(surname);
        attributes.add(legalName);
        attributes.add(phone);
        attributes.add(mail);
        attributes.add(longitude);
        attributes.add(latitude);
        attributes.add(address);
        attributes.add(type);

        return attributes;
    }

    public String getCuitDNI() {
        return cuitDNI;
    }

    public void setCuitDNI(String cuitDNI) {
        this.cuitDNI = cuitDNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    public void addContact(ContactDTO contact) {
        contacts.add(contact);
    }

    public void deleteContact(ContactDTO contact) {
        contacts.remove(contact);
    }

    public String getPriceList() {
        return priceList;
    }

    public void setPriceList(String priceList) {
        this.priceList = priceList;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }
}