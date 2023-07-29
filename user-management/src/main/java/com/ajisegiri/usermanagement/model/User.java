package com.ajisegiri.usermanagement.model;


import com.ajisegiri.usermanagement.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Data
@Table(name = "_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable=false)
    private String email;
    @Column(unique = true,nullable = false,updatable = false)
    private String fireBaseUid;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String password;
    private String imageUrl;
    private String contactNumber;
    @OneToOne(cascade =CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_address_id")
    private Address address;
    @Enumerated(EnumType.STRING)
    private Role role;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Date creationDate;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Time time;


}
