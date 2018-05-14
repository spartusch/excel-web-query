package com.github.spartusch.webquery;

import java.util.Objects;

public class WebQueryParameter {

    private String name;
    private String defaultValue;
    private String description;

    public static WebQueryParameter p(final String name, final String defaultValue, final String description) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(description);

        final var p = new WebQueryParameter();
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
