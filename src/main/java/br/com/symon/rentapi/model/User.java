package br.com.symon.rentapi.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@Table(name = "user")
public class User {
    @Id
    @Column("id")
    private UUID id;

    @Column("name")
    private String name;

    @Column("password_hash")
    private String passwordHash;

    @Column("email")
    private String email;

    @Builder.Default
    @MappedCollection(idColumn = "user_id")
    private Set<Role> roles =  new HashSet<>();

}