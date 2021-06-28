package common;

import java.util.ArrayList;
import java.util.List;

import common.Constants.ErrorType;

public class ErrorInfo {
    private ErrorType errorType;

    private List<String> errors = new ArrayList<>();

    public ErrorInfo() {
        super();
    }

    public ErrorInfo(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorInfo(ErrorType errorType, String msg) {
        this.errorType = errorType;
        addError(msg);
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addError(String msg) {
        errors.add(msg);
    }

    public boolean hasError() {
        return errors.size() > 0;
    }
}
