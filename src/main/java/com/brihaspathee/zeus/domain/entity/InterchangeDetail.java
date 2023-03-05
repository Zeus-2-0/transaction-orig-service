package com.brihaspathee.zeus.domain.entity;

import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import jakarta.persistence.*;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 29, June 2022
 * Time: 2:18 PM
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
@Table(name = "Interchange")
public class InterchangeDetail {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "interchange_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID interchangeSK;

    /**
     * The primary key of the file that was created in the file management service
     */
    @Column(name = "file_sk")
    private String fileSK;

    /**
     * The zeus file control number generated for the file by file management service
     */
    @Column(name = "zfcn")
    private String zeusFileControlNumber;

    /**
     * The ICN received in the file
     */
    @Column(name = "interchange_control_number")
    private String interchangeControlNumber;

    /**
     * The date when the interchange envelope was created
     */
    @Column(name = "interchange_date")
    private String interchangeDate;

    /**
     * The time when the interchange envelope was created
     */
    @Column(name = "interchange_time")
    private String interchangeTime;

    /**
     * The interchange sender id received in the file
     */
    @Column(name = "interchange_sender_id")
    private String interchangeSenderId;

    /**
     * The interchange receiver id received in the file
     */
    @Column(name = "interchange_receiver_id")
    private String interchangeReceiverId;

    /**
     * The id of the trading partner from which the file was received
     */
    @Column(name = "trading_partner_id")
    private String tradingPartnerId;

    /**
     * The line of business type code associated with the file
     */
    @Column(name = "line_of_business_type_code")
    private String lineOfBusinessTypeCode;

    /**
     * The marketplace type code associated with the file
     */
    @Column(name = "marketplace_type_code")
    private String marketplaceTypeCode;

    /**
     * The state type code associated with the file
     */
    @Column(name = "state_type_code")
    private String stateTypeCode;

    /**
     * Total number of functional groups received in the file
     */
    @Column(name = "number_of_functional_groups")
    private Integer numberOfFunctionalGroups;

    /**
     * The set of functional groups associated with the interchange
     */
    @OneToMany(mappedBy = "interchange")
    private Set<FunctionalGroupDetail> functionalGroupSet;

    /**
     * Date and time when the record was created
     */
    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    /**
     * Date and time when the record was updated
     */
    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        return "Interchange{" +
                "interchangeSK=" + interchangeSK +
                ", file_sk='" + fileSK + '\'' +
                ", zeusFileControlNumber='" + zeusFileControlNumber + '\'' +
                ", interchangeControlNumber='" + interchangeControlNumber + '\'' +
                ", interchangeDate='" + interchangeDate + '\'' +
                ", interchangeTime='" + interchangeTime + '\'' +
                ", interchangeSenderId='" + interchangeSenderId + '\'' +
                ", interchangeReceiverId='" + interchangeReceiverId + '\'' +
                ", tradingPartnerId='" + tradingPartnerId + '\'' +
                ", lineOfBusinessTypeCode='" + lineOfBusinessTypeCode + '\'' +
                ", marketplaceTypeCode='" + marketplaceTypeCode + '\'' +
                ", stateTypeCode='" + stateTypeCode + '\'' +
                ", functionalGroupSet=" + functionalGroupSet +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }

    /**
     * equals method
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterchangeDetail that = (InterchangeDetail) o;
        return Objects.equals(interchangeSK, that.interchangeSK);
    }

    /**
     * hashcode method
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(interchangeSK);
    }
}
