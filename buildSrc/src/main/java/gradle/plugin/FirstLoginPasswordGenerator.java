package gradle.plugin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class FirstLoginPasswordGenerator {

    public static String generateSalt(){
        String salt = UUID.randomUUID().toString();
        salt = salt.substring(0, salt.length() - 20);
        salt = salt.replace("-", "");
        return salt;
    }

    public static String getSaltPassword(String password, String salt) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password + salt);
    }

}
