package com.example.adminbot.models.forms;

import com.example.adminbot.bot.TelegramBot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@RequiredArgsConstructor
public abstract class Form {

    private int currentQuestionId = 0;
    private final Map<String, Object> params = new ConcurrentHashMap<>();
    private final List<Question> questions;
    private final TelegramBot bot;

    public abstract void onInit(Update update);

    public abstract void onComplete(Update update);

    public void processAnswer(Update update){
        var question = questions.get(currentQuestionId);

        try {
            question.processAnswer(update, params);
            currentQuestionId++;
        } catch (Exception e){
            question.processError(e);
        }

        if (currentQuestionId >= questions.size()) {
            onComplete(update);
        }

    }

    public void askCurrentQuestion() {
        var question = questions.get(currentQuestionId);
        question.askQuestion();
    }
}