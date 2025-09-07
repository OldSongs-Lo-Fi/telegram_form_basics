package com.example.adminbot.bot;

import com.example.adminbot.exceptions.ExceptionProducer;
import com.example.adminbot.models.Route;
import com.example.adminbot.models.Session;
import com.example.adminbot.models.forms.Form;

import com.example.adminbot.services.SessionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Service
public class TelegramBot extends TelegramLongPollingBot {

    private final String telegramBotUsername;
    private final SessionService sessionService;

    private final Map<String, Consumer<Update>> routes = new ConcurrentHashMap<>();


    public TelegramBot(
            @Value("${telegram.bot.token}") String telegramBotToken,
            @Value("${telegram.bot.username}") String telegramBotUsername,
            SessionService sessionService,
            List<Route> routeProcessors) {
        super(telegramBotToken);
        this.telegramBotUsername = telegramBotUsername;
        this.sessionService = sessionService;

        for (var route : routeProcessors){
            route.setTelegramBot(this);
            routes.put(route.getRoute(), route::process);
        }

    }


    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            System.out.println("✅ Telegram bot registered successfully");
        } catch (Exception e) {
            System.err.println("❌ Error registering bot: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){

            Long chatId = update.getMessage().getChatId();

            if (!sessionService.hasCurrentSession(chatId)){
                sessionService.createNewSession(chatId);
                System.out.println("NEW SESSION CREATED!");
            }

            Session session = sessionService.getCurrentSession(chatId);

            if (session.getCurrentForm() != null){
                Form currentForm = session.getCurrentForm();
                currentForm.processAnswer(update);
                System.out.println("FORM WAS ALREADY TAKEN!");
            }

            if (update.getMessage().hasText()){
                String path = update.getMessage().getText().split(" ")[0];

                if (routes.containsKey(path)){
                    // ROUTES PROCESSING
                    routes.get(path).accept(update);
                    System.out.println("ROUTE WAS CHECKED!");
                }

                if (session.getCurrentForm() != null){
                    // FORM INIT PROCESSING
                    Form currentForm = session.getCurrentForm();
                    currentForm.onInit(update);
                    currentForm.askCurrentQuestion();
                    System.out.println("FORM NEW USED!");
                }


            }
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotUsername;
    }

    public static void sendMessage(ExceptionProducer<Exception> producer, int count, int timeout){

        if (count <= 0) throw new IllegalArgumentException("Count cannot be lower than 0");
        if (timeout <= 0) throw new IllegalArgumentException("Timeout cannot be lower than 0");

        Thread thread = new Thread(
                () -> {
                    int _timeout = timeout;
                    for (int i = 0; i < count; i++){
                        try {
                            producer.tryExecute();
                            break;
                        } catch (Exception e){
                            System.out.println("Error: " + e.getMessage());
                            try {
                                _timeout = Math.min(_timeout * 2, 10000);
                                Thread.sleep(_timeout);
                            } catch (InterruptedException ex) {
                                System.out.println("Can't hold timeout");
                            }
                        }
                    }

                    System.out.println("Message was successfully delivered!");
                }
        );

        thread.start();

    }



}

