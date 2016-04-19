package com.github.angelikaowczarek;

import java.util.List;

public class AddOperationParametersParser {
    private List<String> parameters;
    public static final String CONTENT = "CONTENT";
    public static final String BROWSER = "BROWSER";

    public AddOperationParametersParser(List<String> parameters) {
        this.parameters = parameters;
    }

    public void validateParameters() {
        if (parameters.size() != 4)
            throw new IllegalArgumentException("ADD: To many or to few parameters");

        if (!parameters.get(1).equals(CONTENT) && !parameters.get(1).equals(BROWSER))
            throw new IllegalArgumentException("ADD: Invalid action type parameter");
    }

    public String getUniqueId() {
        return parameters.get(0);
    }

    public String getActionType() {
        return parameters.get(1);
    }

    public String getPayload() {
        return parameters.get(2);
    }

    public String getProximity() {
        return parameters.get(3);
    }
}
