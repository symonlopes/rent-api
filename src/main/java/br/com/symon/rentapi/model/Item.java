package br.com.symon.rentapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Builder
@Data
@Table(name = "items")
public class Item {

    @Id
    private UUID id;

    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String name;

    @NotBlank(message = "TEMP details Não Pode ser vazio")
    @Size(min = 2, max = 100, message = "O details deve ter entre 2 e 100 caracteres")
    private String details;
}
