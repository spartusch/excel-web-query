package com.github.spartusch.webquery;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WebQueryParameterTest {

    @Test
    public void test_p_happyCase() {
        final WebQueryParameter p = WebQueryParameter.p("name", "default", "descr");
        assertThat(p.getName()).isEqualTo("name");
        assertThat(p.getDefaultValue()).isEqualTo("default");
        assertThat(p.getDescription()).isEqualTo("descr");
    }

    @Test(expected = NullPointerException.class)
    public void test_p_nameIsRequired() {
        WebQueryParameter.p(null, "some", "some");
    }

    @Test(expected = NullPointerException.class)
    public void test_p_defaultValueIsRequired() {
        WebQueryParameter.p("some", null, "some");
    }

    @Test(expected = NullPointerException.class)
    public void test_p_descriptionIsRequired() {
        WebQueryParameter.p("some", "some", null);
    }
}
