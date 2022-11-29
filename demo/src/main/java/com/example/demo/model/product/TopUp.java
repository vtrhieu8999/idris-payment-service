package com.example.demo.model.product;

import com.example.demo.model.Transaction;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "top_up")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter @Setter
public class TopUp {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "telco_code", nullable = false)
    private String telcoCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "telco_code", referencedColumnName = "telco_code",
            updatable = false, insertable = false)
    private Company company;
    @Column(nullable = false)
    private String mobileNo;
    @Column(nullable = false)
    private Integer amount;
    @Column(name = "trans_id", unique = true, nullable = false, updatable = false)
    private String transId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trans_id", referencedColumnName = "trans_id",
            updatable = false, insertable = false)
    private Transaction transaction;
}
