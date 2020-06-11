package com.github.spartusch.webquery

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WebQueryTest {

    private lateinit var cut: WebQuery

    @BeforeEach
    fun setUp() {
        cut = WebQuery("content", Charsets.UTF_8)
    }

    @Test
    fun contentBytes_equalsContent() {
        assertThat(cut.contentBytes.contentEquals("content".toByteArray(Charsets.UTF_8))).isTrue()
    }

    @Test
    fun toString_matchesContent() {
        assertThat(cut.toString()).isEqualTo("content")
    }

    @Test
    fun contentLength() {
        assertThat(cut.contentLength).isEqualTo("content".length)
    }

    @Test
    fun contentType() {
        assertThat(cut.contentType).isEqualTo("text/plain; charset=UTF-8")
    }

    @Test
    fun getContentDisposition() {
        assertThat(WebQuery.getContentDisposition("fn.iqy")).isEqualTo("attachment; filename=fn.iqy")
    }
}
