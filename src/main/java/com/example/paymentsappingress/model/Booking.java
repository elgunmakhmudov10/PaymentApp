package com.example.paymentsappingress.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "bookings")
@Builder

public class Booking implements Serializable {
    public static final long serialVersionUID = 1234123456123L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String bookingDate;
    String totalCost;
    CourseType courseType;


    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    User user;

    @OneToOne
    @JoinColumn(name = "payment_id")
    @JsonIgnore
    @ToString.Exclude
    Payment payment;

}
