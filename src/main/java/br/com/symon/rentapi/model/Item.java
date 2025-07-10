package br.com.symon.rentapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
@Data
@Table(name = "item")
public class Item {

    @Id
    @Column("id")
    private UUID id;

    @Column("name")
    @NotBlank(message = "Name cannot be ampty.")
    @Size(min = 5, max = 100, message = "Name must have at least 5 and at most 100 characters.")
    private String name;

    @Column("details")
    private String details;

    @NotEmpty(message = "You must have at least one image for the item.")
    @Builder.Default
    @MappedCollection(idColumn = "item_id")
    private Set<Image> images =  new HashSet<>(); //Initialize collections, so you do not have to check "null".

    @Column("category_id")
    private UUID categoryId;

    @Builder.Default
    @MappedCollection(idColumn = "item_id")
    private Set<TagRef> tags =  new HashSet<>();
}
