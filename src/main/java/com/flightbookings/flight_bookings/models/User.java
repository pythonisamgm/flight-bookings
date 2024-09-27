package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @OneToMany(mappedBy= "user", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value="user-booking")
    private List<Booking> bookings;


    public User(Long id, String username, String password, String email, ERole role, List<Booking> bookings) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.bookings = bookings;
    }

    public User() {
    }

    public Long getId() {
        return id;}

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private ERole role;
        private List<Booking> bookings;


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(ERole role) {
            this.role = role;
            return this;
        }

        public Builder bookings(List<Booking> bookings) {
            this.bookings = bookings;
            return this;
        }

        public Builder projectsList(List<Booking> projectsList) {
            this.bookings = bookings;
            return this;
        }

        public User build() {
            return new User(id, username, password, email, role, bookings);

        }
    }
}
