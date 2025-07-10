package br.com.symon.rentapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Table("tag_item")
public class TagRef {
    @Column("tag_id")
    private UUID tagId;
}
