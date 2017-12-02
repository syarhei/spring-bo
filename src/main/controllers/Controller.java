package main.controllers;

import main.services.Service;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

public abstract class Controller<T> {
    @Autowired
    protected Service<T> service;

    @GetMapping
    public ResponseEntity getEntities() {
        try {
            return ResponseEntity.ok(service.getAll());
        } catch (Exception e) {
            return getResponseError(e);
        }
    }

    public ResponseEntity getEntity(Serializable primaryKey) {
        try {
            T object = service.getById(primaryKey);
            return object == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(object);
        } catch (Exception e) {
            return getResponseError(e);
        }
    }

    @PostMapping
    public ResponseEntity createEntity(@RequestBody T object) {
        try {
            return ResponseEntity.status(201).body(service.create(object));
        } catch (Exception e) {
            return getResponseError(e);
        }
    }

    public ResponseEntity updateEntity(Serializable primaryKey, T object) {
        try {
            T result = service.update(primaryKey, object);
            return result == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
        } catch (Exception e) {
            return getResponseError(e);
        }

    }

    public ResponseEntity deleteEntity(Serializable primaryKey) {
        try {
            T result = service.delete(primaryKey);
            return ResponseEntity.status(result == null ? 404 : 204).build();
        } catch (Exception e) {
            return getResponseError(e);
        }
    }

    // Error Handler
    protected ResponseEntity getResponseError(final Exception e) {
        return ResponseEntity.status(500).body(new Object() {
            public String error = e.getMessage();
        });
    }
}
