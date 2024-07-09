package com.vehiculo.creator;

import com.vehiculo.model.data.VehiculoFilter;

public class VehiculoFilterCreator {

    public static VehiculoFilter createVehiculoFilter() {
        return VehiculoFilter.builder()
                .marca("Ford")
                .modelo("Fiesta")
                .matricula("1234ABC")
                .build();
    }

}
