package com.example.demo.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Accessors(chain = true)
public class Transaction {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String type;
    @Column(name = "partner_code", nullable = false)
    private String partnerCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_code", referencedColumnName = "partner_code",
            updatable = false, insertable = false)
    private Partner partner;
    @Column(name = "trans_id", nullable = false, unique = true, updatable = false)
    private String transId;
    @Column(nullable = false)
    private int status;
}
