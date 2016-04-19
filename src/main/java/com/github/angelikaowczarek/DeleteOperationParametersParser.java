package com.github.angelikaowczarek;

import java.util.List;

public class DeleteOperationParametersParser {
    private List<String> parameters;

    public DeleteOperationParametersParser(List<String> parameters) {
        this.parameters = parameters;
    }

    public void validateParameters() {
        if (parameters.size() != 1)
            throw new IllegalArgumentException("DELETE: Illegal argument");
    }

    public String getActionId() {
        return parameters.get(0);
    }
}
