package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.domain.entity.InterchangeDetail;
import com.brihaspathee.zeus.domain.repository.InterchangeDetailRepository;
import com.brihaspathee.zeus.dto.transaction.FileDetailDto;
import com.brihaspathee.zeus.edi.models.common.Interchange;
import com.brihaspathee.zeus.service.interfaces.InterchangeDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 29, June 2022
 * Time: 3:59 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InterchangeDetailServiceImpl implements InterchangeDetailService {

    /**
     * The repository to perform CRUD operations
     */
    private final InterchangeDetailRepository repository;

    /**
     * Saves the interchange detail
     * @param interchange
     * @return
     */
    @Override
    public InterchangeDetail saveInterchangeDetail(FileDetailDto fileDetailDto, Interchange interchange) {
        InterchangeDetail interchangeDetail = InterchangeDetail.builder()
                .fileSK(fileDetailDto.getFileDetailSK().toString())
                .zeusFileControlNumber(fileDetailDto.getZeusFileControlNumber())
                .interchangeControlNumber(interchange.getIsa13())
                .interchangeDate(interchange.getIsa09())
                .interchangeTime(interchange.getIsa10())
                .interchangeSenderId(interchange.getIsa06())
                .interchangeReceiverId(interchange.getIsa07())
                .tradingPartnerId(fileDetailDto.getTradingPartnerId())
                .lineOfBusinessTypeCode(fileDetailDto.getLineOfBusinessTypeCode())
                .stateTypeCode(fileDetailDto.getStateTypeCode())
                .marketplaceTypeCode(fileDetailDto.getMarketplaceTypeCode())
                .numberOfFunctionalGroups(Integer.parseInt(interchange.getIea01()))
                .build();
        interchangeDetail = repository.save(interchangeDetail);
        return interchangeDetail;
    }
}
