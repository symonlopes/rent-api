package br.com.symon.rentapi.repository;

import br.com.symon.rentapi.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository  extends CrudRepository<Image, UUID> {

}

