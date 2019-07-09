package jlauncher.utils;

public abstract class Callback<T, V> {
    public abstract V apply(T arg);
}
