package br.com.symon.rentapi;

import lombok.Builder;

@Builder
public record ApiError(String code, String message, String detail, String field) { }
