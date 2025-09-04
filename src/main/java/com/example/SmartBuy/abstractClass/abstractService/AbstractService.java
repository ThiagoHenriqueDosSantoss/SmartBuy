package com.example.SmartBuy.abstractClass.abstractService;

import org.springframework.stereotype.Repository;

import java.util.List;

public abstract class AbstractService<T,R> {

    protected final Repository repository;

    protected AbstractService(Repository repository) {
        this.repository = repository;
    }

    public abstract T save(T dto);

    public abstract T update(T dto);

    public abstract List<T> get();

    public abstract T remove(long id);
}
