package br.com.symon.rentapi.controller;

import br.com.symon.rentapi.model.*;
import br.com.symon.rentapi.service.CategoryService;
import br.com.symon.rentapi.service.ItemService;
import br.com.symon.rentapi.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Category> create(@RequestBody @Valid Category entity) {
        log.debug("Creating a new CATEGORY [{}] ", entity);
        var saved = categoryService.create(entity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable UUID id) {
        log.debug("Fetching CATEGORY with id [{}]", id);
        return categoryService.findById(id)
                .map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Category> delete(@PathVariable UUID id){
        log.debug("Deleting CATEGORY with id [{}]", id);
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Category> update(@RequestBody @Valid Category entity) {
        log.debug("Updating CATEGORY with id [{}]", entity);
        var updatedItem = categoryService.update(entity);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

}
