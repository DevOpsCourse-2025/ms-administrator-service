package com.chiops.administrator.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import static jakarta.persistence.GenerationType.AUTO;

import jakarta.persistence.GeneratedValue;
import io.micronaut.data.annotation.Id;

@Entity
@Table(name = "administrator")
public class Administrator {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id")
    Long id;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Column(name = "invitation_code", nullable = false, unique = true)
    @NotBlank(message = "Invitation code cannot be blank")

    private String invitationCode;

    public Administrator(String email, String password, String invitationCode) {
        this.email = email;
        this.password = password;
        this.invitationCode = invitationCode;
    }

    public Administrator() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

}
