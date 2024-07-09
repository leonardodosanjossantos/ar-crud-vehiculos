package com.vehiculo.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoRequest {

    @NotBlank(message = "Se requiere Marca")
    @NotNull(message = "Se requiere Marca")
    private String marca;

    @NotBlank(message = "Se requiere Modelo")
    private String modelo;

    @NotBlank(message = "Se requiere Matricula")
    private String matricula;

    @NotBlank(message = "Se requiere Color")
    private String color;

    @NotNull(message = "Se requiere Año")
    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    @Digits(integer = 4, fraction = 0, message = "El año debe tener 4 dígitos")
    private BigDecimal ano;

}
