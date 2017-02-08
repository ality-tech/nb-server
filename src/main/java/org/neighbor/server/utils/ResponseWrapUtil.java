package org.neighbor.server.utils;

import org.neighbor.api.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseWrapUtil {

    public static ResponseEntity<GeneralResponse> wrap(GeneralResponse response) {
        switch (response.getHttpCode()) {
            case 200:
                return new ResponseEntity<>(response, HttpStatus.OK);
            case 201:
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            case 202:
                return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            default:
                return ResponseEntity.badRequest().body(response);
        }
    }

}
