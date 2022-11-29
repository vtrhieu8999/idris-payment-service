package com.example.demo.model.product;

import com.example.demo.model.Transaction;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "card")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter @Setter
public class Card {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "card_config_id", nullable = false)
    private Long cardConfigId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_config_id", referencedColumnName = "id",
            insertable = false, updatable = false)
    private CardDetails cardDetails;
    @Column(unique = true, nullable = false)
    private String serial;
    @Column(nullable = false)
    private String pinCode;
    @Column(nullable = false)
    private Date expireDate;
    @Column(nullable = false)
    @Builder.Default
    private Boolean isAvailable = true;
    @Column(name = "trans_id")
    private String transId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trans_id", referencedColumnName = "trans_id",
            insertable = false, updatable = false)
    private Transaction transaction;
}
