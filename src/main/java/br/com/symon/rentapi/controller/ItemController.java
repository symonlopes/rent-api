package br.com.symon.rentapi.controller;

import br.com.symon.rentapi.model.Item;
import br.com.symon.rentapi.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
@AllArgsConstructor
@Log4j2
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item){
        log.debug("Creating a new item [{}] ", item);

        var savedItem = itemService.create(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

}
