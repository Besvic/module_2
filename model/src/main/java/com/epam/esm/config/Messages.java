package com.epam.esm.config;

import java.util.Locale;
import java.util.ResourceBundle;


public class Messages {

    private static Locale locale = Locale.US;

    public static String getMessageForLocale(String messageKey) {
        return ResourceBundle.getBundle("messages", locale)
                .getString(messageKey);
    }

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        Messages.locale = locale;
    }
}
