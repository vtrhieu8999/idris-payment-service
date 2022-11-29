package com.example.demo.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "partner")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "partner_code", unique = true, nullable = false, updatable = false)
    private String partnerCode;

    @Column(nullable = false, updatable = false)
    private String partnerPublicKey;

    @Column(nullable = false, updatable = false)
    private String privateKey;

    @Column(nullable = false, updatable = false)
    private String publicKey;

    @Column
    private long balance;
}
