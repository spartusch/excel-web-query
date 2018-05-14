package com.github.spartusch.webquery;

import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

public class WebQueryFactoryImplTest {

    private WebQueryFactoryImpl factory;

    @Before
    public void setUp() {
        factory = new WebQueryFactoryImpl();
    }

    private String content(final WebQuery query) {
        return new String(query.getContent(), query.getCharset());
    }

    @Test
    public void test_createWebQuery_charSetIsUTF8() {
        final WebQuery query = factory.create("http://www.foo.bar/iqy?a=b");
        assertThat(query.getCharset()).isEqualTo(Charset.forName("UTF-8"));
    }

    @Test
    public void test_createWebQuery_fileExtensionIsIqy() {
        final WebQuery query = factory.create("http://www.foo.bar/iqy?a=b");
        assertThat(query.getFileName()).matches("[\\w]+\\.iqy");
    }

    @Test
    public void test_createWebQuery_simpleHappyCaseWithDiscriminator() {
        final WebQuery query = factory.create("http://www.foo.bar/iqy?a=b", "/iqy");
        assertThat(content(query)).isEqualTo("WEB\r\n1\r\nhttp://www.foo.bar?a=b\r\n");
    }

    @Test
    public void test_createWebQuery_simpleHappyCaseWithoutDiscriminator() {
        final WebQuery query = factory.create("http://www.foo.bar/iqy?a=b", "");
        assertThat(content(query)).isEqualTo("WEB\r\n1\r\nhttp://www.foo.bar/iqy?a=b\r\n");
    }

    @Test
    public void test_createWebQuery_simpleHappyCaseWithEmbeddedDiscriminator() {
        final WebQuery query = factory.create("http://www.foo.bar/iqy/foo?a=b", "/iqy");
        assertThat(content(query)).isEqualTo("WEB\r\n1\r\nhttp://www.foo.bar/foo?a=b\r\n");
    }

    @Test
    public void test_createWebQuery_paramsHappyCaseWithPathParams() {
        final WebQuery query = factory.create("http://www.foo.bar/{p1}/{p2}?a=b",
                WebQueryParameter.p("p1", "def1", "dscr1"),
                WebQueryParameter.p("p2", "def2", "dscr2"));
        assertThat(content(query)).isEqualTo("WEB\r\n1\r\nhttp://www.foo.bar/[\"def1\",\"dscr1\"]/[\"def2\",\"dscr2\"]?a=b\r\n");
    }

    @Test
    public void test_createWebQuery_paramsHappyCaseWithQueryParams() {
        final WebQuery query = factory.create("http://www.foo.bar/?a={p1}&b={p2}",
                WebQueryParameter.p("p1", "def1", "dscr1"),
                WebQueryParameter.p("p2", "def2", "dscr2"));
        assertThat(content(query)).isEqualTo("WEB\r\n1\r\nhttp://www.foo.bar/?a=[\"def1\",\"dscr1\"]&b=[\"def2\",\"dscr2\"]\r\n");
    }

    @Test
    public void test_createWebQuery_paramsHappyCaseWithDiscriminator() {
        final WebQuery query = factory.create("http://www.foo.bar/iqy?a={p1}", "/iqy",
                WebQueryParameter.p("p1", "def1", "dscr1"));
        assertThat(content(query)).isEqualTo("WEB\r\n1\r\nhttp://www.foo.bar?a=[\"def1\",\"dscr1\"]\r\n");
    }
}
