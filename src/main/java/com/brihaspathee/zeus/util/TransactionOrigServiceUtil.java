package com.brihaspathee.zeus.util;

import com.brihaspathee.zeus.edi.models.enrollment.Transaction;
import com.brihaspathee.zeus.test.ZeusTransactionControlNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 26, December 2023
 * Time: 5:47 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.util
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionOrigServiceUtil {

    /**
     * The spring environment instance
     */
    private final Environment environment;

    /**
     * Generate a unique code
     * @param zeusTransactionControlNumbers
     * @param transaction
     * @return
     */
    public ZeusTransactionControlNumber generateZtcn(List<ZeusTransactionControlNumber> zeusTransactionControlNumbers,
                                                     Transaction transaction){
        ZeusTransactionControlNumber zeusTransactionControlNumber = null;
        // Use the code from the test data if the profile is "int-test"
        if(Arrays.asList(environment.getActiveProfiles()).contains("int-test")){
            String tcn = transaction.getSt02();
            if(zeusTransactionControlNumbers != null){
                Optional<ZeusTransactionControlNumber> optionalZtcn = zeusTransactionControlNumbers.stream()
                        .filter(
                                transactionControlNumber -> transactionControlNumber.getTcn().equals(tcn))
                        .findFirst();
                if(optionalZtcn.isEmpty()){
                    return null;
                }else {
                    return optionalZtcn.get();
                }
            }
        }else{
            zeusTransactionControlNumber = ZeusTransactionControlNumber.builder()
                    .ztcn(ZeusRandomStringGenerator.randomString(15))
                    .build();
        }
        return zeusTransactionControlNumber;
    }
}
