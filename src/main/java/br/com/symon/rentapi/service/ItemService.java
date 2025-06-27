package br.com.symon.rentapi.service;

import br.com.symon.rentapi.model.Item;
import br.com.symon.rentapi.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
@Log4j2
public class ItemService {

    private final ItemRepository itemRepository;

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Optional<Item> findById(UUID id) {
        return itemRepository.findById(id);
    }

}
