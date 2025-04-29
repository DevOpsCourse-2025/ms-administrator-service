package com.chiops.administrator.repositories;
import io.micronaut.data.repository.CrudRepository;

import com.chiops.administrator.entities.Administrator;

import io.micronaut.data.annotation.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends CrudRepository<Administrator, Long> {

    Optional<Administrator> findByEmail(String email);
    Optional<Administrator> findByInvitationCode(String invitationCode);
}
