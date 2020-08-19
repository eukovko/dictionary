package com.kovko.dictionary.processor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author: eukovko
 * Date: 6/14/2020
 */
class AuthenticationProcessorTest {

    static final String TEST_TOKEN = "testToken";

    @Test
    void testStripQuotation() {

        String token = AuthenticationProcessor.stripQuotations(String.format("\"%s\"", TEST_TOKEN));
        assertThat(token).isEqualTo(TEST_TOKEN);
    }
}