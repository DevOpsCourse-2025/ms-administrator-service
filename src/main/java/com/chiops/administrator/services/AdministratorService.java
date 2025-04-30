package com.chiops.administrator.services;

import java.util.List;

import com.chiops.administrator.libs.dtos.request.AdministratorRequestDTO;
import com.chiops.administrator.libs.dtos.response.AdministratorResponseDTO;

import jakarta.inject.Singleton;

@Singleton
public interface AdministratorService {
    
    AdministratorResponseDTO signUpAdministrator(AdministratorRequestDTO administrator);
    AdministratorResponseDTO signInAdministrator(AdministratorRequestDTO administrator);
    AdministratorResponseDTO findAdministratorByEmail(String email);
    AdministratorResponseDTO deleteAdministratorByEmail(String email);
    AdministratorResponseDTO updateAdministrator(AdministratorRequestDTO administrator);
    List<AdministratorResponseDTO>  getAdministratorList();
}
