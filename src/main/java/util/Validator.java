package util;

public class Validator {
    public static boolean isValidEmail(String email) {

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


        if (email.contains(" ")) {
            return false;
        }

        return true;

//    halidha@example.com
    }
    public static boolean isValidContactNumber(String contactNumber) {

        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            return false;
        }


        for (int i = 0; i < contactNumber.length(); i++) {
            if (!Character.isDigit(contactNumber.charAt(i))) {
                return false;
            }
        }


        if (contactNumber.length() != 10) {
            return false;
        }

        return true;
    }


}
