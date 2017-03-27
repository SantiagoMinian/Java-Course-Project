package com.bcmworld.tp1;

import com.bcmworld.tp1.model.daos.ClientDAO;
import com.bcmworld.tp1.model.dtos.ClientDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static spark.Spark.get;

public class App {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello Rochi");
        get("/saclier", (req, res) -> "Lucas");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("HibernatePersistence");
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.getTransaction().commit();
        manager.close();
        factory.close();

        ClientDTO client = new ClientDTO();
        client.setCuitDNI("1");
        client.setName("colo");
        ClientDTO client2 = new ClientDTO();
        client2.setCuitDNI("2");
        client2.setName("cragno");
        ClientDTO client3 = new ClientDTO();
        client3.setCuitDNI("3");
        client3.setName("rochi");
        ClientDTO client4 = new ClientDTO();
        client4.setCuitDNI("4");
        client4.setName("pelo");

        ClientDAO cl = new ClientDAO();
        cl.save(client);
        cl.save(client2);
        cl.save(client3);
        cl.save(client4);

        client = cl.findById(ClientDTO.class, "1");
        System.out.println(client.getName());

        client2 = cl.findById(ClientDTO.class, "2");
        System.out.println(client2.getName());

        client3 = cl.findById(ClientDTO.class, "3");
        System.out.println(client3.getName());

        client4 = cl.findById(ClientDTO.class, "4");
        System.out.println(client4.getName());

        cl.delete(ClientDTO.class, "3");

        cl.findAll(ClientDTO.class).forEach(e -> System.out.println(e.isDeleted()));

        cl.close();
    }
}