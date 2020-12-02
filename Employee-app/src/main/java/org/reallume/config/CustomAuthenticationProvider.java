package org.reallume.config;

import org.reallume.domain.employee.ActionOfRights;
import org.reallume.domain.employee.Employee;
import org.reallume.repository.employee.EmployeeRepository;
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

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private EmployeeRepository employeeRepository;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String defaultAuthority = "main-page-access";
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        List<GrantedAuthority> authorities;

        Optional<Employee> foundEmployee = employeeRepository.findByLogin(name);

        if (employeeRepository.findByLogin(name).isPresent()) {

            String salt = foundEmployee.get().getSalt();
            String saltPassword = foundEmployee.get().getPassword();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            String authoritiesString = "";
            for (ActionOfRights actionOfRights : foundEmployee.get().getRights().getActionOfRights()) {
                if(actionOfRights.getStatus())
                    authoritiesString += actionOfRights.getAction().getName() + ",";
            }

            authoritiesString = authoritiesString + defaultAuthority;

            authorities = Arrays.stream(authoritiesString.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            if (passwordEncoder.matches(password + salt, saltPassword)) {

                if (foundEmployee.get().getActivity().equals(false))
                    throw new BadCredentialsException("External system authentication failed");
                else
                    return new UsernamePasswordAuthenticationToken(name, password, authorities);

            } else
                throw new BadCredentialsException("External system authentication failed");

        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }



}
