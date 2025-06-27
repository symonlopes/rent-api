package br.com.symon.rentapi;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Builder
public class ErrorResponse {

    @Builder.Default
    private Collection<ApiError> errors = new ArrayList<>();
}
