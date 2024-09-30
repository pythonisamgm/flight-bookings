package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Schema(description = "All details about the User entity.")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The database generated user ID")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "The username of the user")
    private String username;

    @Column
    @Schema(description = "The password of the user")
    private String password;

    @Column
    @Schema(description = "The email of the user")
    private String email;

    @Enumerated(EnumType.STRING)
    @Schema(description = "The role of the user")
    private ERole role;

    @OneToMany(mappedBy= "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value="user-booking")
    @Schema(description = "The list of bookings made by the user")
    private List<Booking> bookings;

    public User() {
    }

    public User(Long id, String username, String password, String email, ERole role, List<Booking> bookings) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.bookings = bookings;
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
    @JsonIgnore
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

        public Builder bookingList(List<Booking> BookingList) {
            this.bookings = bookings;
            return this;
        }

        public User build() {
            return new User(id, username, password, email, role, bookings);

        }
    }
}
