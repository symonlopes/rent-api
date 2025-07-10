package br.com.symon.rentapi.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Builder
@Data
@Table("tag")
public class Tag {

    @Id
    @Column("id")
    private UUID id;

    @Column("name")
    private String name;

    @Column("value")
    private String value;
}
