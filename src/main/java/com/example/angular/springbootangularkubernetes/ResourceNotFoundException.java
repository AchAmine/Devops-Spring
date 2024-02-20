package com.example.angular.springbootangularkubernetes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CustomHttpException {
    String getMessage();
    HttpStatus getHttpStatus();
}

public class ResourceNotFoundException extends RuntimeException implements CustomHttpException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}

public class ForbiddenException extends RuntimeException implements CustomHttpException {

    public ForbiddenException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
