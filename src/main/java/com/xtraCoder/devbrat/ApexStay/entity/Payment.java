package com.xtraCoder.devbrat.ApexStay.entity;

import com.xtraCoder.devbrat.ApexStay.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionId; // Unique transaction identifier

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(nullable = false, precision = 10, scale = 2) // e.g., 99999999.99
    private BigDecimal amount; // Payment amount


}
