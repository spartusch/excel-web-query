package com.github.spartusch.webquery;

public interface WebQueryService {

    WebQuery createWebQuery(final String uri, final WebQueryParameter... params);

    WebQuery createWebQuery(final String uri, final String discriminator, final WebQueryParameter... params);

}
