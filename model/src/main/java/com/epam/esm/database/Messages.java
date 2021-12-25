package com.epam.esm.database;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * The type Messages.
 */
public class Messages {

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
     * Gets locale.
     *
     * @return the locale
     */
    public static Locale getLocale() {
        return locale;
    }

    /**
     * Sets locale.
     *
     * @param locale the locale
     */
    public static void setLocale(Locale locale) {
        Messages.locale = locale;
    }
}
