package br.com.symon.rentapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("image")
public class Image {

    @Column("url")
    private String url;

    @Id
    @Column("id")
    private UUID id;

    @Column("item_id")
    private UUID itemId;
}
