package com.example.UserManagement.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

public class ControllerUtils {
    static Map<String, List<String>> getErrors(BindingResult bindingResult) {
        Map<String, List<String>> allErrors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            String key = error.getField() + "Error";
            if (allErrors.containsKey(key)) {
                allErrors.get(key).add(error.getDefaultMessage());
            } else {
                allErrors.put(key, new ArrayList<>(Collections.singletonList(error.getDefaultMessage())));
            }
        }
        return allErrors;
    }
}