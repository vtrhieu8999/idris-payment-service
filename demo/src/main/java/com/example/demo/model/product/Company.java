package com.example.demo.model.product;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "company")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter @Setter
public class Company {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "name", unique = true, updatable = false, nullable = false)
    private String name;
    @Column(name = "telco_code", unique = true, updatable = false, nullable = false)
    private String telcoCode;
}
