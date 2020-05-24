package com.github.spartusch.webquery;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WebQueryFactoryJavaTest {

    @Test
    public void create_apiInJava() {
        final WebQuery webQuery = WebQueryFactory.create("http://www.foo.bar/{p1}/{p2}?a=b");
        assertThat(webQuery).isNotNull();
    }

    @Test
    public void create_apiInJavaWithVarargs() {
        final WebQuery webQuery = WebQueryFactory.create("http://www.foo.bar/{p1}/{p2}?a=b",
                new WebQueryParameter("p1", "def1", "dscr1"),
                new WebQueryParameter("p2", "def2", "dscr2"));
        assertThat(webQuery).isNotNull();
    }

    @Test
    public void create_apiInJavaWithPropertiesAndParameters() {
        final Map<String, String> properties = Map.of("k1", "v1");
        final List<WebQueryParameter> parameters = List.of(new WebQueryParameter("p1", "def1", "dscr1"));

        final WebQuery webQuery = WebQueryFactory.create("http://www.foo.bar/{p1}/{p2}?a=b", properties, parameters);

        assertThat(webQuery).isNotNull();
    }

}
