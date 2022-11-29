package com.example.demo.model.product;

import com.example.demo.model.Transaction;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "bill")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter @Setter
public class Bill {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String productCode;
    @Column(nullable = false)
    private String billingCode;
    @Column(nullable = false)
    private String billingName;
    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
    @Builder.Default
    private Boolean isPaid = false;
    @Column(name = "trans_id")
    private String transId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trans_id", referencedColumnName = "trans_id",
            updatable = false, insertable = false)
    private Transaction transaction;


}
