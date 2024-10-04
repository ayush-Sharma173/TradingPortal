package com.project.Trading.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.Trading.domain.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;
    private String mobile;
    //when we fetch user from client side password should be ignored
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //password is only writable

    private String password;

//    private UserStatus status= UserStatus.PENDING;

    private boolean isVerified = false;

    @Embedded
    private TwoFactorAuth twoFactorAuth=new TwoFactorAuth();


    private UserRole role= UserRole.USER ;//default =customer


}
