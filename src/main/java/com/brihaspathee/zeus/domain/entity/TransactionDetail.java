package com.brihaspathee.zeus.domain.entity;

import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import jakarta.persistence.*;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11, June 2022
 * Time: 6:01 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Transaction")
public class TransactionDetail {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "transaction_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID transactionSK;

    /**
     * The functianal group that the transaction is associated with
     */
    @ManyToOne
    @JoinColumn(name = "functional_group_sk")
    private FunctionalGroupDetail functionalGroup;

    /**
     * The unique control number created for the transaction. This is created by the transaction origination
     * service and can be used to track the transaction across services. This is a unique id that will not be
     * repeated for any transactions
     */
    @Column(name = "ztcn")
    private String zeusTransactionControlNumber;

    /**
     * The source of the data
     */
    @Column(name = "source", length = 50, columnDefinition = "varchar", nullable = false)
    private String source;

    /**
     * The transaction control number received for the transaction in the file
     */
    @Column(name = "transaction_control_number")
    private String transactionControlNumber;

    /**
     * The transaction data in JSON format
     */
    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "transaction_data")
    private String transactionData;

    /**
     * The date the record was created
     */
    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    /**
     * The date the record was updated
     */
    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    /**
     * The toString method
     * @return
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionSK=" + transactionSK +
                ", functionalGroup=" + functionalGroup +
                ", zeusTransactionControlNumber='" + zeusTransactionControlNumber + '\'' +
                ", transactionControlNumber='" + transactionControlNumber + '\'' +
                ", transactionData='" + transactionData + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }

    /**
     * The equals method
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDetail that = (TransactionDetail) o;
        return transactionSK.equals(that.transactionSK);
    }

    /**
     * The hash code method
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(transactionSK);
    }
}
