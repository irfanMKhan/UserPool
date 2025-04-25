package com.secured.userpool.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class CommonResponseException extends ResponseStatusException {

    public CommonResponseException(HttpStatusCode status) {
        super(status);
    }

    public CommonResponseException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public CommonResponseException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public CommonResponseException(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }

    protected CommonResponseException(HttpStatusCode status, String reason, Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(status, reason, cause, messageDetailCode, messageDetailArguments);
    }

}
