package br.com.rafaelbiasi.blog.core.domain.service.impl;

import br.com.rafaelbiasi.blog.core.domain.model.RegistrationResponse;
import br.com.rafaelbiasi.blog.core.domain.model.User;
import br.com.rafaelbiasi.blog.core.domain.repository.UserRepository;
import br.com.rafaelbiasi.blog.core.domain.service.RoleService;
import br.com.rafaelbiasi.blog.core.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.of;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public Optional<User> findById(final long id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(final User user) {
        userRepository.delete(user);
    }

    @Override
    public User save(final User user) {
        requireNonNull(user, "The User has a null value.");
        of(user).filter(User::isNew)
                .filter(User::hasNoHoles)
                .ifPresent(acc -> roleService
                        .findByName("ROLE_USER")
                        .map(Collections::singleton)
                        .ifPresent(acc::setRoles)
                );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        requireNonNull(pageable, "The Pageable has a null value.");
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findOneByEmail(final String email) {
        requireNonNull(email, "The E-mail has a null value.");
        return userRepository.findOneByEmailIgnoreCase(email);
    }

    @Override
    public Optional<User> findOneByUsername(final String username) {
        requireNonNull(username, "The Username has a null value.");
        return userRepository.findOneByUsernameIgnoreCase(username);
    }

    @Override
    public void registerUser(final User user) {
        requireNonNull(user, "The User has a null value.");
        save(user);
    }

    @Override
    public RegistrationResponse checkEmailAndUsernameExists(final User user) {
        return RegistrationResponse.builder()
                .emailExists(isEmailExists(user))
                .usernameExists(isUsernameExists(user))
                .build();
    }

    private boolean isUsernameExists(final User user) {
        return userRepository.findOneByUsernameIgnoreCase(user.getUsername()).isPresent();
    }

    private boolean isEmailExists(final User user) {
        return userRepository.findOneByEmailIgnoreCase(user.getEmail()).isPresent();
    }
}
