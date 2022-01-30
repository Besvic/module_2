package com.epam.esm.config;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ResourceBundle;


public final class LocalizedMessage {

    private LocalizedMessage() {
    }

    public static String getMessageForLocale(String messageKey) {
        return ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale())
                .getString(messageKey);
    }
}
