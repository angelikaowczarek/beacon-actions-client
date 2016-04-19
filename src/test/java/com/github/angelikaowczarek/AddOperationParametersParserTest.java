package com.github.angelikaowczarek;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddOperationParametersParserTest {
    GetOperationParametersParser getOperationParametersParser;

    @Test(expected = IllegalArgumentException.class)
    public void testThrowExceptionForIncorrectNumberOfArguments() throws IOException {
        // given
        List<String> tooFewParameters = new ArrayList<>();
        tooFewParameters.add("parameter");
        tooFewParameters.add("CONTENT");
        tooFewParameters.add("parameter");

        getOperationParametersParser =
                new GetOperationParametersParser(tooFewParameters);

        // when
        getOperationParametersParser.validateParameters();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowExceptionForIncorrectActionTypeArgument() throws IOException {
        // given
        List<String> parametersWithIncorrectArgument = new ArrayList<>();
        parametersWithIncorrectArgument.add("parameter");
        parametersWithIncorrectArgument.add("parameterDifferentFromContentOrBrowser");
        parametersWithIncorrectArgument.add("parameter");
        parametersWithIncorrectArgument.add("parameter");

        getOperationParametersParser =
                new GetOperationParametersParser(parametersWithIncorrectArgument);

        // when
        getOperationParametersParser.validateParameters();
    }
}