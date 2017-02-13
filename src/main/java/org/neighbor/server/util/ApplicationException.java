package org.neighbor.server.util;

/**
 *
 * Created by Konstantin Konyshev on 13/02/2017.
 */
public class ApplicationException extends Exception {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
