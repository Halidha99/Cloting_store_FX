package service.custom;

import service.SuperService;

public interface UserService extends SuperService {
    boolean sendOTPEmail(String email, String otp);
    void storeOTP(String email, String otp);
    boolean validateOTP(String email, String otp);
}
