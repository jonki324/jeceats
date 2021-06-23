package common;

import java.util.ArrayList;
import java.util.List;

public class ErrorInfo {
    private List<String> errors = new ArrayList<>();

    public ErrorInfo() {
        super();
    }

    public ErrorInfo(String msg) {
        addError(msg);
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
