package com.cefetmg.boinabrasa.dto;

import com.cefetmg.boinabrasa.entity.Role;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String login;
    private String senha;
    private Role role;
    private Long pessoaId;
}
