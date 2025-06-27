package br.com.symon.rentapi.controller;

import br.com.symon.rentapi.model.Item;
import br.com.symon.rentapi.service.ItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/items")
@AllArgsConstructor
@Log4j2
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody @Valid Item item){
        log.debug("Creating a new item [{}] ", item);
        var savedItem = itemService.create(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable UUID id) {
        log.debug("Fetching item with id [{}]", id);
        return itemService.findById(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
