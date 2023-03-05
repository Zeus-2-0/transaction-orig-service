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
 * Time: 2:34 PM
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
@Table(name = "Functional_Group")
public class FunctionalGroupDetail {

    /**
     * The primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "functional_group_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID functionalGroupSK;

    /**
     * The interchange that the functional group is part of.
     */
    @ManyToOne
    @JoinColumn(name = "interchange_sk")
    private InterchangeDetail interchange;

    /**
     * Receiver id present in the functional group segment
     */
    @Column(name = "group_receiver_id")
    private String groupReceiverId;

    /**
     * Sender id present in the functional group segment
     */
    @Column(name = "group_sender_id")
    private String groupSenderId;

    /**
     * The group control number.
     * It is unique for all the groups within the file
     */
    @Column(name = "group_control_number")
    private String groupControlNumber;

    /**
     * The date with the functional group was created
     */
    @Column(name = "group_creation_date")
    private String groupCreationDate;

    /**
     * The time when the functional group was created
     */
    @Column(name = "group_creation_time")
    private String groupCreationTime;

    /**
     * Total number of functional groups received in the file
     */
    @Column(name = "number_of_transactions")
    private Integer numberOfTransactions;

    /**
     * The set of transactions present in the functional group
     */
    @OneToMany(mappedBy = "functionalGroup")
    private Set<TransactionDetail> transactions;

    /**
     * The date when the record was created
     */
    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    /**
     * The date when the record was updated
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
        return "FunctionalGroup{" +
                "functionalGroupSK=" + functionalGroupSK +
                ", groupReceiverId='" + groupReceiverId + '\'' +
                ", groupSenderId='" + groupSenderId + '\'' +
                ", groupControlNumber='" + groupControlNumber + '\'' +
                ", groupCreationDate='" + groupCreationDate + '\'' +
                ", groupCreationTime='" + groupCreationTime + '\'' +
                ", transactions=" + transactions +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }

    /**
     * the equals method
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionalGroupDetail that = (FunctionalGroupDetail) o;
        return functionalGroupSK.equals(that.functionalGroupSK);
    }

    /**
     * the hashcode method
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(functionalGroupSK);
    }
}
