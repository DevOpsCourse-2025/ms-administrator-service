package com.chiops.administrator.services.impl;

import com.chiops.administrator.entities.Administrator;
import com.chiops.administrator.libs.exceptions.exception.*;
import com.chiops.administrator.libs.clients.InvitationCodeClient;
import com.chiops.administrator.libs.dtos.request.AdministratorRequestDTO;
import com.chiops.administrator.libs.dtos.response.AdministratorResponseDTO;
import com.chiops.administrator.repositories.AdministratorRepository;
import com.chiops.administrator.services.AdministratorService;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class AdministratorServiceImpl implements AdministratorService {

    private final InvitationCodeClient invitationCodeClient;
    private final AdministratorRepository administratorRepository;

    public AdministratorServiceImpl(
            InvitationCodeClient invitationCodeClient, 
            AdministratorRepository administratorRepository) {
        this.invitationCodeClient = invitationCodeClient;
        this.administratorRepository = administratorRepository;
    }

    @Override
    public AdministratorResponseDTO signUpAdministrator(AdministratorRequestDTO administrator) {
        if(invitationCodeClient.findByCode(administrator.getInvitationCode()).isEmpty()) {
            throw new NotFoundException("Invitation code " + administrator.getInvitationCode() + " not found");
        }

        if (administratorRepository.findByEmail(administrator.getEmail()).isPresent()) {
            throw new ConflictException("Email " + administrator.getEmail() + " already exists");
        }

        if (administratorRepository.findByInvitationCode(administrator.getInvitationCode()).isPresent()) {
            throw new ConflictException("Invitation code " + administrator.getInvitationCode() + " already used");
        }

        invitationCodeClient.markAsUsed(administrator.getInvitationCode());

        Administrator entity = toEntity(administrator);
        entity.setInvitationCode(administrator.getInvitationCode());

        return toResponseDTO(administratorRepository.save(entity));
    }

    @Override
    public AdministratorResponseDTO findAdministratorByEmail(String email) {
        Administrator administrator = administratorRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Administrator with email " + email + " not found"));

        return toResponseDTO(administrator);
    }

    @Override
    public AdministratorResponseDTO signInAdministrator(AdministratorRequestDTO administrator) {
        Administrator existingAdministrator = administratorRepository.findByEmail(administrator.getEmail())
                .orElseThrow(() -> new NotFoundException("Administrator with email " + administrator.getEmail() + " not found"));

        if (!existingAdministrator.getPassword().equals(administrator.getPassword())
            || !existingAdministrator.getInvitationCode().equals(administrator.getInvitationCode())) {
            throw new BadRequestException("Invalid credentials for administrator " + administrator.getEmail());
        }

        if (existingAdministrator.getInvitationCode() == null) {
            throw new ConflictException("No invitation code associated with administrator " + administrator.getEmail());
        }

        return toResponseDTO(existingAdministrator);
    }

    @Override
    public void deleteAdministratorByEmail(String email) {
        Administrator administrator = administratorRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Administrator with email " + email + " not found"));

        administratorRepository.delete(administrator);
    }

    @Override
    public AdministratorResponseDTO updateAdministrator(AdministratorRequestDTO administrator) {
        Administrator existingAdministrator = administratorRepository.findByEmail(administrator.getEmail())
                .orElseThrow(() -> new NotFoundException("Administrator with email " + administrator.getEmail() + " not found"));

        existingAdministrator.setEmail(administrator.getEmail());
        existingAdministrator.setPassword(administrator.getPassword());
        existingAdministrator.setInvitationCode(administrator.getInvitationCode());

        return toResponseDTO(administratorRepository.update(existingAdministrator));
    }

    @Override
    public List<AdministratorResponseDTO> getAdministratorList() {
        List<Administrator> admins = administratorRepository.findAll();
        if (admins.isEmpty()) {
            throw new NotFoundException("No administrators found in the system");
        }
        return admins.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    private Administrator toEntity(AdministratorRequestDTO dto) {
        Administrator admin = new Administrator();
        admin.setEmail(dto.getEmail());
        admin.setPassword(dto.getPassword());
        admin.setInvitationCode(dto.getInvitationCode());
        return admin;
    }

    private AdministratorResponseDTO toResponseDTO(Administrator admin) {
        AdministratorResponseDTO dto = new AdministratorResponseDTO();
        dto.setEmail(admin.getEmail());
        dto.setInvitationCode(admin.getInvitationCode());
        return dto;
    }
}