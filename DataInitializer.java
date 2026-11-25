
package com.vehicle.vehicle_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {

        createUser("admin", "admin123", "Admin");

        createUser("staff", "staff123", "Service Staff");

        createUser("customer", "customer123", "Customer");
    }

    private void createUser(String username, String password, String role) {

        if (userRepository.findByUsername(username).isEmpty()) {

            User user = new User();

            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);

            userRepository.save(user);
        }
    }
}
