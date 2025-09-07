package com.example.adminbot.models;

import com.example.adminbot.bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Route {

    public String getRoute();
    public void process(Update update);
    public void setTelegramBot(TelegramBot telegramBot);

}
