package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.enrollment.Transaction;
import com.brihaspathee.zeus.helper.interfaces.TransactionHeaderHelper;
import com.brihaspathee.zeus.helper.interfaces.TransactionHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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

    /**
     * Popualte the transaction header details
     */
    private final TransactionHeaderHelper transactionHeaderHelper;
    /**
     * This method takes the list of all the segments in a transaction
     * and creates a transaction object
     * @param transactionSegments
     * @return
     */
    @Override
    public Transaction createTransaction(List<String> transactionSegments) {
        // Transaction transaction = Transaction.builder().build();
        // A boolean value that keeps track if we are processing the transaction header segments
        // ST, BGN, REF, DTP and QTY
        // This is set to true as we know that the first set of the segments will always be the header  segments
        AtomicBoolean processingTransactionHeader = new AtomicBoolean(true);
        // A boolean value that keeps track if we are processing the Loop 1000
        // Sponsor, Broker and Payer
        // This is set to FALSE initially until all the segments of the header are processed
        // Once the header segments are completed, this will be set to TRUE to pick up all the segments of Loop 1000
        AtomicBoolean loop1000 = new AtomicBoolean(false);
        // A boolean value that keeps track if we are processing the Member Loops (Loop 2000)
        // Loop 2000, 2100s 2300s and 2750
        // This is set to FALSE initially until all the segments of the header and Loop 2000 are processed
        // Once the header and loop 1000 segments are completed, this will be set to TRUE to pick up all the segments the belongs to all the members
        AtomicBoolean loopMembers = new AtomicBoolean(false);
        // List to contain all the transaction header segments
        List<String> transactionHeaderSegments = new ArrayList<>();
        // List to contain all the segment within loop 1000
        List<String> loop1000Segments = new ArrayList<>();
        // List to contain all the segments within the member details (Loop 2000)
        List<String> memberSegments = new ArrayList<>();
        for(String transactionSegment: transactionSegments){
            // Trim down any white spaces in the segment
            transactionSegment = transactionSegment.trim();
            // get all the individual elements of the segment in a String array
            // Note: The first element in the array will be the name of the segment (E.g. ST, N1, INS, BGN etc.)
            String [] elements = transactionSegment.split("\\*");
            String segmentName = elements[0];
            // check if we are still processing the transaction header
            if(processingTransactionHeader.get()){
                //Collect all the transaction header segments
                transactionHeaderSegments.add(transactionSegment);
            }
            if( (loopMembers.get() || segmentName.equals("INS")) &&
                        !segmentName.equals("SE")){
                // if we are here then it means that we are collecting all the segments within loop 2000
                memberSegments.add(transactionSegment);
                // This also means that all the segments of loop 1000 are collected so this boolean can
                // be set to FALSE if it is not already set to FALSE
                loop1000.set(false);
                // The loopingMembers boolean should be set to TRUE if it is not already set to TRUE
                loopMembers.set(true);
            }else if(loop1000.get()){
                // this means we are in the middle of collection all the segments of Loop 1000
                loop1000Segments.add(transactionSegment);
            }else if(segmentName.equals("N1")){
                // Loop 1000 segments starts with N1, but an N1 segment can be present in other loops as well
                // so we have to put this at the end once we ensure that we are not in the middle of gathering segments
                // of other loops
                loop1000Segments.add(transactionSegment);
                // We are starting to collect the segments of loop 1000, so the header boolean can be set to FALSE
                // and the loop1000 boolean can be set to true
                processingTransactionHeader.set(false);
                loop1000.set(true);
            }else if(segmentName.equals("SE")){
                transactionHeaderSegments.add(transactionSegment);
            }
        }
        Transaction transaction = transactionHeaderHelper.createTransactionHeader(transactionHeaderSegments);

        return transaction;
    }
}
