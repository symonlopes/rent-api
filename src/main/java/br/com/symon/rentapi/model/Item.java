package br.com.symon.rentapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Item {
    private UUID id;
    private String name;
    private String details;
}
