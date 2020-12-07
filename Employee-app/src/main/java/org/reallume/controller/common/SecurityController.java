package org.reallume.controller.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServlet;
import java.util.UUID;

@Controller
public class SecurityController extends HttpServlet {

    @GetMapping(value = "/login")
    public String loginPage(Model model) {
        return "common/login-page";
    }

    @GetMapping(value = "/access-error")
    public String accessErrorPage(Model model) {
        return "common/access-denied-page";
    }

    public static String generateLogin(){
        String login = UUID.randomUUID().toString();
        login = login.substring(0, login.length() - 25);
        login = login.replace("-", "");
        return login;
    }

    public static String generatePassword(){
        String password = UUID.randomUUID().toString();
        password = password.substring(0, password.length() - 15);
        password = password.replace("-", "");
        return password;
    }

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
