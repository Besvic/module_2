package com.epam.esm.util;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * The type Localized message.
 */
public final class LocalizedMessage {

    private LocalizedMessage() {
    }

    /**
     * Gets message for locale.
     *
     * @param messageKey the message key
     * @return the message for locale
     */
    public static String getMessageForLocale(String messageKey) {
        return ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale())
                .getString(messageKey);
    }

    /**
     * Gets message for locale.
     *
     * @param messageKey the message key
     * @param locale     the locale
     * @return the message for locale
     */
    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle("messages", locale)
                .getString(messageKey);
    }
}
