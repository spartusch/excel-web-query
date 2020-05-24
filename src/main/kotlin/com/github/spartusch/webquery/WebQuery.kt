package com.github.spartusch.webquery

import java.nio.charset.Charset

class WebQuery internal constructor(content: String, val charset: Charset) {

    val contentBytes = content.toByteArray(charset)
    val contentLength get() = contentBytes.size
    val contentType get() = "text/plain; charset=${charset.name()}"

    override fun toString() = String(contentBytes, charset)

    companion object {
        @JvmStatic
        fun getContentDisposition(fileName: String) = "attachment; filename=$fileName"
    }

}
