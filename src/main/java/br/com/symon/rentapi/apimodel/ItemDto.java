package br.com.symon.rentapi.apimodel;

import br.com.symon.rentapi.model.Category;
import br.com.symon.rentapi.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
    private UUID id;
    private Category category;
    private String name;
    private String details;

    @Builder.Default
    private Set<Tag> tags = new HashSet<>();
}