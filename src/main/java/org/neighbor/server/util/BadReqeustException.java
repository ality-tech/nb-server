package org.neighbor.server.util;

/**
 *
 * Created by Konstantin Konyshev on 13/02/2017.
 */
public class BadReqeustException extends Exception {

    public BadReqeustException(String message) {
        super(message);
    }

    public BadReqeustException(String message, Throwable cause) {
        super(message, cause);
    }
}
