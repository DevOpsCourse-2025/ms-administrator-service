package com.chiops.administrator.controllers;

import com.chiops.administrator.libs.dtos.request.AdministratorRequestDTO;
import com.chiops.administrator.libs.dtos.response.AdministratorResponseDTO;
import com.chiops.administrator.libs.exceptions.entities.ErrorResponse;
import com.chiops.administrator.libs.exceptions.exception.BadRequestException;
import com.chiops.administrator.libs.exceptions.exception.InternalServerException;
import com.chiops.administrator.libs.exceptions.exception.MethodNotAllowedException;
import com.chiops.administrator.libs.exceptions.exception.NotFoundException;
import com.chiops.administrator.services.AdministratorService;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;
import java.util.List;

@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/admin")
@Validated
@Secured(SecurityRule.IS_ANONYMOUS)
public class AdministratorController {

    private final AdministratorService administratorService;

    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @Post("/register")
    public AdministratorResponseDTO createAdministrator(@Valid @Body AdministratorRequestDTO administrator) {
        try {
            return administratorService.signUpAdministrator(administrator);
        } catch (BadRequestException e) {
            throw new BadRequestException("Error de solicitud al registrar administrador: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Error interno al registrar administrador: " + e.getMessage());
        }
    }

    @Post("/login")
    public AdministratorResponseDTO signInAdministrator(@Valid @Body AdministratorRequestDTO administrator) {
        try {
            return administratorService.signInAdministrator(administrator);
        } catch (BadRequestException e) {
            throw new BadRequestException("Error de solicitud al iniciar sesión: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Error interno al iniciar sesión: " + e.getMessage());
        }
    }

    @Post("/get/{email}")
    public AdministratorResponseDTO findAdministratorByEmail(@Valid @PathVariable String email) {
        try {
            return administratorService.findAdministratorByEmail(email);
        } catch (BadRequestException e) {
            throw new BadRequestException("Error de solicitud al obtener administrador con email: " + email + ". " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Error interno al obtener administrador con email: " + email + ". " + e.getMessage());
        }
    }

    @Delete("/delete/{email}")
    public void deleteAdministratorByEmail(@Valid @PathVariable String email) {
        try {
            administratorService.deleteAdministratorByEmail(email);
        } catch (BadRequestException e) {
            throw new BadRequestException("Error de solicitud al eliminar administrador con email: " + email + ". " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Error interno al eliminar administrador con email: " + email + ". " + e.getMessage());
        }
    }

    @Put("/update")
    public AdministratorResponseDTO updateAdministrator(@Valid @Body AdministratorRequestDTO administrator) {
        try {
            return administratorService.updateAdministrator(administrator);
        } catch (BadRequestException e) {
            throw new BadRequestException("Error de solicitud al actualizar administrador: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Error interno al actualizar administrador: " + e.getMessage());
        }
    }

    @Get("/getall")
    public List<AdministratorResponseDTO> getAdministratorList() {
        try {
            return administratorService.getAdministratorList();
        } catch (BadRequestException e) {
            throw new BadRequestException("Error de solicitud al obtener la lista de administradores: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Error interno al obtener la lista de administradores: " + e.getMessage());
        }
    }
}
