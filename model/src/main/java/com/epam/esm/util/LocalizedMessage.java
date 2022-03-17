package com.epam.esm.util;

import org.springframework.context.i18n.LocaleContextHolder;

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
}
