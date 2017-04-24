package com.bcmworld.tp1;

import com.bcmworld.tp1.controller.ClientController;
import com.bcmworld.tp1.model.daos.GenericDAO;
import com.bcmworld.tp1.model.dtos.*;
import spark.ModelAndView;
import spark.TemplateViewRoute;
import spark.template.velocity.VelocityTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

        ProductDTO prod = new ProductDTO();
        prod.setName("hola");

        PriceDTO price = new PriceDTO();
        price.setPrice(123);

        PriceListDTO pl = new PriceListDTO();
        pl.setProduct(prod);
        pl.setPrice(price);

        GenericDAO<PriceListDTO, Long> priceListDAO = new GenericDAO<>(PriceListDTO.class);

        priceListDAO.save(pl);

        ContactDTO contactP = new ContactDTO();
        contactP.setName("papaColo");
        ContactDTO contactM = new ContactDTO();
        contactM.setName("mamaColo");

        ContactDTO contactPCR = new ContactDTO();
        contactPCR.setName("papaCragno");
        ContactDTO contactMCR = new ContactDTO();
        contactMCR.setName("mamaCragno");

        ClientDTO client = new ClientDTO();
        client.setCuitDNI("1");
        client.setName("colo");
        contactP.setClient(client);
        contactM.setClient(client);
        client.addContact(contactM);
        client.addContact(contactP);
        ClientDTO client2 = new ClientDTO();
        client2.setCuitDNI("2");
        client2.setName("cragno");
        contactPCR.setClient(client2);
        contactMCR.setClient(client2);
        client2.addContact(contactMCR);
        client2.addContact(contactPCR);
        ClientDTO client3 = new ClientDTO();
        client3.setCuitDNI("3");
        client3.setName("rochi");
        ClientDTO client4 = new ClientDTO();
        client4.setCuitDNI("4");
        client4.setName("pelo");

        GenericDAO<ClientDTO, String> cl = new GenericDAO<>(ClientDTO.class);
        cl.save(client);
        cl.save(client2);
        cl.save(client3);
        cl.save(client4);

        client = cl.findById("1");
        System.out.println(client.getName());

        client2 = cl.findById("2");
        System.out.println(client2.getName());

        client3 = cl.findById("3");
        System.out.println(client3.getName());

        client4 = cl.findById("4");
        System.out.println(client4.getName());

        cl.delete("3");

        System.out.println(cl.countAll());

        cl.findById("2").getContacts().forEach(e -> System.out.println(e.getName()));
        cl.findById("1").getContacts().forEach(e -> System.out.println(e.getName()));

        cl.delete("2");

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
            cl.save(clien);
        }

        ClientController clientController = new ClientController();
    }
}