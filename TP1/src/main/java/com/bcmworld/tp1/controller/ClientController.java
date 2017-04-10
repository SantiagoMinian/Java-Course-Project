package com.bcmworld.tp1.controller;

import com.bcmworld.tp1.model.daos.GenericDAO;
import com.bcmworld.tp1.model.dtos.ClientDTO;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class ClientController {

    private GenericDAO<ClientDTO, String> clientDao;

    public ClientController() {
        clientDao = new GenericDAO<>(ClientDTO.class);
        get("/clients", getClientsRoute(), new VelocityTemplateEngine());
        get("/clients/:id", getClientRoute(), new VelocityTemplateEngine());
        post("/clients/add", getAddRoute());
        put("/clients/update", getUpdateRoute());
        delete("/clients/remove", getDeleteRoute());
    }

    private TemplateViewRoute getClientsRoute() {
        return (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("clients", clientDao.findAll());
            return new ModelAndView(model, "public/clients.vm");
        };
    }

    private TemplateViewRoute getClientRoute() {
        return (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String id = request.params(":id");
            model.put("client", clientDao.findById(id));
            return new ModelAndView(model, "public/client.vm");
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