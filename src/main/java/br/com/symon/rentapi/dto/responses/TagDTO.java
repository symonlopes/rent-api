package br.com.symon.rentapi.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class TagDTO {
    private UUID id;
    private String name;
    private String value;
}
