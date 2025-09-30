package com.xtraCoder.devbrat.ApexStay.entity;

import com.xtraCoder.devbrat.ApexStay.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Many guests can be associated with one booking
    @JoinColumn(name = "user_id")// Foreign key to User entity
    private User user;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

    @ManyToMany(mappedBy = "guests") // Inverse side of the many-to-many relationship with Booking
    private Set<Booking> bookings; // A guest can have multiple bookings
}
