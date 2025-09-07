package com.example.adminbot.routes;

import com.example.adminbot.bot.TelegramBot;
import com.example.adminbot.entities.User;
import com.example.adminbot.models.Route;
import com.example.adminbot.repos.UserRepo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartRoute implements Route {

    private final UserRepo userRepo;
    private TelegramBot telegramBot;

    public StartRoute(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public String getRoute() {
        return "/start";
    }

    @Override
    public void process(Update update) {
        Long chatId = update.getMessage().getChatId();

        // Информация о пользователе, отправившем сообщение
        String firstName = update.getMessage().getFrom().getFirstName();
        String lastName = update.getMessage().getFrom().getLastName();
        String username = update.getMessage().getFrom().getUserName();
        String languageCode = update.getMessage().getFrom().getLanguageCode();
        Boolean isBot = update.getMessage().getFrom().getIsBot();
        Long userId = update.getMessage().getFrom().getId();
        Boolean isPremium = update.getMessage().getFrom().getIsPremium();

        User user = new User(
                chatId,
                firstName,
                lastName,
                username,
                languageCode,
                isBot,
                userId,
                isPremium
        );

        userRepo.save(user);

        TelegramBot.sendMessage(() -> {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Hello world!");
                    telegramBot.execute(sendMessage);
                }, 3, 3000);


    }

    @Override
    public void setTelegramBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
}
