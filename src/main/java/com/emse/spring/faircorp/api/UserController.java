package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.security.SpringSecurityConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-api/users")

public class UserController {

    @GetMapping
    @Secured(SpringSecurityConfig.ROLE_ADMIN)
    public ResponseEntity<String> getLoggedInUsername(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userDetails.getUsername());

    }
}
