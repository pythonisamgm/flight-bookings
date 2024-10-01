package com.flightbookings.flight_bookings.config;


import com.flightbookings.flight_bookings.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up web security and authentication filters.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthTokenFilter authTokenFilter;
    /**
     * Constructs a WebSecurityConfig with the required authentication provider and token filter.
     *
     * @param authenticationProvider the authentication provider.
     * @param authTokenFilter        the JWT authentication filter.
     */
    public WebSecurityConfig(AuthenticationProvider authenticationProvider, AuthTokenFilter authTokenFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authTokenFilter = authTokenFilter;
    }

    /**
     * Configures the security filter chain for handling HTTP requests.
     *
     * @param http the HttpSecurity object for configuring security rules.
     * @return a configured SecurityFilterChain instance.
     * @throws Exception if there is an error during security configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf ->
                        csrf.disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/api/v1/auth/register").permitAll()
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers("/getflight").permitAll()
                                .requestMatchers("/api/v1/passengers/create").permitAll()
                                .requestMatchers("/api/v1/passengers/{id}").permitAll()
                                .requestMatchers("/api/v1/passengers").permitAll()
                                .requestMatchers("/api/v1/passengers/update/{id}").permitAll()
                                .requestMatchers("/api/v1/passengers/delete/{id}").permitAll()
                                .requestMatchers("/api/v1/flight/create").permitAll()
                                .requestMatchers("/api/v1/flight/{id}").permitAll()
                                .requestMatchers("/api/v1/flight/").permitAll()
                                .requestMatchers("/api/v1/flight/update/{id}").permitAll()
                                .requestMatchers("/api/v1/flight/delete/{id}").permitAll()

                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}