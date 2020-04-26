package com.wedsite.sale.service.dto;


import com.wedsite.sale.common.validator.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * The type Password reset dto.
 */
@Data
@PasswordMatches
public class PasswordResetDTO {

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    @NotEmpty
    private String token;
}
