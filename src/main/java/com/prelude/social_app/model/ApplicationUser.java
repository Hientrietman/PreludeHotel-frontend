package com.prelude.social_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.format.annotation.NumberFormat;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")

@Data
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true, nullable = false)
    private String username;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    @Column(unique = true)

    private String email;
    @Column(nullable = true)
    private boolean enabled;
    private String verificationCode;
    private String phone;
    private Date dob;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")}, // Trỏ đến khóa chính của bảng User
            inverseJoinColumns = {@JoinColumn(name = "role_id")} // Trỏ đến khóa chính của bảng Role
    )
    private Set<Roles> authorities;

    public ApplicationUser() {
        this.authorities = new HashSet<>();
        this.enabled = false;
    }
}
