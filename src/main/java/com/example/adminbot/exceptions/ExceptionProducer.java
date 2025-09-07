package com.example.adminbot.exceptions;

public interface ExceptionProducer<T extends Throwable> {
    void tryExecute() throws T;
}
