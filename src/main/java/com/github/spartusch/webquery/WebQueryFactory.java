package com.github.spartusch.webquery;

public interface WebQueryFactory {

    WebQuery create(final String uri, final WebQueryParameter... params);

    WebQuery create(final String uri, final String discriminator, final WebQueryParameter... params);

}
