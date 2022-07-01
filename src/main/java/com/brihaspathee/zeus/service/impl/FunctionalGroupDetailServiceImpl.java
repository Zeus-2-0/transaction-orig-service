package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.domain.entity.FunctionalGroupDetail;
import com.brihaspathee.zeus.domain.entity.InterchangeDetail;
import com.brihaspathee.zeus.domain.repository.FunctionalGroupDetailRepository;
import com.brihaspathee.zeus.edi.models.common.FunctionalGroup;
import com.brihaspathee.zeus.service.interfaces.FunctionalGroupDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, July 2022
 * Time: 8:00 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FunctionalGroupDetailServiceImpl implements FunctionalGroupDetailService {

    /**
     * The repository to save the functional group
     */
    private final FunctionalGroupDetailRepository repository;

    /**
     * Takes the interchange detail that the functional groups belongs to and saves it
     * @param interchangeDetail
     * @param functionalGroup
     * @return
     */
    @Override
    public FunctionalGroupDetail saveFunctionalGroupDetail(InterchangeDetail interchangeDetail,
                                                           FunctionalGroup functionalGroup) {
        FunctionalGroupDetail functionalGroupDetail = FunctionalGroupDetail.builder()
                .interchange(interchangeDetail)
                .groupSenderId(functionalGroup.getGs02())
                .groupReceiverId(functionalGroup.getGs03())
                .groupCreationDate(functionalGroup.getGs04())
                .groupCreationTime(functionalGroup.getGs05())
                .groupControlNumber(functionalGroup.getGs06())
                .numberOfTransactions(Integer.parseInt(functionalGroup.getGe01()))
                .build();
        repository.save(functionalGroupDetail);
        return functionalGroupDetail;
    }
}
