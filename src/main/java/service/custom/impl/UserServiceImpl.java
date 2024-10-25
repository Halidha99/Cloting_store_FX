package service.custom.impl;

import org.mindrot.jbcrypt.BCrypt;
import service.custom.UserService;
import util.EmailUtil;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private final Map<String, String> otpStorage = new HashMap<>();
    @Override
    public boolean sendOTPEmail(String email, String otp) {

        return EmailUtil.sendOTPEmail(email, otp);
    }

    @Override
    public void storeOTP(String email, String otp) {
        otpStorage.put(email, otp);
        System.out.println("Stored OTP: " + otp + " for email: " + email);
    }


    @Override
    public boolean validateOTP(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        System.out.println("Validating OTP for email: " + email);
        System.out.println("Entered OTP: " + otp);
        System.out.println("Stored OTP: " + storedOtp);
        return storedOtp != null && storedOtp.equals(otp);
    }


    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }


    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }


    public boolean checkPassword(String plainPassword, String hashedPassword) {
        System.out.println("Plain Password: " + plainPassword);
        System.out.println("Hashed Password: " + hashedPassword);
        if (plainPassword == null || hashedPassword == null) {
            throw new IllegalArgumentException("Passwords cannot be null");
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }


    private boolean authenticate(String password, String storedPassword) {
        if (storedPassword == null) {
            System.out.println("Stored password is null");
            return false;
        }
        return checkPassword(password, storedPassword);
    }
}

