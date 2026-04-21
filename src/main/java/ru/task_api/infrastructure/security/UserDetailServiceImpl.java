package ru.task_api.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.task_api.application.abstractions.persistence.repositories.UsersRepository;
import ru.task_api.domain.entities.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsersRepository repository;

    public UserDetailServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User not found");

        return org.springframework.security.core.userdetails.User
               .withUsername(username)
               .password(user.password())
               .roles("USER")
               .build();
    
    }
}
