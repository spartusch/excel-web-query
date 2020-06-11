package com.github.spartusch.webquery

import java.nio.charset.StandardCharsets

data class WebQueryParameter(val name: String, val defaultValue: String, val description: String)

object WebQueryFactory {
    private fun replacePlaceholders(
        uriWithPlaceholders: String,
        params: List<WebQueryParameter>
    ) = params.fold(uriWithPlaceholders) {
        uri, param -> uri.replace("{${param.name}}", "[\"${param.defaultValue}\",\"${param.description}\"]")
    }

    @JvmStatic
    fun create(
        uriWithPlaceholders: String,
        vararg params: WebQueryParameter
    ) = create(uriWithPlaceholders = uriWithPlaceholders, params = params.toList())

    @JvmStatic
    fun create(
        uriWithPlaceholders: String,
        properties: Map<String, String> = mapOf("Formatting" to "None"),
        params: List<WebQueryParameter> = emptyList()
    ): WebQuery {
        val uri = replacePlaceholders(uriWithPlaceholders, params)
        var content = "WEB\r\n1\r\n$uri\r\n"

        if (properties.isNotEmpty()) {
            content += properties
                    .map { (key, value) -> "$key=$value" }
                    .joinToString(prefix = "\r\n", separator = "\r\n", postfix = "\r\n")
        }

        return WebQuery(content, StandardCharsets.UTF_8)
    }
}
