package com.github.spartusch.webquery;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class WebQueryDefaultFactory implements WebQueryFactory {

    private final Map<String, String> properties;

    public WebQueryDefaultFactory() {
        properties = Map.of("Formatting", "None");
    }

    public WebQueryDefaultFactory(final Map<String, String> properties) {
        Objects.requireNonNull(properties);
        this.properties = Map.copyOf(properties);
    }

    @Override
    public final WebQuery create(final String uri, final WebQueryParameter... params) {
        final var sb = new StringBuilder("WEB\r\n1\r\n").append(uri);

        for (final WebQueryParameter param : params) {
            final var pattern = "{" + param.getName() + "}";
            final var replacement = "[\"" + param.getDefaultValue() + "\",\"" + param.getDescription() + "\"]";
            replaceAll(sb, pattern, replacement);
        }

        sb.append("\r\n");

        if (properties.size() > 0) {
            sb.append("\r\n");
            properties.forEach((key, value) -> {
                sb.append(key).append("=").append(value).append("\r\n");
            });
        }

        return new WebQuery(sb.toString(), StandardCharsets.UTF_8);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    private void replaceAll(final StringBuilder sb, final String pattern, final String replacement) {
        int index;
        while ((index = sb.indexOf(pattern)) != -1) {
            sb.replace(index, index + pattern.length(), replacement);
        }
    }
}
