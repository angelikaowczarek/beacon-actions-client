package com.github.angelikaowczarek;

import org.junit.Test;

public class ParametersParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void parserThrowsExceptionWhenMissingParameters() throws Exception {
        // given
        String[] tooFewParameters = {"parameter"};

        // when
        new ParametersParser(tooFewParameters)
                .checkApiKeyAndOperationParameters();
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserThrowsExceptionWhenFirstArgumentIsNotEqualAction() throws Exception {
        // given
        String[] incorrectParameters = {"uniqueIdExample", "notADDorGETorDELETE"};

        // when
        new ParametersParser(incorrectParameters)
                .checkApiKeyAndOperationParameters();
    }
}