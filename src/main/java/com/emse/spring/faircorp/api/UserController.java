package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.security.SpringSecurityConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apii/users")

public class UserController {

    @GetMapping
//    @Secured(SpringSecurityConfig.ROLE_ADMIN) //CURRENTLY FAILING. REASON UNKNOWN
    public ResponseEntity<String> getLoggedInUsername(@AuthenticationPrincipal UserDetails userDetails){
//    public ResponseEntity<String> getLoggedInUsername(){
        return ResponseEntity.ok(userDetails.getUsername());

    }

//    public ResponseEntity<String> getLoggedInUsername(){
//
//        //ALTERNATIVE, INSTEAD OF THE PARAMETER ANNOTATIONS
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        return ResponseEntity.ok(currentPrincipalName);
//
//    }
}
