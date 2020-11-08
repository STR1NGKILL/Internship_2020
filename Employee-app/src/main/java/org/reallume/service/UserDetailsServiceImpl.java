package org.reallume.service;

import org.reallume.domain.Employee;
import org.reallume.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Employee> user = employeeRepository.findByLogin(username);

        user.orElseThrow(() -> new UsernameNotFoundException(username + " не найден!"));

        return user.map(UserDetailsImpl::new).get();
    }
}
