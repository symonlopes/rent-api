package br.com.symon.rentapi.service;

import br.com.symon.rentapi.ResourceNotFoundException;
import br.com.symon.rentapi.dto.responses.ImageDTO;
import br.com.symon.rentapi.dto.responses.ItemSaveResponseDTO;
import br.com.symon.rentapi.dto.responses.TagDTO;
import br.com.symon.rentapi.model.Item;
import br.com.symon.rentapi.repository.CategoryRepository;
import br.com.symon.rentapi.repository.ItemRepository;
import br.com.symon.rentapi.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
@Log4j2
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public ItemSaveResponseDTO save(Item item) {
        var inserted = itemRepository.save(item);

        return findById(inserted.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found after insertion with id: " + inserted.getId()));
    }

    public Optional<ItemSaveResponseDTO> findById(UUID id) {

        var item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found after id: " + id));

        var itemDto = ItemSaveResponseDTO.builder().build();

        var category = categoryRepository.findById(item.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found after id: " + id));

        item.getTags().forEach(tag -> {
            var tagEntity = tagRepository.findById(tag.getTagId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tag.getTagId()));
            var tagDto = TagDTO.builder().id(tagEntity.getId()).build();
            BeanUtils.copyProperties(tagEntity, tagDto);
            itemDto.getTags().add(tagDto);
        });

        item.getImages().forEach(img -> {
            var imageDto =  ImageDTO.builder().build();
            BeanUtils.copyProperties(img, imageDto);
            itemDto.getImages().add(imageDto);
        });


        BeanUtils.copyProperties(item, itemDto);

        itemDto.setCategory(category);

        return Optional.of(itemDto);
    }

    public void deleteById(UUID id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item not found with id: " + id);
        }
        itemRepository.deleteById(id);
    }

    public Item update(Item itemToUpdate) {
        log.debug("Attempting to update item with id: {}", itemToUpdate);
        return itemRepository.save(itemToUpdate);
    }


}
