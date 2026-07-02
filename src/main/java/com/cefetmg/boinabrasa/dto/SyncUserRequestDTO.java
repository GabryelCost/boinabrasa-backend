package com.cefetmg.boinabrasa.dto;

import lombok.Data;

@Data
public class SyncUserRequestDTO {
    private String login;
    private String senha;
    private String role;
}
