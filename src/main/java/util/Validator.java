package util;

import org.mindrot.jbcrypt.BCrypt;

public class Validator {

    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        int atIndex = email.indexOf('@');
        if (atIndex == -1 || atIndex != email.lastIndexOf('@')) {
            return false;
        }

        if (atIndex == 0) {
            return false;
        }

        String domain = email.substring(atIndex + 1);
        int dotIndex = domain.indexOf('.');

        if (dotIndex == -1 || dotIndex == 0 || dotIndex == domain.length() - 1) {
            return false;
        }

        return !email.contains(" ");
    }

    public boolean isValidContactNumber(String contactNumber) {
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            return false;
        }

        for (int i = 0; i < contactNumber.length(); i++) {
            if (!Character.isDigit(contactNumber.charAt(i))) {
                return false;
            }
        }

        return contactNumber.length() == 10;
    }
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    public boolean checkPassword(String plainPassword,String hashedPassword){
        if (plainPassword!=null&&hashedPassword!=null){
            return BCrypt.checkpw(plainPassword,hashedPassword);
        }
        return false;
    }
}
