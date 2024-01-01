package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.domain.entity.FunctionalGroupDetail;
import com.brihaspathee.zeus.domain.entity.TransactionDetail;
import com.brihaspathee.zeus.domain.repository.TransactionDetailRepository;
import com.brihaspathee.zeus.edi.models.enrollment.Transaction;
import com.brihaspathee.zeus.service.interfaces.TransactionDetailService;
import com.brihaspathee.zeus.util.ZeusRandomStringGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, July 2022
 * Time: 8:12 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {

    /**
     * The repository to save the transaction detail
     */
    private final TransactionDetailRepository repository;

    /**
     * The object mapper to convert the transaction data to String
     */
    private final ObjectMapper objectMapper;

    /**
     * Save the details of the transaction
     * @param functionalGroupDetail
     * @param transaction
     * @param ztcn
     * @return
     */
    @Override
    public TransactionDetail saveTransactionDetail(FunctionalGroupDetail functionalGroupDetail,
                                                   Transaction transaction,
                                                   String ztcn) throws JsonProcessingException {
        String transactionAsString = objectMapper.writeValueAsString(transaction);
        TransactionDetail transactionDetail = TransactionDetail.builder()
                .transactionControlNumber(transaction.getSt02())
                .zeusTransactionControlNumber(ztcn)
                .functionalGroup(functionalGroupDetail)
                .transactionData(transactionAsString)
                .build();
        repository.save(transactionDetail);
        return transactionDetail;
    }
}
