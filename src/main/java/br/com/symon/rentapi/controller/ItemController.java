package br.com.symon.rentapi.controller;

import br.com.symon.rentapi.dto.responses.ItemSaveResponseDTO;
import br.com.symon.rentapi.model.Item;
import br.com.symon.rentapi.service.ItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/item")
@AllArgsConstructor
@Log4j2
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ItemSaveResponseDTO> save(@RequestBody @Valid Item item){
        log.debug("Creating a new item [{}] ", item);
        var savedItem = itemService.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    public ResponseEntity<ItemSaveResponseDTO> getById(@PathVariable UUID id) {
        log.debug("Fetching item with id [{}]", id);
        return itemService.findById(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Item> delete(@PathVariable UUID id){
        log.debug("Deleting item with id [{}]", id);
        itemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Item> update(@RequestBody @Valid Item item) {
        var updatedItem = itemService.update(item);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

}
