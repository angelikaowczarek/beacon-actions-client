package com.github.angelikaowczarek;

import java.util.List;

public class GetOperationParametersParser {
    private List<String> parameters;

    public GetOperationParametersParser(List<String> parameters) {
        this.parameters = parameters;
    }

    public void validateParameters() {
        if (parameters.size() != 1)
            throw new IllegalArgumentException("GET: Illegal argument");
    }

    public String getUniqueId() {
        return parameters.get(0);
    }
}
