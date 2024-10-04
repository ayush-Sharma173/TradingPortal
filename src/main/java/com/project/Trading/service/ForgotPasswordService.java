package com.project.Trading.service;

import com.project.Trading.domain.VerificationType;
import com.project.Trading.model.ForgotPasswordToken;
import com.project.Trading.model.User;


public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user, String id, String otp,
                                    VerificationType verificationType,String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);

    boolean verifyToken(ForgotPasswordToken token,String otp);
}

