package main.controllers;

import main.models.Team;
import main.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

public abstract class BaseController<T> {
    @Autowired
    protected BaseService<T> baseService;

    @GetMapping
    public ResponseEntity getEntities() {
        return ResponseEntity.ok(baseService.getAll());
    }

    private Serializable get(Serializable value) {
        try {
            return Integer.parseInt(value.toString());
        } catch (Exception e) {
            return value;
        }
    }

    public ResponseEntity getEntity(Serializable primaryKey) {
        T object = baseService.getById(primaryKey);
        return object == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(object);
    }


    @PostMapping
    public ResponseEntity createEntity(@RequestBody T object) {
        return  ResponseEntity.status(201).body(baseService.create(object));
    }

    public ResponseEntity updateEntity(Serializable primaryKey, T object) {
        T result = baseService.update(primaryKey, object);
        return result == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }

    public ResponseEntity deleteEntity(Serializable primaryKey) {
        T result = baseService.delete(primaryKey);
        return ResponseEntity.status(result == null ? 404 : 204).build();
    }

}
