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

    @Column("URL")
    private String url;

    @Id @Column("ID")
    private UUID id;

    @Column("ITEM_ID")
    private UUID itemId;
}
