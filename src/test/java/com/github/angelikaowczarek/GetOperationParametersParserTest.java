package com.github.angelikaowczarek;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GetOperationParametersParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void testThrowExceptionForIncorrectNumberOfArguments() throws Exception {
        // given
        List<String> tooFewParameters = new ArrayList<>();
        GetOperationParametersParser getOperationParametersParser =
                new GetOperationParametersParser(tooFewParameters);

        // when
        getOperationParametersParser.validateParameters();
    }
}