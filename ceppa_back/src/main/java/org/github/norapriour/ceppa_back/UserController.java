package org.github.norapriour.ceppa_back;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final KeycloakAdminService keycloakAdminService;

    public UserController(UserRepository userRepository, KeycloakAdminService keycloakAdminService) {
        this.userRepository = userRepository;
        this.keycloakAdminService = keycloakAdminService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.findById(id)
                .map(User::getKeycloakId)
                .ifPresent(keycloakAdminService::deleteUser);
        userRepository.deleteById(id);
    }

    @PostMapping
    public User addUser(@RequestBody CreateUserRequest newUser) {
        String keycloakId = keycloakAdminService.createUser(newUser);
        return userRepository.save(newUser, keycloakId);
    }

    @PatchMapping("/{id}/keycloak")
    public User linkKeycloakId(@PathVariable Long id, @RequestBody User user) {
        return userRepository.linkKeycloakId(id, user.getKeycloakId());
    }
}
