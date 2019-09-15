package com.github.spartusch.webquery;

import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class WebQueryTest {

    private WebQuery webQuery;

    @Before
    public void setUp() {
        webQuery = new WebQuery("content", StandardCharsets.UTF_8);
    }

    @Test
    public void test_getContentType() {
        assertThat(webQuery.getContentType()).isEqualTo("text/plain; charset=UTF-8");
    }

    @Test
    public void test_getContentLength() {
        assertThat(webQuery.getContentLength()).isEqualTo(webQuery.getContent().length);
    }

    @Test
    public void test_getContentDisposition_withFilename() {
        assertThat(webQuery.getContentDisposition("fn.iqy")).isEqualTo("attachment; filename=fn.iqy");
    }

    @Test(expected = NullPointerException.class)
    public void test_getContentDisposition_withoutFilename() {
        webQuery.getContentDisposition(null);
    }
}
