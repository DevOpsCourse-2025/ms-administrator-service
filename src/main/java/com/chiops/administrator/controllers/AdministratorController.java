package com.chiops.administrator.controllers;

import com.chiops.administrator.libs.dtos.request.AdministratorRequestDTO;
import com.chiops.administrator.libs.dtos.response.AdministratorResponseDTO;
import com.chiops.administrator.libs.exceptions.exception.BadRequestException;
import com.chiops.administrator.libs.exceptions.exception.InternalServerException;
import com.chiops.administrator.services.AdministratorService;

import io.micronaut.http.annotation.*;
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
            throw new BadRequestException("Bad request while registering administrator: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error while registering administrator: " + e.getMessage());
        }
    }

    @Post("/login")
    public AdministratorResponseDTO signInAdministrator(@Valid @Body AdministratorRequestDTO administrator) {
        try {
            return administratorService.signInAdministrator(administrator);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request while signing in: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error while signing in: " + e.getMessage());
        }
    }

    @Post("/get/{email}")
    public AdministratorResponseDTO findAdministratorByEmail(@Valid @PathVariable String email) {
        try {
            return administratorService.findAdministratorByEmail(email);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request while retrieving administrator with email " + email + ": " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error while retrieving administrator with email " + email + ": " + e.getMessage());
        }
    }

    @Delete("/delete/{email}")
    public void deleteAdministratorByEmail(@Valid @PathVariable String email) {
        try {
            administratorService.deleteAdministratorByEmail(email);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request while deleting administrator with email " + email + ": " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error while deleting administrator with email " + email + ": " + e.getMessage());
        }
    }

    @Put("/update")
    public AdministratorResponseDTO updateAdministrator(@Valid @Body AdministratorRequestDTO administrator) {
        try {
            return administratorService.updateAdministrator(administrator);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request while updating administrator: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error while updating administrator: " + e.getMessage());
        }
    }

    @Get("/getall")
    public List<AdministratorResponseDTO> getAdministratorList() {
        try {
            return administratorService.getAdministratorList();
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request while retrieving administrator list: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error while retrieving administrator list: " + e.getMessage());
        }
    }
}
