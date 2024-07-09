package com.vehiculo.controller.impl;

import com.vehiculo.controller.VehiculoController;
import com.vehiculo.model.data.VehiculoFilter;
import com.vehiculo.model.request.VehiculoRequest;
import com.vehiculo.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.Order;

@Controller
public class VehiculoControllerImpl implements VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("redirect:/vehiculos");
    }

    @Override
    @GetMapping("/vehiculos")
    public String filterVehiculo(
            Model model,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "marca,desc") String[] sort,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) String matricula) {

        try {
            String sortField = sort[0];
            String sortDirection = sort[1];

            Direction direction = sortDirection.equals("desc") ? DESC : ASC;
            Order order = new Order(direction, sortField);

            model.addAttribute("marca", marca);
            model.addAttribute("modelo", modelo);
            model.addAttribute("matricula", matricula);

            var filter = VehiculoFilter.builder()
                    .marca(marca)
                    .modelo(modelo)
                    .matricula(matricula)
                    .build();
            var vehiculos = vehiculoService.getVehiculosPaginated(filter, PageRequest.of(page - 1, 10, Sort.by(order)));

            model.addAttribute("vehiculos", vehiculos.getContent());
            model.addAttribute("currentPage", vehiculos.getNumber() + 1);
            model.addAttribute("totalItems", vehiculos.getTotalElements());
            model.addAttribute("totalPages", vehiculos.getTotalPages());
            model.addAttribute("pageSize", 10);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "vehiculos";
    }

    @Override
    @PostMapping("/vehiculo/create")
    public @ResponseBody ResponseEntity<String[]> createVehiculo(
            @RequestBody @Valid VehiculoRequest vehiculoRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String[] errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .toArray(String[]::new);
            return ResponseEntity.badRequest().body(errors);
        }

        vehiculoService.createVehiculo(vehiculoRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/vehiculo/edit/{id}")
    public @ResponseBody ResponseEntity<String[]> updateVehiculo(
            @PathVariable("id") String id,
            @RequestBody @Valid VehiculoRequest vehiculoRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String[] errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .toArray(String[]::new);
            return ResponseEntity.badRequest().body(errors);
        }

        vehiculoService.updateVehiculo(id, vehiculoRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("vehiculo/delete/{id}")
    public ResponseEntity deleteVehiculo(@PathVariable("id") String id) {
        vehiculoService.deleteVehiculo(id);
        return ResponseEntity.ok().build();
    }


}
