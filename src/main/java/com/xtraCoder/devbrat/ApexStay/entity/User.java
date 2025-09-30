package com.xtraCoder.devbrat.ApexStay.entity;

import com.xtraCoder.devbrat.ApexStay.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name="app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER) // Eagerly load roles with the user
    @Enumerated(EnumType.STRING)
    private Set<Role> role;


}
