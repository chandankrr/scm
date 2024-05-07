package com.chandankrr.scm.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserForm {

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Min 3 characters is required")
    private String name;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Min 6 characters is required")
    private String password;

    @Size(min = 8, max = 12, message = "Invalid phone number")
    private String phoneNumber;

    private String about;
}
