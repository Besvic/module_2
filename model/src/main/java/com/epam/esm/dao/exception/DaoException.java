package com.epam.esm.dao.exception;

import java.security.PrivilegedActionException;

/**
 * The type Dao exception.
 */
public class DaoException extends Exception{

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public DaoException(String message) {
        super(message);
    }
}
