package com.brihaspathee.zeus.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
public class Transaction {

    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "transaction_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID transactionSK;

    @Column(name = "zfcn")
    private String zeusFileControlNumber;

    @Column(name = "interchange_control_number")
    private String interchangeControlNumber;

    @Column(name = "ztcn")
    private String zeusTransactionControlNumber;

    @Column(name = "transaction_control_number")
    private String transactionControlNumber;

    @Column(name = "file_sk")
    private String fileSk;

    @Column(name = "trading_partner_sk")
    private String tradingPartnerSK;

    @Column(name = "trading_partner_id")
    private String tradingPartnerId;

    @Column(name = "line_of_business_type_code")
    private String lineOfBusinessTypeCode;

    @Column(name = "state_type_code")
    private String stateTypeCode;

    @Column(name = "marketplace_type_code")
    private String marketplaceTypeCode;

    @Column(name = "interchange_sender_id")
    private String interchangeSenderId;

    @Column(name = "interchange_receiver_id")
    private String interchangeReceiverId;

    @Column(name = "group_sender_id")
    private String groupSenderId;

    @Column(name = "group_receiver_id")
    private String groupReceiverId;

    @Lob
    @Column(name = "transaction_data")
    private String transactionData;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionSK=" + transactionSK +
                ", zeusFileControlNumber='" + zeusFileControlNumber + '\'' +
                ", interchangeControlNumber='" + interchangeControlNumber + '\'' +
                ", zeusTransactionControlNumber='" + zeusTransactionControlNumber + '\'' +
                ", transactionControlNumber='" + transactionControlNumber + '\'' +
                ", fileSk=" + fileSk +
                ", tradingPartnerSK=" + tradingPartnerSK +
                ", tradingPartnerId='" + tradingPartnerId + '\'' +
                ", lineOfBusinessTypeCode='" + lineOfBusinessTypeCode + '\'' +
                ", stateTypeCode='" + stateTypeCode + '\'' +
                ", marketplaceTypeCode='" + marketplaceTypeCode + '\'' +
                ", interchangeSenderId='" + interchangeSenderId + '\'' +
                ", interchangeReceiverId='" + interchangeReceiverId + '\'' +
                ", groupSenderId='" + groupSenderId + '\'' +
                ", groupReceiverId='" + groupReceiverId + '\'' +
                ", transactionData='" + transactionData + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionSK.equals(that.transactionSK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionSK);
    }
}
