package com.vehiculo.controller;

import com.vehiculo.model.entity.Vehiculo;
import com.vehiculo.model.request.VehiculoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Vehiculos",
        description = "Vehiculos endpoints")
public interface VehiculoController {

    @Operation(
            summary = "Get vehiculos by filter",
            description = "Get vehiculos by filter"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = Page.class
                            )
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ResponseEntity.class
                            )
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = {@Content (
                            mediaType = "application/json",
                            schema = @Schema (
                                    implementation = ResponseEntity.class
                            )
                    )}
            )
    })
    String filterVehiculo(Model model, int page, String[] sort, String marca, String modelo, String matricula);

    @Operation(
            summary = "Create vehiculo",
            description = "Create vehiculo"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = Vehiculo.class
                            )
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ResponseEntity.class
                            )
                    )}
            )
    })
    ResponseEntity<String[]> createVehiculo(@RequestBody @Valid VehiculoRequest vehiculoRequest, BindingResult bindingResult);

    @Operation(
            summary = "Update vehiculo",
            description = "Update vehiculo"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = Vehiculo.class
                            )
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ResponseEntity.class
                            )
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = {@Content (
                            mediaType = "application/json",
                            schema = @Schema (
                                    implementation = ResponseEntity.class
                            )
                    )}
            )
    })
    ResponseEntity<String[]> updateVehiculo(String id, VehiculoRequest vehiculoRequest, BindingResult bindingResult);

    @Operation(
            summary = "Delete vehiculo",
            description = "Delete vehiculo"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ResponseEntity.class
                            )
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = {@Content (
                            mediaType = "application/json",
                            schema = @Schema (
                                    implementation = ResponseEntity.class
                            )
                    )}
            )
    })
    ResponseEntity deleteVehiculo(String id);
}
