package com.github.spartusch.webquery;

public interface WebQueryFactory {

    WebQuery create(final String uri, final WebQueryParameter... params);

}
