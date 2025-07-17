package br.com.symon.rentapi.service;

import br.com.symon.rentapi.error.ResourceNotFoundException;
import br.com.symon.rentapi.model.Category;
import br.com.symon.rentapi.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
@Log4j2
public class CategoryService {

    private final CategoryRepository repository;

    public Category create(Category entity) {
        return repository.save(entity);
    }

    public Optional<Category> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("CATEGORY not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public Category update(Category entity) {
        log.debug("Attempting to update CATEGORY with id: {}", entity);
        return repository.save(entity);
    }


}
