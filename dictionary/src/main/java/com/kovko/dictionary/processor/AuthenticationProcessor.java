package com.kovko.dictionary.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * Author: eukovko
 * Date: 6/13/2020
 */
@Component
@Slf4j
public class AuthenticationProcessor implements Processor {

    private static final String BEARER_TYPE_FORMAT = "Bearer %s";

    // TODO: 8/19/2020 Remove initial token
    private String authenticationToken = "ZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SmxlSEFpT2pFMU9USXlOREF4Tnpnc0lrMXZaR1ZzSWpwN0lrTm9ZWEpoWTNSbGNuTlFaWEpFWVhraU9qVXdNREF3TENKVmMyVnlTV1FpT2pFMk1UTXNJbFZ1YVhGMVpVbGtJam9pTTJGaVpqSTJOVEl0WVRrMU9DMDBOVFEwTFRobFptWXRaRGd3TWpZek56SmtZMlU1SW4xOS4zWlZJWElKRENFV29PckVQWW96amdYVkgwVlpqbnZrRkxURHBKWVBKNkdn";

    @Override
    public void process(Exchange exchange) {

        authenticationToken = stripQuotations(exchange.getIn().getBody(String.class));
        exchange.getIn().setBody(authenticationToken);
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public String getAuthorizationHeader() {
        return String.format(BEARER_TYPE_FORMAT, getAuthenticationToken());
    }

    static String stripQuotations(String token) {
        return token.substring(1, token.length() - 1);
    }
}
