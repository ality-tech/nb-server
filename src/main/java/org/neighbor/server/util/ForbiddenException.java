package org.neighbor.server.util;

/**
 *
 * Created by Konstantin Konyshev on 13/02/2017.
 */
public class ForbiddenException extends Exception {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
