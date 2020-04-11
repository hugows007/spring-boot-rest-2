package br.com.hugows.restwithspringboot.controller;

import br.com.hugows.restwithspringboot.data.model.User;
import br.com.hugows.restwithspringboot.repository.UserRepository;
import br.com.hugows.restwithspringboot.security.AccountCredentialsVO;
import br.com.hugows.restwithspringboot.security.jwt.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "AuthenticationEndpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @ApiOperation(value = "Authenticate a user and returns a token")
    @PostMapping(value = "/signin")
    public ResponseEntity<?> create(@RequestBody AccountCredentialsVO credentialsVO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentialsVO.getUsername(), credentialsVO.getPassword()));
            User user = userRepository.findByUserName(credentialsVO.getUsername());
            String token;

            if (user != null) {
                token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + credentialsVO.getUsername() + " not found!");
            }


            Map<Object, Object> model = new HashMap<>();
            model.put("username", user.getUsername());
            model.put("token", token);

            return ResponseEntity.ok(model);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

}
