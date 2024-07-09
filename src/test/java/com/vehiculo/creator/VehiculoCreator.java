package com.vehiculo.creator;

import com.vehiculo.model.entity.Vehiculo;

import java.math.BigDecimal;
import java.util.List;

public class VehiculoCreator {

    public static Vehiculo createVehiculo() {
        return Vehiculo.builder()
                .id("1")
                .marca("Ford")
                .modelo("Fiesta")
                .matricula("1234ABC")
                .color("Azul")
                .ano(BigDecimal.valueOf(2021))
                .build();
    }

    public static List<Vehiculo> createVehiculos() {
        var vehiculo1 = Vehiculo.builder()
                .id("2")
                .marca("Ford")
                .modelo("Focus")
                .matricula("5678DEF")
                .color("Rojo")
                .ano(BigDecimal.valueOf(2022))
                .build();

        var vehiculo2 = Vehiculo.builder()
                .id("3")
                .marca("Ford")
                .modelo("Fusion")
                .matricula("9012GHI")
                .color("Verde")
                .ano(BigDecimal.valueOf(2023))
                .build();

        var vehiculo3 = Vehiculo.builder()
                .id("4")
                .marca("Ford")
                .modelo("Mustang")
                .matricula("3456JKL")
                .color("Amarillo")
                .ano(BigDecimal.valueOf(2024))
                .build();

        var vehiculo4 = Vehiculo.builder()
                .id("5")
                .marca("Nissan")
                .modelo("Sentra")
                .matricula("7890MNO")
                .color("Blanco")
                .ano(BigDecimal.valueOf(2025))
                .build();
        return List.of(vehiculo1, vehiculo2, vehiculo3, vehiculo4);
    }

}
