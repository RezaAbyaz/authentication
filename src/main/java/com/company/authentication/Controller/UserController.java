package com.company.authentication.Controller;

import com.company.authentication.common.JwtRequestModel;
import com.company.authentication.common.JwtResponseModel;
import com.company.authentication.common.TokenManager;
import com.company.authentication.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userDetailsService;
    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseModel> createToken(@RequestBody JwtRequestModel request) throws Exception {
        String jwtToken = "";
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            jwtToken = tokenManager.generateJwtToken(userDetails);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        return ResponseEntity.ok(new JwtResponseModel(jwtToken));
    }


    @GetMapping
    public String home(){
        return "Hello, World";
    }

    @GetMapping("/user")
    public String user(){
        return "Hello, User";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Hello, Admin";
    }

}
