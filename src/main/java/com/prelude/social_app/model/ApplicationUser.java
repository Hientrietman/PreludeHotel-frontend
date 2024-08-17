package com.prelude.social_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.cdi.Eager;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(unique = true, nullable = false)
    String username;
    @JsonIgnore
    String password;
    String firstName;
    String lastName;
    @Column(unique = true)
    String email;
    String phone;
    Date dob;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")}, // Trỏ đến khóa chính của bảng User
            inverseJoinColumns = {@JoinColumn(name = "role_id")} // Trỏ đến khóa chính của bảng Role
    )
    private Set<Roles> authorities;

    public ApplicationUser() {
        authorities = new HashSet<>();
    }
}
