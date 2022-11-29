package com.example.demo.model.product;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter @Setter
public class Payment {
    @Id
    private String serviceCode;
    @Column(name = "provider", nullable = false)
    private String provider;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider", referencedColumnName = "name",
    updatable = false, insertable = false)
    private Company company;
    @Column(name = "service_name", nullable = false)
    private String serviceName;
}
