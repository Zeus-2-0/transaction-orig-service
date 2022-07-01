package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.domain.entity.FunctionalGroupDetail;
import com.brihaspathee.zeus.domain.entity.InterchangeDetail;
import com.brihaspathee.zeus.domain.repository.TransactionDetailRepository;
import com.brihaspathee.zeus.edi.models.common.FunctionalGroup;
import com.brihaspathee.zeus.edi.models.common.Interchange;
import com.brihaspathee.zeus.edi.models.enrollment.Transaction;
import com.brihaspathee.zeus.helper.interfaces.EDIFileDataHelper;
import com.brihaspathee.zeus.service.interfaces.FileDataProcessingService;
import com.brihaspathee.zeus.service.interfaces.FunctionalGroupDetailService;
import com.brihaspathee.zeus.service.interfaces.InterchangeDetailService;
import com.brihaspathee.zeus.service.interfaces.TransactionDetailService;
import com.brihaspathee.zeus.web.model.FileDetailDto;
import com.brihaspathee.zeus.web.model.FileResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, April 2022
 * Time: 2:06 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileDataProcessingServiceImpl implements FileDataProcessingService {

    /**
     * The EDIFileDataHelper class will parse the file information from the String and create the
     * Interchange Object
     */
    private final EDIFileDataHelper ediFileDataHelper;

    /**
     * The service that will save the interchange details
     */
    private final InterchangeDetailService interchangeDetailService;

    /**
     * The service that will save the functional group details
     */
    private final FunctionalGroupDetailService functionalGroupDetailService;

    /**
     * The service that will save the transaction details
     */
    private final TransactionDetailService transactionDetailService;

    /**
     * This method receives the data of the file in a String and converts that into a
     * Interchange object
     * @param fileDetailDto
     * @throws JsonProcessingException
     */
    @Override
    public FileResponseDto processFileData(FileDetailDto fileDetailDto) throws JsonProcessingException {
        String fileData = fileDetailDto.getFileData();
        List<String> fileSegments = new ArrayList<>(Arrays.asList(fileData.split("~")));
        Interchange interchange = ediFileDataHelper.processEDIFileData(fileSegments);
        log.info("Interchange Info:{}", interchange);
        InterchangeDetail interchangeDetail = interchangeDetailService.saveInterchangeDetail(fileDetailDto, interchange);
        Set<FunctionalGroup> functionalGroupSet = interchange.getFunctionalGroupSet();
        functionalGroupSet.stream().forEach(functionalGroup -> {
            // save the functional group using the interchange detail key
            FunctionalGroupDetail functionalGroupDetail = functionalGroupDetailService.saveFunctionalGroupDetail(
                    interchangeDetail,
                    functionalGroup
            );
            // get all the transactions that are present in the functional group
            Set<Transaction> transactions = functionalGroup.getTransactionSet();
            transactions.stream().forEach(transaction -> {
                // save each of the transactions within each functional group
                try {
                    transactionDetailService.saveTransactionDetail(
                            functionalGroupDetail,
                            transaction
                    );
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });

        });
        log.info("After Save Complete");
        FileResponseDto fileResponseDto = FileResponseDto.builder()
                .zeusFileControlNumber(fileDetailDto.getZeusFileControlNumber())
                .fileReceiptAck(interchangeDetail.getInterchangeSK().toString())
                .serviceName("TRANS_ORIG_SERVICE")
                .build();
        return fileResponseDto;
    }
}
