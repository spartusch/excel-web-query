package com.github.spartusch.webquery;

import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WebQueryDefaultFactoryTest {

    private WebQueryDefaultFactory factory;

    @Before
    public void setUp() {
        factory = new WebQueryDefaultFactory();
    }

    private String content(final WebQuery query) {
        return new String(query.getContent(), query.getCharset());
    }

    @Test
    public void test_defaultConstructor_setsFormattingPropertyToNone() {
        assertThat(factory.getProperties().get("Formatting")).isEqualTo("None");
    }

    @Test(expected = NullPointerException.class)
    public void test_propertyConstructor_requiresProperties() {
        new WebQueryDefaultFactory(null);
    }

    @Test
    public void test_propertyConstructor_copiesPropertiesImmutably() {
        final var initialProperties = new HashMap<String, String>();
        initialProperties.put("a", "b");
        initialProperties.put("1", "2");
        factory = new WebQueryDefaultFactory(initialProperties);

        initialProperties.put("modify", "the map");
        final var properties = factory.getProperties();

        assertThat(properties.size()).isEqualTo(2);
        assertThat(properties.containsKey("a")).isTrue();
        assertThat(properties.containsKey("1")).isTrue();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_getProperties_propertiesAreImmutable() {
        factory.getProperties().put("modify", "the map");
    }

    @Test
    public void test_createWebQuery_charSetIsUTF8() {
        final WebQuery query = factory.create("http://www.foo.bar/iqy?a=b");
        assertThat(query.getCharset()).isEqualTo(StandardCharsets.UTF_8);
    }

    @Test
    public void test_createWebQuery_simpleHappyCaseWithDefaultProperty() {
        final WebQuery query = factory.create("http://www.foo.bar/?a=b");
        assertThat(content(query)).isEqualTo("WEB\r\n1\r\nhttp://www.foo.bar/?a=b\r\n\r\nFormatting=None\r\n");
    }

    @Test
    public void test_createWebQuery_simpleHappyCaseWithProperties() {
        final var properties = Map.of("k1", "v1", "k2", "v2");
        factory = new WebQueryDefaultFactory(properties);

        final WebQuery query = factory.create("http://www.foo.bar/?a=b");

        final String queryContent = content(query);
        assertThat(queryContent).startsWith("WEB\r\n1\r\nhttp://www.foo.bar/?a=b\r\n\r\n");
        assertThat(queryContent).contains("k1=v1\r\n");
        assertThat(queryContent).contains("k2=v2\r\n");
    }

    @Test
    public void test_createWebQuery_simpleHappyCaseWithoutProperties() {
        factory = new WebQueryDefaultFactory(Map.of());
        final WebQuery query = factory.create("http://www.foo.bar/?a=b");
        assertThat(content(query)).isEqualTo("WEB\r\n1\r\nhttp://www.foo.bar/?a=b\r\n");
    }

    @Test
    public void test_createWebQuery_paramsHappyCaseWithPathParams() {
        final WebQuery query = factory.create("http://www.foo.bar/{p1}/{p2}?a=b",
                WebQueryParameter.p("p1", "def1", "dscr1"),
                WebQueryParameter.p("p2", "def2", "dscr2"));
        assertThat(content(query)).startsWith("WEB\r\n1\r\nhttp://www.foo.bar/[\"def1\",\"dscr1\"]/[\"def2\",\"dscr2\"]?a=b\r\n");
    }

    @Test
    public void test_createWebQuery_paramsHappyCaseWithQueryParams() {
        final WebQuery query = factory.create("http://www.foo.bar/?a={p1}&b={p2}",
                WebQueryParameter.p("p1", "def1", "dscr1"),
                WebQueryParameter.p("p2", "def2", "dscr2"));
        assertThat(content(query)).startsWith("WEB\r\n1\r\nhttp://www.foo.bar/?a=[\"def1\",\"dscr1\"]&b=[\"def2\",\"dscr2\"]\r\n");
    }
}
