package com.itmsg.episode.app.system.workflow;

import java.util.Objects;

@FunctionalInterface
public interface WorkflowConsumer<T> {

    void accept(T t) throws Exception;

    default WorkflowConsumer<T> andThen(WorkflowConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
