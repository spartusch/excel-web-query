package com.github.spartusch.webquery;

import java.nio.charset.Charset;
import java.util.Objects;

public class WebQuery {

    private byte[] content;
    private Charset charset;

    public WebQuery(final String content, final Charset charset) {
        this.content = content.getBytes(charset);
        this.charset = charset;
    }

    public byte[] getContent() {
        return content;
    }

    public Charset getCharset() {
        return charset;
    }

    public String getContentType() {
        return "text/plain; charset=" + charset.name();
    }

    public int getContentLength() {
        return content.length;
    }

    public String getContentDisposition(final String fileName) {
        Objects.requireNonNull(fileName);
        return "attachment; filename=" + fileName;
    }
}
