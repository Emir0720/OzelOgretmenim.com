package com.example.SoftwareEngineeringProject.Controller;

import com.example.SoftwareEngineeringProject.Exception.IdNotFoundException;
import com.example.SoftwareEngineeringProject.Repository.UserRepository;
import com.example.SoftwareEngineeringProject.Request.LoginRequest;
import com.example.SoftwareEngineeringProject.Service.SecurityRoleRequestService;
import com.example.SoftwareEngineeringProject.Service.SecurityUser;
import com.example.SoftwareEngineeringProject.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final SecurityUser securityUser;

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    private final SecurityRoleRequestService securityRoleRequestService;

    public UserController(UserService userService, UserRepository userRepository, SecurityUser securityUser, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, SecurityRoleRequestService securityRoleRequestService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.securityUser = securityUser;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.securityRoleRequestService = securityRoleRequestService;
    }


    @GetMapping("/userinfo")
    public ResponseEntity<String> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
        String username = authentication.getName();
        return ResponseEntity.ok(username);
    }



    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String role =  authentication.getAuthorities().toString();
            return ResponseEntity.ok(userDetails.getUsername()+" "+userDetails.getAuthorities().toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() throws IdNotFoundException {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication != null){
                    SecurityContextHolder.clearContext();
            }

            else {
                throw new IdNotFoundException("Account Not Logged In");
            }

            return  ResponseEntity.status(HttpStatus.OK).body("User Logged Out Successfuly");

    }




}
