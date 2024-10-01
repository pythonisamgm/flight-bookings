package com.flightbookings.flight_bookings.config;


import com.flightbookings.flight_bookings.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final AuthTokenFilter authTokenFilter;

    public WebSecurityConfig(AuthenticationProvider authenticationProvider, AuthTokenFilter authTokenFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authTokenFilter = authTokenFilter;
    }
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

                                .requestMatchers("/api/v1/bookings/create").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/api/v1/bookings/{id}").permitAll()
                                .requestMatchers("/api/v1/bookings/create2").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/v1/bookings/{id}").permitAll()
                                .requestMatchers("/api/v1/bookings/").permitAll()
                                .requestMatchers("/api/v1/bookings/update/{id}").permitAll()
                                .requestMatchers("/api/v1/bookings/delete/{id}").permitAll()

                                .requestMatchers("api/v1/flight/create").permitAll()
                                .requestMatchers("api/v1/flight/{id}").permitAll()
                                .requestMatchers("api/v1/flight/").permitAll()
                                .requestMatchers("api/v1/flight/update/{id}").permitAll()
                                .requestMatchers("api/v1/flight/delete/{id}").permitAll()
                                .requestMatchers("api/v1/flight/{id}/cancel").permitAll()
                                .requestMatchers("api/v1/flight/{id}/delay").permitAll()
                                .requestMatchers("api/v1/flight/updateAvailability").permitAll()
                                .requestMatchers("api/v1/flight/byAirplaneType").permitAll()

                                .requestMatchers("/api/v1/passengers/create").permitAll()
                                .requestMatchers("/api/v1/passengers/{id}").permitAll()
                                .requestMatchers("/api/v1/passengers").permitAll()
                                .requestMatchers("/api/v1/passengers/update/{id}").permitAll()
                                .requestMatchers("/api/v1/passengers/delete/{id}").permitAll()

                                .requestMatchers("api/v1/user/create").permitAll()
                                .requestMatchers("api/v1/user/{id}").permitAll()
                                .requestMatchers("api/v1/user/").permitAll()
                                .requestMatchers("api/v1/user/update/{id}").permitAll()
                                .requestMatchers("api/v1/user/delete/{id}").permitAll()

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