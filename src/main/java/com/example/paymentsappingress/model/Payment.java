package com.example.paymentsappingress.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payments")
@Builder

public class Payment implements Serializable {

    public static final long serialVersionUID = 1234123456123L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double paymentAmount;

    String paymentMethod;

    String paymentDate;
    @OneToOne(mappedBy = "payment")
    Booking booking;
}