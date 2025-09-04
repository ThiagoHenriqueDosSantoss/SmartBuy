package com.example.SmartBuy.abstractClass.abstractController;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractController<T,S> {
    protected final Service services;

    public AbstractController(Service services) {
        this.services = services;
    }

    @PostMapping
    public abstract ResponseEntity<T> save(@RequestBody T dto);

    @PutMapping
    public abstract ResponseEntity<T> update(@RequestBody T dto);

    @GetMapping
    public abstract ResponseEntity<List<T>> getAll();

    @DeleteMapping("/{id}")
    public abstract ResponseEntity<Void> remove(@PathVariable long id);
}