package com.gangoffours.pprs.pprs.common;

import java.util.Objects;
import java.util.function.Supplier;

public final class Lazy<T> {

    private final Supplier<T> _supplier;
    private volatile T value;

    public Lazy(Supplier<T> supplier) {
        _supplier = supplier;
    }

    public synchronized T Value() {
        final T result = value;
        return result == null ? compute() : result;
    }

    public synchronized Boolean HasValue() {
        return value != null;
    }

    private T compute() {
        return value == null ? (value = Objects.requireNonNull(_supplier.get())) : value;
    }
}