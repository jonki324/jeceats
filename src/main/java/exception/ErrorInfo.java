package exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorInfo {
    private Map<String, List<String>> errors = new HashMap<String, List<String>>();

    public static final String DEFAULT_FIELD_NAME = "system";

    public ErrorInfo() {
        super();
    }

    public ErrorInfo(String errorMessage) {
        addError(DEFAULT_FIELD_NAME, errorMessage);
    }

    public ErrorInfo(String field, String errorMessage) {
        addError(field, errorMessage);
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void addError(String field, String errorMessage) {
        if (errors.containsKey(field)) {
            errors.get(field).add(errorMessage);
        } else {
            List<String> errmsgList = new ArrayList<String>();
            errmsgList.add(errorMessage);
            errors.put(field, errmsgList);
        }
    }

    public boolean hasError() {
        return errors.size() > 0;
    }
}
