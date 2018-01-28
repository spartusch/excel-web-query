package com.github.spartusch.webquery;

import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Service
public class WebQueryServiceImpl implements WebQueryService {

    @Override
    public final WebQuery createWebQuery(final String uri, final WebQueryParameter... params) {
        return createWebQuery(uri, null, params);
    }

    @Override
    public final WebQuery createWebQuery(final String uri, final String discriminator, final WebQueryParameter... params) {
        final StringBuilder sb = new StringBuilder("WEB\r\n1\r\n").append(uri);

        for (final WebQueryParameter param : params) {
            final String pattern = "{" + param.getName() + "}";
            final String replacement = "[\"" + param.getDefaultValue() + "\",\"" + param.getDescription() + "\"]";
            replaceAll(sb, pattern, replacement);
        }

        if (discriminator != null && discriminator.length() > 0) {
            replaceAll(sb, discriminator, "");
        }

        sb.append("\r\n");

        final Charset charset = Charset.forName("UTF-8");
        final byte[] content = sb.toString().getBytes(charset);
        final String fileName = "webquery.iqy";

        return new WebQuery(content, charset, fileName);
    }

    private void replaceAll(final StringBuilder sb, final String pattern, final String replacement) {
        int index;
        while ((index = sb.indexOf(pattern)) != -1) {
            sb.replace(index, index + pattern.length(), replacement);
        }
    }
}
