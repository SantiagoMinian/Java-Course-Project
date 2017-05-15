package com.bcmworld.tp1;

import com.bcmworld.tp1.controller.ClientController;
import com.bcmworld.tp1.model.daos.GenericDAO;
import com.bcmworld.tp1.model.dtos.*;
import com.bcmworld.tp1.payment.CardStrategy;
import com.bcmworld.tp1.payment.CompositePaymentMethod;
import spark.ModelAndView;
import spark.TemplateViewRoute;
import spark.template.velocity.VelocityTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

public class App {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("HibernatePersistence");
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.getTransaction().commit();
        manager.close();
        factory.close();

        CardStrategy debit = new CardStrategy();
        debit.setCardHolder("CRAGLO");
        debit.setCvc(123);
        debit.setExpirationDate(new Date());
        debit.setNumber("12345678");
        debit.setType(CardStrategy.Type.DEBIT);
        CardStrategy credit = new CardStrategy();
        debit.setCardHolder("CRAGLO");
        debit.setCvc(321);
        debit.setExpirationDate(new Date());
        debit.setNumber("12348765");
        debit.setType(CardStrategy.Type.CREDIT);

        CompositePaymentMethod payment = new CompositePaymentMethod();
        payment.add(debit, 199.5);
        payment.add(credit, 0.5);

        ProductDTO prod = new ProductDTO();
        prod.setName("hola");

        PriceDTO price = new PriceDTO();
        price.setPrice(123);

        PriceListDTO pl = new PriceListDTO();
        pl.setProduct(prod);
        pl.setPrice(price);

        GenericDAO<PriceListDTO, Long> priceListDAO = new GenericDAO<>(PriceListDTO.class);

        priceListDAO.save(pl);

        GenericDAO<ClientDTO, String> clientDAO = new GenericDAO<>(ClientDTO.class);

        ClientDTO clien = new ClientDTO();
        clien.setType("Client");
        clien.setAddress("1234");
        clien.setLatitude(-34.5730052);
        clien.setLongitude(-58.5042918);
        clien.setPhone("+54");
        clien.setLegalName("cljo");
        clien.setSurname("jo");
        clien.setMail("a@2.c");
        clien.setPriceList(pl);

        for(Integer i = 10; i < 50; i++) {
            clien.setCuitDNI(i.toString());
            clien.setName("client" + i);
            clientDAO.save(clien);
        }

        ClientController clientController = new ClientController();

        payment.charge();
    }
}