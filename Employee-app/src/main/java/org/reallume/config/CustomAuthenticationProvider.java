package org.reallume.config;

import org.reallume.domain.ActionOfRights;
import org.reallume.domain.Employee;
import org.reallume.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.reallume.controller.SecurityController.getSaltPassword;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<GrantedAuthority> authorities;

        Optional<Employee> foundEmployee = employeeRepository.findByLogin(name);

        if (employeeRepository.findByLogin(name).isPresent()) {

            String salt = foundEmployee.get().getSalt();
            String saltPassword = getSaltPassword(password, salt);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            String authoritiesString = "";
            for (ActionOfRights actionOfRights : foundEmployee.get().getRights().getActionOfRights()) {
                if(actionOfRights.getStatus())
                    authoritiesString += actionOfRights.getAction().getName() + ",";
            }

            authoritiesString = authoritiesString.substring(0, authoritiesString.length() - 1);

            authorities = Arrays.stream(authoritiesString.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            if (passwordEncoder.matches(password + salt, saltPassword))
                return new UsernamePasswordAuthenticationToken(name, password, authorities);
            else
                throw new BadCredentialsException("External system authentication failed");
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
