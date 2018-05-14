package com.github.spartusch.webquery;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.nio.charset.Charset;

public class WebQuery {

    private byte[] content;
    private Charset charset;

    public WebQuery(final String content, final Charset charset) {
        this.content = content.getBytes(charset);
        this.charset = charset;
    }

    public HttpEntity<byte[]> toHttpEntity() {
        return toHttpEntity(null);
    }

    public HttpEntity<byte[]> toHttpEntity(final String attachmentFileName) {
        final var headers = new HttpHeaders();

        headers.set(HttpHeaders.CONTENT_TYPE, "text/plain; charset=" + charset.name());
        headers.setContentLength(content.length);

        if (attachmentFileName != null) {
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachmentFileName);
        }

        return new HttpEntity<>(content, headers);
    }

    public byte[] getContent() {
        return content;
    }

    public Charset getCharset() {
        return charset;
    }

}
