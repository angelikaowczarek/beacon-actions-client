package com.github.angelikaowczarek;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DeleteOperationParametersParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void testThrowExceptionForIncorrectNumberOfArguments() throws Exception {
        // given
        List<String> tooFewParameters = new ArrayList<>();
        DeleteOperationParametersParser deleteOperationParametersParser =
                new DeleteOperationParametersParser(tooFewParameters);

        // when
        deleteOperationParametersParser.validateParameters();
    }
}