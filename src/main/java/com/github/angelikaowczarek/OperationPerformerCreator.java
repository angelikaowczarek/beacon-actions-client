package com.github.angelikaowczarek;

import java.io.IOException;

import static com.github.angelikaowczarek.Actions.*;

public class OperationPerformerCreator {
    private ParametersParser parametersParser;

    public OperationPerformerCreator(ParametersParser parametersParser) {
        this.parametersParser = parametersParser;
    }

    public void initialize() {
        parametersParser.initialize();

        OperationPerformer operationPerformer =
                createOperationPerformer(parametersParser.getUniqueId(), parametersParser.getAction());

        try {
            operationPerformer.perform(parametersParser.convertArgsToParametersList());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private OperationPerformer createOperationPerformer(String uniqueId, String action) {
        OperationPerformer operationPerformer;
        if (action.equals(ADD)) {
            operationPerformer = new AddOperationPerformer(uniqueId);
        } else if (action.equals(GET)) {
            operationPerformer = new GetOperationPerformer(uniqueId);
        } else
            operationPerformer = new DeleteOperationPerformer(uniqueId);
        return operationPerformer;
    }

}
