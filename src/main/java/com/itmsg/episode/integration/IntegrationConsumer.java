package com.itmsg.episode.integration;

import java.util.Objects;

@FunctionalInterface
public interface IntegrationConsumer<T> {

    void accept(T t) throws Exception;

    default IntegrationConsumer<T> andThen(IntegrationConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
