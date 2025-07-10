package br.com.symon.rentapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table("category")
public class Category {

    @Id
    private UUID id;

    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name cannot be empty.")
    @Size(min = 3, max = 30, message = "Name must have at least 3 and at most 30 characters.")
    @Column("name")
    private String name;
}
