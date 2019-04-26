package com.foolday.common.base;

@FunctionalInterface
public interface BeanFactory<T> {
    T newInstance();
}
