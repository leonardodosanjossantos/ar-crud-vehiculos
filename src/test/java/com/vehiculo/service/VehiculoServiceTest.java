package com.vehiculo.service;

import com.vehiculo.creator.VehiculoCreator;
import com.vehiculo.creator.VehiculoFilterCreator;
import com.vehiculo.creator.VehiculoRequestCreator;
import com.vehiculo.model.data.VehiculoFilter;
import com.vehiculo.model.entity.Vehiculo;
import com.vehiculo.model.repository.VehiculoRepository;
import com.vehiculo.model.request.VehiculoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VehiculoServiceTest {

    @Mock
    VehiculoRepository repository;

    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    VehiculoService service;

    Vehiculo vehiculo;

    VehiculoRequest vehiculoRequest;

    VehiculoFilter vehiculoFilter;

    List<Vehiculo> vehiculoList;

    Pageable pageable;

    @BeforeEach
    void setUp() {
        vehiculo = VehiculoCreator.createVehiculo();
        vehiculoRequest = VehiculoRequestCreator.createVehiculoRequest();
        vehiculoFilter = VehiculoFilterCreator.createVehiculoFilter();
        vehiculoList = VehiculoCreator.createVehiculos();
        pageable = PageRequest.of(0, 10);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetVehiculosPaginated() {
        vehiculoFilter.setMarca("Ford");
        vehiculoFilter.setModelo("Mustang");
        vehiculoFilter.setMatricula("3456JKL");

        new Query(new Criteria()
                .andOperator(
                        Criteria.where("marca").regex(".*Ford.*", "i"),
                        Criteria.where("modelo").regex(".*Mustang.*", "i"),
                        Criteria.where("matricula").regex(".*3456JKL.*", "i")
                ));

        when(mongoTemplate.count(any(Query.class), any(Class.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), any(Class.class))).thenReturn(List.of(vehiculoList.get(2)));

        Page<Vehiculo> result = service.getVehiculosPaginated(vehiculoFilter, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(vehiculoList.get(2), result.getContent().get(0));
    }

    @Test
    void testGetVehiculosPaginatedWithEmptyFilter() {
        Query query = new Query(new Criteria());

        when(mongoTemplate.count(any(Query.class), any(Class.class))).thenReturn((long) vehiculoList.size());
        when(mongoTemplate.find(any(Query.class), any(Class.class))).thenReturn(vehiculoList);

        Page<Vehiculo> result = service.getVehiculosPaginated(vehiculoFilter, pageable);

        assertEquals(vehiculoList.size(), result.getTotalElements());
        assertEquals(vehiculoList.size(), result.getContent().size());
        assertEquals(vehiculoList.get(0), result.getContent().get(0));
        assertEquals(vehiculoList.get(1), result.getContent().get(1));
        assertEquals(vehiculoList.get(2), result.getContent().get(2));
        assertEquals(vehiculoList.get(3), result.getContent().get(3));
    }

    @Test
    void testGetVehiculosPaginatedWithNoResults() {
        vehiculoFilter.setMarca("NonExistentBrand");

        new Query(Criteria.where("marca").regex(".*NonExistentBrand.*", "i"));

        when(mongoTemplate.find(any(Query.class), any(Class.class))).thenReturn(List.of());
        when(mongoTemplate.count(any(Query.class), any(Class.class))).thenReturn(0L);

        Page<Vehiculo> result = service.getVehiculosPaginated(vehiculoFilter, pageable);

        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getContent().size());
    }

    @Test
    void testGetVehiculosPaginatedThrowsException() {
        vehiculoFilter.setMarca("Ford");

        new Query(Criteria.where("marca").regex(".*Ford.*", "i"));

        when(mongoTemplate.count(any(Query.class), any(Class.class))).thenThrow(new DataAccessException("Exception during count") {});
        when(mongoTemplate.find(any(Query.class), any(Class.class))).thenThrow(new DataAccessException("Exception during find") {});

        assertThrows(DataAccessException.class, () -> service.getVehiculosPaginated(vehiculoFilter, pageable));
    }

    @Test
    void createVehiculo() {
        when(repository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        service.createVehiculo(vehiculoRequest);

        verify(repository, times(1)).save(any(Vehiculo.class));
        assertEquals(vehiculoRequest.getMatricula(), vehiculo.getMatricula());
    }

    @Test
    void updateVehiculo() {
        String id = "1";
        when(repository.findById(anyString())).thenReturn(Optional.of(vehiculo));
        when(repository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        service.updateVehiculo(id, vehiculoRequest);

        verify(repository, times(1)).save(any(Vehiculo.class));
        verify(repository, times(1)).findById(anyString());
        assertEquals(id, vehiculo.getId());
    }

    @Test
    void deleteVehiculo() {
        String id = "1";

        service.deleteVehiculo(id);

        verify(repository, times(1)).deleteById(id);
    }
}