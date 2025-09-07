package com.example.adminbot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table")
@Entity
public class User {
    @Id
    Long chatId;

    String firstName;
    String lastName;
    String username;
    String languageCode;
    Boolean isBot;
    Long userId;
    Boolean isPremium;

    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate = new Date();

    public User(Long chatId, String firstName, String lastName, String username, String languageCode, Boolean isBot, Long userId, Boolean isPremium) {
        this.chatId = chatId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.languageCode = languageCode;
        this.isBot = isBot;
        this.userId = userId;
        this.isPremium = isPremium;
    }

    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", isBot=" + isBot +
                ", userId=" + userId +
                ", isPremium=" + isPremium +
                ", createdDate=" + createdDate +
                '}';
    }
}
