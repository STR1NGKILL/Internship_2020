package org.reallume.controller;

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
