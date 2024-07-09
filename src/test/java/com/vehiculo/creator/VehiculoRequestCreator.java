package com.vehiculo.creator;

import com.vehiculo.model.request.VehiculoRequest;

import java.math.BigDecimal;

public class VehiculoRequestCreator {

    public static VehiculoRequest createVehiculoRequest() {
        return VehiculoRequest.builder()
                .marca("Ford")
                .modelo("Fiesta")
                .matricula("1234ABC")
                .color("Azul")
                .ano(BigDecimal.valueOf(2021))
                .build();
    }

}
