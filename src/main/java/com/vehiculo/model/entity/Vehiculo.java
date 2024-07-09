package com.vehiculo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "vehiculo")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
    private String id;

    private String marca;
    private String modelo;
    private String matricula;
    private String color;
    private BigDecimal ano;

}
