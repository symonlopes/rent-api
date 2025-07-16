package br.com.symon.rentapi.dto.responses;

import br.com.symon.rentapi.model.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ItemSaveResponseDTO {

    private UUID id;
    private Category category;
    private String name;
    private String details;

    @Builder.Default
    private Set<TagDTO> tags = new HashSet<>();

    @Builder.Default
    private Set<ImageDTO> images = new HashSet<>();
}