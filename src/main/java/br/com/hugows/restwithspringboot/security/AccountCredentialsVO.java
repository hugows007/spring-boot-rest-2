package br.com.hugows.restwithspringboot.security;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AccountCredentialsVO implements Serializable {

    private String username;
    private String password;
}
