package com.epam.esm.config;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * The type Messages.
 */
public final class LocalizedMessage {

    private LocalizedMessage() {
    }

    private static Locale locale = Locale.US;

    /**
     * Gets message for locale.
     *
     * @param messageKey the message key
     * @return the message for locale
     */
    public static String getMessageForLocale(String messageKey) {
        return ResourceBundle.getBundle("messages", locale)
                .getString(messageKey);
    }

    /**
     * Sets locale.
     *
     * @param locale the locale
     */
    public static void setLocale(Locale locale) {
        LocalizedMessage.locale = locale;
    }
}
