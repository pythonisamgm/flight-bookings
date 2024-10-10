package com.flightbookings.flight_bookings.config;

import com.flightbookings.flight_bookings.repositories.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Configuration class for setting up beans related to authentication and user management.
 */
@Configuration
public class ApplicationConfig {

    private final IUserRepository iUserRepository;

    /**
     * Constructs an ApplicationConfig with the provided user repository.
     *
     * @param iUserRepository the repository for managing user data.
     */
    public ApplicationConfig(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    /**
     * Bean for mapping models.
     *
     * @return a new ModelMapper instance.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Bean for managing authentication.
     *
     * @param configuration the authentication configuration.
     * @return an AuthenticationManager instance.
     * @throws Exception if there is an error during authentication setup.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Bean for providing authentication.
     *
     * @return an AuthenticationProvider instance.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Bean for encoding passwords using BCrypt.
     *
     * @return a BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for loading user details by username.
     *
     * @return a UserDetailsService that loads user data from the repository.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> iUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
