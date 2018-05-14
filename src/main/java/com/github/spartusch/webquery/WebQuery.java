package com.github.spartusch.webquery;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;

public class WebQuery {

    private byte[] content;
    private Charset charset;
    private String fileName;

    public WebQuery(final byte[] content, final Charset charset, final String fileName) {
        this.content = content;
        this.charset = charset;
        this.fileName = fileName;
    }

    public HttpEntity<byte[]> toHttpEntity() {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.setContentLength(content.length);
        return new HttpEntity<>(content, headers);
    }

    public byte[] getContent() {
        return content;
    }

    public Charset getCharset() {
        return charset;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

}
