package com.github.angelikaowczarek;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new OperationPerformerCreator(
                new ParametersParser(args))
                .initialize();
    }
}