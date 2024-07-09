package com.vehiculo.service;

import com.vehiculo.model.data.VehiculoFilter;
import com.vehiculo.model.entity.Vehiculo;
import com.vehiculo.model.repository.VehiculoRepository;
import com.vehiculo.model.request.VehiculoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static java.lang.String.format;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Page<Vehiculo> getVehiculosPaginated(VehiculoFilter filter, Pageable pageable) {

        var where = new Criteria();
        if (StringUtils.hasText(filter.getMarca())) {
            where.and("marca").regex(format(".*%s.*", filter.getMarca()), "i");
        }
        if (StringUtils.hasText(filter.getModelo())) {
            where.and("modelo").regex(format(".*%s.*", filter.getModelo()), "i");
        }
        if (StringUtils.hasText(filter.getMatricula())) {
            where.and("matricula").regex(format(".*%s.*", filter.getMatricula()), "i");
        }

        final var total = mongoTemplate.count(query(where), Vehiculo.class);
        final var vehiculos = mongoTemplate.find(query(where).with(pageable), Vehiculo.class);

        return PageableExecutionUtils.getPage(vehiculos, pageable, () -> total);

    }

    public Vehiculo createVehiculo(VehiculoRequest vehiculoRequest) {
        Vehiculo vehiculo = Vehiculo.builder()
                .marca(vehiculoRequest.getMarca())
                .modelo(vehiculoRequest.getModelo())
                .matricula(vehiculoRequest.getMatricula())
                .color(vehiculoRequest.getColor())
                .ano(vehiculoRequest.getAno())
                .build();
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo updateVehiculo(String id, VehiculoRequest vehiculoRequest) {
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElseThrow();
        vehiculo.setMarca(vehiculoRequest.getMarca());
        vehiculo.setModelo(vehiculoRequest.getModelo());
        vehiculo.setMatricula(vehiculoRequest.getMatricula());
        vehiculo.setColor(vehiculoRequest.getColor());
        vehiculo.setAno(vehiculoRequest.getAno());
        return vehiculoRepository.save(vehiculo);
    }

    public void deleteVehiculo(String id) {
        vehiculoRepository.deleteById(id);
    }
}
