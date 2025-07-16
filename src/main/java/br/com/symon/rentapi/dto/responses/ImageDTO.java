package br.com.symon.rentapi.dto.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class ImageDTO {
    private String url;
    private UUID id;
    private UUID itemId;
}
