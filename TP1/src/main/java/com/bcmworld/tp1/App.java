package com.bcmworld.tp1;

import com.bcmworld.tp1.controller.ClientController;
import com.bcmworld.tp1.model.daos.GenericDAO;
import com.bcmworld.tp1.model.dtos.ClientDTO;
import com.bcmworld.tp1.model.dtos.PriceDTO;
import com.bcmworld.tp1.model.dtos.ProductDTO;
import com.bcmworld.tp1.payment.CardStrategy;
import com.bcmworld.tp1.payment.CompositePaymentMethod;
import com.bcmworld.tp1.telegram.SaclierBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

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

        PriceDTO price = new PriceDTO();
        price.setPrice(123);


        ProductDTO prod = new ProductDTO();
        prod.setName("hola");
        prod.setOnSale(true);
        prod.setSalePercentage(0.8);
        prod.setCode("123");
        prod.setImage("asd");
        prod.setStock(10);
        prod.setSubtype("tort");
        prod.setType("a");
        prod.setCost(10.0);

        ClientDTO clien = new ClientDTO();
        clien.setType("Client");
        clien.setAddress("1234");
        clien.setLatitude(-34.5730052);
        clien.setLongitude(-58.5042918);
        clien.setPhone("+54");
        clien.setLegalName("cljo");
        clien.setSurname("jo");
        clien.setMail("a@2.c");

        GenericDAO<ClientDTO, String> clientDAO = new GenericDAO<>(ClientDTO.class);
        GenericDAO<ProductDTO, Long> productDAO = new GenericDAO<>(ProductDTO.class);
        for (Integer i = 10; i < 50; i++) {
            clien.setCuitDNI(i.toString());
            clien.setName("client" + i);
            clientDAO.save(clien);
        }

        clien.setCuitDNI("5");
        clien.setTelegramId(259628094L);
        clien.setName("Santiago");
        clientDAO.save(clien);
        productDAO.save(prod);

        prod = productDAO.findById(1L);
        clien = clientDAO.findById("5");

        prod.addObserver(clien);
        //clien.addInterest(prod);

        productDAO.update(prod);
        //clientDAO.update(clien);

        ClientController clientController = new ClientController();

        payment.charge();

        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(SaclierBot.getInstance());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        productDAO.findById(1L).setSale(0.8);
    }
}