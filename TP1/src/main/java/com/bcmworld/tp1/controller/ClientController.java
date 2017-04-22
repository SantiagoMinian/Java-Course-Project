package com.bcmworld.tp1.controller;

import com.bcmworld.tp1.model.daos.GenericDAO;
import com.bcmworld.tp1.model.dtos.ClientDTO;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class ClientController {

    private GenericDAO<ClientDTO, String> clientDao;
    private static final int CLIENTS_PER_PAGE = 10;

    public ClientController() {
        clientDao = new GenericDAO<>(ClientDTO.class);
        get("/clients/:index", getClientsRoute(), new VelocityTemplateEngine());
        get("/client/:id", getClientRoute(), new VelocityTemplateEngine());
        get("/client/:id/json", getClientJsonRoute());
        post("/clients/add", getAddRoute());
        put("/clients/update", getUpdateRoute());
        delete("/clients/remove", getDeleteRoute());
    }

    private TemplateViewRoute getClientsRoute() {
        return (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int index = Integer.valueOf(request.params(":index"));
//            clientDao.findAll(index * CLIENTS_PER_PAGE, CLIENTS_PER_PAGE).forEach(clientDTO -> System.out.println(clientDTO.getName()));
            model.put("clients", clientDao.findAll((index-1) * CLIENTS_PER_PAGE, CLIENTS_PER_PAGE));
            model.put("index", index);
            model.put("pages", Math.floor(clientDao.countAll() / CLIENTS_PER_PAGE) + 1);
            return new ModelAndView(model, "public/velocity/clients.vm");
        };
    }

    private TemplateViewRoute getClientRoute() {
        return (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String id = request.params(":id");
            ClientDTO client = clientDao.findById(id);
            model.put("client", client);
            model.put("contacts", client.getContacts());
            return new ModelAndView(model, "public/velocity/client.vm");
        };
    }

    private Route getClientJsonRoute() {
        return (request, response) -> {
            String id = request.params(":id");
            ClientDTO client = clientDao.findById(id);
            client.setContacts(null);

            return new Gson().toJson(client);
        };
    }

    private Route getAddRoute() {

        return (request, response) -> {

            // TODO: validation and 400 status code in case of error
            //System.out.println(client.getName() == null);

            String body = request.body();
            ClientDTO client = new Gson().fromJson(body, ClientDTO.class);

            clientDao.save(client);

            response.status(201);

            return "{ \"result\" : \"OK\"}";
        };
    }

    private Route getUpdateRoute() {

        return (request, response) -> {

            // TODO: validation and 400 status code in case of error
            //System.out.println(client.getName() == null);

            String body = request.body();
            ClientDTO client = new Gson().fromJson(body, ClientDTO.class);

            clientDao.update(client);

            response.status(201);

            return "{ \"result\" : \"OK\"}";
        };
    }

    private Route getDeleteRoute() {

        return (request, response) -> {

            // TODO: validation and 400

            String body = request.body();
            ClientDTO client = new Gson().fromJson(body, ClientDTO.class);

            if (client.getCuitDNI() != null) clientDao.delete(client.getCuitDNI());

            return "{ \"result\" : \"OK\"}";
        };
    }
}