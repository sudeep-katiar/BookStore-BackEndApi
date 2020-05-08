package com.bookstore.user.exception;

/**
 * This class extends {@link RuntimeException} and creates a custom exception
 * which would be thrown during the user's abnormal activity
 *
 * @author Durgasankar Mishra
 * @version 1.1
 * @created 2020-05-05
 */
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final int status;

    /**
     * Constructor takes message and Status code as input parameter and fetch
     * message from its superclass.
     *
     * @param message as String input parameter
     * @param status  as Integer input parameter
     */
    public UserNotFoundException( String message, int status ) {
        super (message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
