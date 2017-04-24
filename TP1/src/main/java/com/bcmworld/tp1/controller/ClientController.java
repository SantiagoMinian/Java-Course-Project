package com.bcmworld.tp1.controller;

import com.bcmworld.tp1.model.daos.GenericDAO;
import com.bcmworld.tp1.model.dtos.ClientDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.RegexValidator;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;
import spark.template.velocity.VelocityTemplateEngine;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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
            model.put("clients", clientDao.findAll((index - 1) * CLIENTS_PER_PAGE, CLIENTS_PER_PAGE));
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
            client.setPriceList(null);

            return new Gson().toJson(client);
        };
    }

    private Route getAddRoute() {

        return (request, response) -> {

            String body = request.body();

            String invalid = validateClientJson(body);
            if (invalid != null) {
                response.status(400);
                return invalid;
            }

            ClientDTO client = new Gson().fromJson(body, ClientDTO.class);
            clientDao.save(client);

            response.status(201);
            return "{ \"result\" : \"OK\"}";
        };
    }

    private Route getUpdateRoute() {

        return (request, response) -> {

            String body = request.body();

            String invalid = validateClientJson(body);
            if (invalid != null) {
                response.status(400);
                return invalid;
            }

            ClientDTO client = new Gson().fromJson(body, ClientDTO.class);
            clientDao.update(client);

            response.status(201);
            return "{ \"result\" : \"OK\"}";
        };
    }

    private Route getDeleteRoute() {

        return (request, response) -> {

            String body = request.body();
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> myMap = new Gson().fromJson(body, type);
            if (!new RegexValidator("^[0-9]{1,}$").isValid(myMap.get("cuitDNI"))) {
                return "{ 'result':'error', 'invalid':'cuitDNI'";
            }

            ClientDTO client = new Gson().fromJson(body, ClientDTO.class);
            if (client.getCuitDNI() != null) clientDao.delete(client.getCuitDNI());

            response.status(201);
            return "{ \"result\" : \"OK\"}";
        };
    }

    private String validateClientJson(String json) {

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> myMap = new Gson().fromJson(json, type);

        boolean error = false;
        String invalid = "{ 'result':'error', 'invalid':[";

        if (!new RegexValidator("^[0-9]{1,}$", false).isValid(myMap.get("cuitDNI"))) {
            error = true;
            invalid += "'cuitDNI', ";
        }
        if (myMap.get("name") == null || myMap.get("name").isEmpty()) {
            error = true;
            invalid += "'name', ";
        }
        if (myMap.get("surname") == null || myMap.get("surname").isEmpty()) {
            error = true;
            invalid += "'surname', ";
        }
        if (myMap.get("legalName") == null || myMap.get("legalName").isEmpty()) {
            error = true;
            invalid += "'legalName', ";
        }
        if (myMap.get("phone") == null || myMap.get("phone").isEmpty()) {
            error = true;
            invalid += "'phone', ";
        }
        if (!EmailValidator.getInstance().isValid(myMap.get("mail"))) {
            error = true;
            invalid += "'mail', ";
        }
        if (!DoubleValidator.getInstance().isValid(myMap.get("longitude"))) {
            error = true;
            invalid += "'longitude', ";
        }
        if (!DoubleValidator.getInstance().isValid(myMap.get("latitude"))) {
            error = true;
            invalid += "'latitude', ";
        }
        if (myMap.get("address") == null || myMap.get("address").isEmpty()) {
            error = true;
            invalid += "'address', ";
        }
        if (myMap.get("type") == null) {
            error = true;
            invalid += "'type', ";
        } else if (!(myMap.get("type").equalsIgnoreCase("Client")
                || myMap.get("type").equalsIgnoreCase("Company"))) {
            error = true;
            invalid += "'type', ";
        }
        invalid += "]}";

        if (error) return invalid;
        else return null;
    }
}