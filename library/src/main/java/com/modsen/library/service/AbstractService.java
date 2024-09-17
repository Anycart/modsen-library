package com.modsen.library.service;

public interface AbstractService<K> {
    void add(K id);

    void delete(K id);
}