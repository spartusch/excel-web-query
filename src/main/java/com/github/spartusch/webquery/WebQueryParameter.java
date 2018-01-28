package com.github.spartusch.webquery;

public class WebQueryParameter {

    private String name;
    private String defaultValue;
    private String description;

    public static WebQueryParameter p(final String name, final String defaultValue, final String description) {
        if (name == null || defaultValue == null || description == null) {
            throw new IllegalArgumentException("All WebQueryParameter attributes are required and cannot be null");
        }
        final WebQueryParameter p = new WebQueryParameter();
        p.name = name;
        p.defaultValue = defaultValue;
        p.description = description;
        return p;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getDescription() {
        return description;
    }
}
