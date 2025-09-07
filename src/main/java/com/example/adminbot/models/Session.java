package com.example.adminbot.models;

import com.example.adminbot.models.forms.Form;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    Map<String, Object> params = new ConcurrentHashMap<>();
    Form currentForm;

}
