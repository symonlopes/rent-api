package br.com.symon.rentapi.dto.requests;

import br.com.symon.rentapi.model.Category;
import br.com.symon.rentapi.model.Image;
import br.com.symon.rentapi.model.TagRef;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ItemSaveRequestDTO {
    private UUID id;

    @NotBlank(message = "Name cannot be ampty.")
    @Size(min = 5, max = 100, message = "Name must have at least 5 and at most 100 characters.")
    private String name;
    private String details;

    @NotEmpty(message = "You must have at least one image for the item.")
    @Builder.Default
    private Set<Image> images =  new HashSet<>();

    private Category category;

    @Builder.Default
    @NotEmpty(message = "You must have at least one tag for the item.")
    private Set<TagRef> tags =  new HashSet<>();
}
