package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.BGN;
import com.brihaspathee.zeus.edi.models.common.DTP;
import com.brihaspathee.zeus.edi.models.common.QTY;
import com.brihaspathee.zeus.edi.models.common.REF;
import com.brihaspathee.zeus.edi.models.enrollment.Transaction;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 15, April 2022
 * Time: 3:18 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionHeaderHelperImpl implements TransactionHeaderHelper {

    /**
     * The helper class that creates the BGN Segment
     */
    private final BGNSegmentHelper bgnSegmentHelper;

    /**
     * The helper class that create the REF Segment
     */
    private final REFSegmentHelper refSegmentHelper;

    /**
     * The helper class that create the DTP Segments
     */
    private final DTPSegmentHelper dtpSegmentHelper;

    /**
     * The helper class that create the QTY Segments
     */
    private final QTYSegmentHelper qtySegmentHelper;

    /**
     * This method will receive the transaction header segments and populate the
     * Transaction objects with the header details.
     * @param transactionHeaderSegments
     * @return
     */
    @Override
    public Transaction createTransactionHeader(List<String> transactionHeaderSegments) {
        log.info("Transaction Header Segments:{}", transactionHeaderSegments);
        Transaction transaction = Transaction.builder().build();
        // Loop thorugh the transaction header segments
        for(String transactionHeaderSegment: transactionHeaderSegments){
            // Trim the segment for any white spaces
            transactionHeaderSegment = transactionHeaderSegment.trim();
            // Split the segment into elements
            List<String> elements = new ArrayList<String>(Arrays.asList(transactionHeaderSegment.split("\\*")));
            String segmentName = elements.get(0);
            if(segmentName.equals("ST")){
                // Populate the ST Segment
                populateTransactionEnvelope(transaction, elements);
            }else if(segmentName.equals("SE")){
                // Populate the details of the SE trailer details
                populateTransactionTrailer(transaction, elements);
            }else if(segmentName.equals("BGN")){
                // Populate the details of the BGN segment
                BGN bgn = bgnSegmentHelper.populateBeginSegment(elements);
                transaction.setBeginningSegment(bgn);
            }else if(segmentName.equals("REF")){
                // Populate the details of the REF Segment
                REF transactionSetPolicyNumber = refSegmentHelper.populateREFSegment(elements);
                transaction.setTransactionSetPolicyNumber(transactionSetPolicyNumber);
            }else if(segmentName.equals("DTP")){
                // Populate the details of the DTP Segments
                DTP fileEffectiveDate = dtpSegmentHelper.populateDTPSegment(elements);
                transaction.getFileEffectiveDates().add(fileEffectiveDate);
            }else if(segmentName.equals("QTY")){
                // Populate the details of the QTY segments
                QTY transactionSetControls = qtySegmentHelper.populateQTYSegment(elements);
                transaction.getTransactionSetControls().add(transactionSetControls);
            }
        }
        return transaction;
    }

    private void populateTransactionEnvelope(Transaction transaction, List<String> elements){
//        List<String> elements = new ArrayList<String>(Arrays.asList(stSegment.split("\\*")));
        Iterator<String> iterator = elements.iterator();
        int stElement = 0;
        while (iterator.hasNext()){
            //log.info("Element Value:{}", element);
            String elementValue = iterator.next();
            //log.info("Element Value:{}",elementValue);
            switch (stElement){
                case 1:
                    transaction.setSt01(elementValue);
                    stElement++;
                    break;
                case 2:
                    transaction.setSt02(elementValue);
                    stElement++;
                    break;
                case 3:
                    transaction.setSt03(elementValue);
                    stElement++;
                    break;
                default:
                    stElement++;
            }
        }
    }

    private void populateTransactionTrailer(Transaction transaction, List<String> elements) {
//        ArrayList<String> segmentDetails = new ArrayList<String>(Arrays.asList(seg.split("\\*")));
        Iterator<String> iterator = elements.iterator();
        int seElement = 0;
        while (iterator.hasNext()){
            //log.info("Element Value:{}", element);
            String elementValue = iterator.next();
            //log.info("Element Value:{}",elementValue);
            switch (seElement){
                case 1:
                    transaction.setSe01(elementValue);
                    seElement++;
                    break;
                case 2:
                    transaction.setSe02(elementValue);
                    seElement++;
                    break;
                default:
                    seElement++;
            }
        }
    }
}
