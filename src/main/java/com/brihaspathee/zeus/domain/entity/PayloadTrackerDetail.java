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
 * Date: 11, October 2022
 * Time: 6:58 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAYLOAD_TRACKER_DETAIL")
public class PayloadTrackerDetail {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "payload_tracker_detail_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID payloadTrackerDetailSK;

    /**
     * The payload tracker that the details are associated
     */
    @ManyToOne
    @JoinColumn(name = "payload_tracker_sk")
    private PayloadTracker payloadTracker;

    /**
     * The type of response e.g. ACK, RESULT etc
     */
    @Column(name = "response_type_code", length = 50, columnDefinition = "varchar", nullable = false)
    private String responseTypeCode;

    /**
     * The response payload data in JSON format
     */
    @Lob
    @Column(name = "response_payload")
    private String responsePayload;

    /**
     * The unique id for the response payload
     */
    @Column(name = "response_payload_id")
    private String responsePayloadId;

    /**
     * The date when the record was created
     */
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    /**
     * The date when the record was updated
     */
    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        return "PayloadTrackerDetail{" +
                "payloadTrackerDetailSK=" + payloadTrackerDetailSK +
                ", payloadTracker=" + payloadTracker +
                ", responseTypeCode='" + responseTypeCode + '\'' +
                ", responsePayload='" + responsePayload + '\'' +
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
        PayloadTrackerDetail that = (PayloadTrackerDetail) o;
        return Objects.equals(payloadTrackerDetailSK, that.payloadTrackerDetailSK);
    }

    /**
     * hashcode method
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(payloadTrackerDetailSK);
    }
}
