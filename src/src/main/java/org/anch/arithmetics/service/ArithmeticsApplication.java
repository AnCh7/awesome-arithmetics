package org.anch.arithmetics.service;

import org.anch.arithmetics.service.errorhandling.AppExceptionMapper;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.ValidationFeature;

public class ArithmeticsApplication extends ResourceConfig {

    public ArithmeticsApplication() {
        register(ArithmeticsResource.class);
        register(AppExceptionMapper.class);
        register(JacksonFeature.class);
        register(ValidationFeature.class);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
    }
}
