import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    // Метод для хеширования пароля
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // Метод для проверки пароля
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}