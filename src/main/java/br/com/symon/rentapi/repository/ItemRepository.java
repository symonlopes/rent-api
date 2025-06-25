package br.com.symon.rentapi.repository;

import br.com.symon.rentapi.model.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemRepository {

    public Item save(Item item){

        return item;
    }

}
