package com.devy.manager.RestServer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class BadRequestException extends RuntimeException {

    private final List<String> errors;
}
