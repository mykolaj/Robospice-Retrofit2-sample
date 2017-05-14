package ua.techguardians.robospice.sample.retrofit2.errors;

/*
 * Created on 14.05.17.
 */

/**
 * This class stores an information about a failed request execution
 */
public class CustomRequestFailureException extends Exception {

    private Integer httpStatusCode;
    private String message;
    private String errorBody;

    public CustomRequestFailureException(final int httpStatusCode, final String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(String errorBody) {
        this.errorBody = errorBody;
    }
}
