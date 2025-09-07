package com.example.adminbot.models.forms;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public abstract class Question {

    public abstract void askQuestion();
    public abstract void processAnswer(Update update, Map<String, Object> params) throws Exception;
    public abstract void processError(Exception e);

}
