package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.enrollment.Transaction;
import com.brihaspathee.zeus.helper.interfaces.TransactionHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12, April 2022
 * Time: 5:01 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionHelperImpl implements TransactionHelper {
    @Override
    public Transaction createTransaction(List<String> transactionSegments) {
        return Transaction.builder().build();
    }
}