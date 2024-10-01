package com.project.Trading.controller;

import com.project.Trading.config.JwtProvider;
import com.project.Trading.model.User;
import com.project.Trading.repository.UserRepository;
import com.project.Trading.response.AuthResponse;
import com.project.Trading.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {
        User isEmailexist = userRepository.findByEmail(user.getEmail());
        if (isEmailexist != null){
            throw new Exception("Email is already registered") ;
        }
        User newUser =new User();

        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(newUser);

        Authentication authentication =new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()

        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtProvider.generatetoken(authentication);

        AuthResponse res=new AuthResponse() ;
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Register is successful") ;

        return new ResponseEntity<>(res, HttpStatus.CREATED) ;
    }

}
