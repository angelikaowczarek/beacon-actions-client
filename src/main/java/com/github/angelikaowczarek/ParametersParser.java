package com.github.angelikaowczarek;

import java.util.ArrayList;
import java.util.List;
import static com.github.angelikaowczarek.Actions.*;

public class ParametersParser {
    private String[] args;

    public ParametersParser(String[] args) {
        this.args = args;
    }

    public void initialize() {
        try {
            checkApiKeyAndOperationParameters();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void checkApiKeyAndOperationParameters() {
        if (args.length < 2)
            throw new IllegalArgumentException("Missing parameters");

        String action = args[1];
        if (!action.equals(ADD) && !action.equals(GET) && !action.equals(DELETE))
            throw new IllegalArgumentException("Missing or invalid operation parameter");
    }

    public List<String> convertArgsToParametersList() {
        List<String> parameters = new ArrayList<>();
        for (int i = 2; i < args.length; i++) {
            parameters.add(args[i]);
        }
        return parameters;
    }

    public String getUniqueId(){
        return args[0];
    }

    public String getAction() {
        return args[1];
    }
}
