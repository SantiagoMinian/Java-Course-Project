package com.bcmworld.tp1.telegram;

import com.bcmworld.tp1.model.daos.GenericDAO;
import com.bcmworld.tp1.model.dtos.ClientDTO;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class SaclierBot extends TelegramLongPollingBot {

    private static SaclierBot instance = null;

    public static final String USERNAME = "SaclierBot";
    public static final String TOKEN = "345477099:AAFltu-evFktfHH0pC1H_93d_Wf5jTNLiGQ";

    public static SaclierBot getInstance() {
        if (instance == null) instance = new SaclierBot();
        return instance;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            SendMessage message = response(update);
            try {
                sendMessage(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private SendMessage response(Update update) {

        SendMessage message = new SendMessage().setChatId(update.getMessage().getChatId());
        String receivedMessage = update.getMessage().getText();

        if (receivedMessage.equals("/start")) {
            message.setText(update.getMessage().getChatId() + "Welcome to SaclierBot! In order to get started you need to enter your Cuit or DNI " +
                    "with the following format: CuitDNI:yourcuitdni");
        } else if (receivedMessage.startsWith("CuitDNI:")) {
            GenericDAO<ClientDTO, String> clientDAO = new GenericDAO<>(ClientDTO.class);
            ClientDTO client = clientDAO.findById(receivedMessage.substring(8));
            if (client == null)
                message.setText("I couldn't find a Client with that Cuit/DNI, register on our webpage or enter the correct Cuit/DNI");
            else {
                client.setTelegramId(update.getMessage().getChatId());
                clientDAO.update(client);
                message.setText("Hello " + client.getName() + "! From now on I'll let you know when one of the products you are interested in is on SALE");
            }
        }
        return message;

    }

    public void sendMessage(String text, Long chatId) {
        try {
            sendMessage(new SendMessage().setChatId(chatId).setText(text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }
}
