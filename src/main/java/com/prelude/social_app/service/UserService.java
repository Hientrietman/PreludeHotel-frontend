package com.prelude.social_app.service;

import com.prelude.social_app.model.ApplicationUser;
import com.prelude.social_app.model.Roles;
import com.prelude.social_app.repository.RoleRepository;
import com.prelude.social_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ApplicationUser register(ApplicationUser user) {
        Set<Roles> roles = user.getAuthorities();
        roles.add(roleRepository.findByAuthority("USER").get());
        user.setAuthorities(roles);
        return userRepository.save(user);
    }
}
