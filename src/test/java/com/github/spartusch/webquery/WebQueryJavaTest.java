package com.github.spartusch.webquery;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class WebQueryJavaTest {

    @Test
    public void webQuery_apiInJava() {
        final WebQuery webQuery = new WebQuery("content", StandardCharsets.UTF_8);

        assertThat(webQuery.getCharset()).isNotNull();
        assertThat(webQuery.getContentBytes()).isNotEmpty();
        assertThat(webQuery.getContentLength()).isGreaterThan(0);
        assertThat(webQuery.getContentType()).isNotBlank();
    }

    @Test
    public void webQuery_staticApiInJava() {
        assertThat(WebQuery.getContentDisposition("file")).isNotBlank();
    }

}
