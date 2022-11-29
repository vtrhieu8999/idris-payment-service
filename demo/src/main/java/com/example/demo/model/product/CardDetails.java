package com.example.demo.model.product;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "card_config")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter @Setter
public class CardDetails {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Integer priceTag;
    @Column(nullable = false)
    private Long value;
    @Column(nullable = false)
    private Float discountPercent;
    @Column(name = "telco_code", nullable = false)
    private String telcoCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "telco_code", referencedColumnName = "telco_code",
            insertable = false, updatable = false)
    private Company company;
}
