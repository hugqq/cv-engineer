package com.hugqq.config;

import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

public class EnvironmentAlone {
    private static  EnvironmentAlone environmentAlone;
    private ConfigurableEnvironment environment;
    private Map<String, String> propertyMap;

    private EnvironmentAlone(ConfigurableEnvironment env) {
        this.environment = env;
    }

    private EnvironmentAlone(Map<String, String> propertyMap) {
        this.propertyMap = propertyMap;
    }

    public static void createInstance(ConfigurableEnvironment env) {
        synchronized (EnvironmentAlone.class) {
            environmentAlone = new EnvironmentAlone(env);
        }
    }

    public static void createInstance(Map<String, String> propertyMap) {
        synchronized (EnvironmentAlone.class) {
            environmentAlone = new EnvironmentAlone(propertyMap);
        }
    }

    public static EnvironmentAlone getInstance() {
        if (environmentAlone != null) {
            return environmentAlone;
        }

        throw new RuntimeException("Environment isn't created");
    }

    public String getEnvironmentParam(String paramName) {
        if(environment != null) {
            return this.environment.getProperty(paramName);
        }
        return propertyMap.get(paramName);
    }
}

