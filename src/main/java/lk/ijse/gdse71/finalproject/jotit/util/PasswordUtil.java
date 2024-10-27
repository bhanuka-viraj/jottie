package lk.ijse.gdse71.finalproject.jotit.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hashPassword(String plainPassword) {
        String genSalt = BCrypt.gensalt();
        System.out.println(genSalt);
        return BCrypt.hashpw(plainPassword, genSalt);
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
