package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.broker.producer.TransactionPublisher;
import com.brihaspathee.zeus.domain.entity.FunctionalGroupDetail;
import com.brihaspathee.zeus.domain.entity.InterchangeDetail;
import com.brihaspathee.zeus.domain.entity.TransactionDetail;
import com.brihaspathee.zeus.dto.account.RawTransactionDto;
import com.brihaspathee.zeus.dto.transaction.FileDetailDto;
import com.brihaspathee.zeus.edi.models.common.FunctionalGroup;
import com.brihaspathee.zeus.edi.models.common.Interchange;
import com.brihaspathee.zeus.edi.models.enrollment.Transaction;
import com.brihaspathee.zeus.helper.interfaces.EDIFileDataHelper;
import com.brihaspathee.zeus.service.interfaces.FileDataProcessingService;
import com.brihaspathee.zeus.service.interfaces.FunctionalGroupDetailService;
import com.brihaspathee.zeus.service.interfaces.InterchangeDetailService;
import com.brihaspathee.zeus.service.interfaces.TransactionDetailService;
import com.brihaspathee.zeus.test.ZeusTransactionControlNumber;
import com.brihaspathee.zeus.util.TransactionOrigServiceUtil;
import com.brihaspathee.zeus.util.ZeusRandomStringGenerator;
import com.brihaspathee.zeus.web.model.FileResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
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
     * The spring environment instance
     */
    private final Environment environment;

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
     * The utility class to generate the ztcn
     */
    private final TransactionOrigServiceUtil transactionOrigServiceUtil;

    /**
     * The kafka producer that will send the transaction to the kafka topic for other services to consume
     */
    private final TransactionPublisher transactionPublisher;

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
                    ZeusTransactionControlNumber zeusTransactionControlNumber =
                            transactionOrigServiceUtil.generateZtcn(fileDetailDto.getTransactionControlNumbers(), transaction);
                    assert zeusTransactionControlNumber != null;
                    TransactionDetail transactionDetail = transactionDetailService.saveTransactionDetail(
                            functionalGroupDetail,
                            transaction, zeusTransactionControlNumber.getZtcn(),
                            "MARKETPLACE");
                    sendTransactionToConsumers(fileDetailDto, transaction,
                            transactionDetail, zeusTransactionControlNumber);
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

    /**
     * Construct the raw transaction dto object and send to publisher to send to kafka
     * @param fileDetailDto
     * @param transaction
     */
    private void sendTransactionToConsumers(FileDetailDto fileDetailDto,
                                            Transaction transaction,
                                            TransactionDetail transactionDetail,
                                            ZeusTransactionControlNumber zeusTransactionControlNumber) throws JsonProcessingException {
        RawTransactionDto rawTransactionDto = RawTransactionDto.builder()
                .transaction(transaction)
                .ztcn(transactionDetail.getZeusTransactionControlNumber())
                .zfcn(fileDetailDto.getZeusFileControlNumber())
                .source(transactionDetail.getSource())
                .businessUnitTypeCode(fileDetailDto.getBusinessUnitTypeCode())
                .lineOfBusinessTypeCode(fileDetailDto.getLineOfBusinessTypeCode())
                .marketplaceTypeCode(fileDetailDto.getMarketplaceTypeCode())
                .stateTypeCode(fileDetailDto.getStateTypeCode())
                .tradingPartnerId(fileDetailDto.getTradingPartnerId())
                .zeusTransactionControlNumber(zeusTransactionControlNumber)
                .build();
        transactionPublisher.publishTransaction(rawTransactionDto);
    }
}
