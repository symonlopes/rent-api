package br.com.symon.rentapi.controller;

import br.com.symon.rentapi.model.Item;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/items")
@Log4j2
public class ItemController {

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item){
        log.debug("Creating a new item [{}] ", item);

        //Simulating saving
        item.setId(UUID.randomUUID());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

}
