package com.epam.esm.exception;

import java.security.PrivilegedActionException;

/**
 * The type Service exception.
 */
public class ServiceException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for                later retrieval by the {@link #getMessage()} method.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * PrivilegedActionException}***).
     *
     * @param messageForLocale the message for locale
     * @param cause            the cause (which is saved for later retrieval by the {@link #getCause()} method).                         (A <tt>null</tt> value is                         permitted, and indicates that the cause is nonexistent or                         unknown.)
     * @since 1.4
     */
    public ServiceException(String messageForLocale, Throwable cause) {
        super(cause);
    }
}
