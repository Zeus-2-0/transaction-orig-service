package com.brihaspathee.zeus.domain.repository;

import com.brihaspathee.zeus.domain.entity.InterchangeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 29, June 2022
 * Time: 3:55 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.domain.repository
 * To change this template use File | Settings | File and Code Template
 */
@Repository
public interface InterchangeDetailRepository extends JpaRepository<InterchangeDetail, UUID> {

    /**
     * Find the data using ICN
     * @param icn
     * @return
     */
    Optional<InterchangeDetail> findByInterchangeControlNumber(String icn);
}
