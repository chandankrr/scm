package com.chandankrr.scm.form;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserForm {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String about;
}
