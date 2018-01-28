package com.github.spartusch.webquery;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;

import java.nio.charset.Charset;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class WebQueryTest {

    private WebQuery webQuery;

    @Before
    public void setUp() {
        webQuery = new WebQuery("content".getBytes(), Charset.forName("UTF-8"), "fn");
    }

    @Test
    public void test_setFileName() {
        webQuery.setFileName("fn2");
        assertThat(webQuery.getFileName()).isEqualTo("fn2");
    }

    @Test
    public void test_toHttpEntity_content() {
        HttpEntity<byte[]> httpEntity = webQuery.toHttpEntity();
        assertThat(httpEntity.getBody()).isEqualTo(webQuery.getContent());
    }

    @Test
    public void test_toHttpEntity_contentType() {
        HttpEntity<byte[]> httpEntity = webQuery.toHttpEntity();
        assertThat(httpEntity.getHeaders()).contains(entry("Content-Type", singletonList("application/octet-stream")));
    }

    @Test
    public void test_toHttpEntity_contentLength() {
        HttpEntity<byte[]> httpEntity = webQuery.toHttpEntity();
        assertThat(httpEntity.getHeaders()).contains(entry("Content-Length", singletonList(String.valueOf(webQuery.getContent().length))));
    }

    @Test
    public void test_toHttpEntity_contentDisposition() {
        HttpEntity<byte[]> httpEntity = webQuery.toHttpEntity();
        assertThat(httpEntity.getHeaders()).contains(entry("Content-Disposition", singletonList("attachment; filename=fn")));
    }
}
