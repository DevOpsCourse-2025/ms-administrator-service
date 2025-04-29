package com.chiops.administrator.libs.dtos;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Introspected
@Serdeable
public class InvitationCodeDTO {
    @NotBlank(message = "Code cannot be blank")
    private String code;

    private String status;

    public InvitationCodeDTO(String status, String code) {
        this.status = status;
        this.code = code;
    }

    public InvitationCodeDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
