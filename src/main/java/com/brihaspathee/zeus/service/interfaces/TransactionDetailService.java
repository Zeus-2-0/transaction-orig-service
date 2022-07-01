package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.domain.entity.FunctionalGroupDetail;
import com.brihaspathee.zeus.domain.entity.TransactionDetail;
import com.brihaspathee.zeus.edi.models.enrollment.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, July 2022
 * Time: 8:10 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionDetailService {

    /**
     * Save the details of the transaction
     * @param functionalGroupDetail
     * @param transaction
     * @return
     */
    TransactionDetail saveTransactionDetail(FunctionalGroupDetail functionalGroupDetail, Transaction transaction) throws JsonProcessingException;
}
