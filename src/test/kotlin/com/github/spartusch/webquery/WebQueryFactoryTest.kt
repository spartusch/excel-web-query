package com.github.spartusch.webquery

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WebQueryFactoryTest {

    private var cut = WebQueryFactory

    @Test
    fun create_charSetIsUTF8() {
        val query = cut.create("http://www.foo.bar/iqy?a=b")
        assertThat(query.charset).isEqualTo(Charsets.UTF_8)
    }

    @Test
    fun create_withDefaultProperties() {
        val query = cut.create("http://www.foo.bar/?a=b")

        assertThat(query.toString())
                .isEqualTo("WEB\r\n1\r\nhttp://www.foo.bar/?a=b\r\n\r\nFormatting=None\r\n")
    }

    @Test
    fun create_withCustomProperties() {
        val query = cut.create("http://www.foo.bar/?a=b", mapOf("k1" to "v1", "k2" to "v2"))

        assertThat(query.toString())
                .startsWith("WEB\r\n1\r\nhttp://www.foo.bar/?a=b\r\n\r\n")
                .contains("k1=v1\r\n")
                .contains("k2=v2\r\n")
    }

    @Test
    fun create_withEmptyProperties() {
        val query = cut.create("http://www.foo.bar/?a=b", emptyMap())

        assertThat(query.toString())
                .isEqualTo("WEB\r\n1\r\nhttp://www.foo.bar/?a=b\r\n")
    }

    @Test
    fun create_withPathParams() {
        val query = cut.create("http://www.foo.bar/{p1}/{p2}?a=b",
                WebQueryParameter("p1", "def1", "dscr1"),
                WebQueryParameter("p2", "def2", "dscr2"))

        assertThat(query.toString())
                .startsWith("WEB\r\n1\r\nhttp://www.foo.bar/[\"def1\",\"dscr1\"]/[\"def2\",\"dscr2\"]?a=b\r\n")
    }

    @Test
    fun create_withQueryParams() {
        val query = cut.create("http://www.foo.bar/?a={p1}&b={p2}",
                WebQueryParameter("p1", "def1", "dscr1"),
                WebQueryParameter("p2", "def2", "dscr2"))

        assertThat(query.toString())
                .startsWith("WEB\r\n1\r\nhttp://www.foo.bar/?a=[\"def1\",\"dscr1\"]&b=[\"def2\",\"dscr2\"]\r\n")
    }
}
