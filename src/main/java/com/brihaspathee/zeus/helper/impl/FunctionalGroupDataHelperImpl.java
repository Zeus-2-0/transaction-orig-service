package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.FunctionalGroup;
import com.brihaspathee.zeus.edi.models.enrollment.Transaction;
import com.brihaspathee.zeus.helper.interfaces.FunctionalGroupDataHelper;
import com.brihaspathee.zeus.helper.interfaces.TransactionHelper;
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
 * Date: 06, April 2022
 * Time: 2:16 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FunctionalGroupDataHelperImpl implements FunctionalGroupDataHelper {

    /**
     * Helps to create the Transaction object
     */
    private final TransactionHelper transactionHelper;

    /**
     * The method takes the list of all segments that are present inside a
     * functional group and creates the functional group object
     * A functional group consists of one or more transactions
     * @param functionalGroupSegments
     * @param transactionSequence
     * @return
     */
    @Override
    public FunctionalGroup createFunctionalGroup(List<String> functionalGroupSegments, int transactionSequence) {
        // Functional group object that will be populated with the segment data
        FunctionalGroup functionalGroup = FunctionalGroup.builder().build();
        // boolean value that indicates that the process is currently gathering data for creating the
        // transaction object
        boolean processingST = false;
        // The list that will contain the segment within each ST-SE
        List<String> transactionSegments = null;
        // Transaction sequence counter to keep track of the sequence within the file
        for(String functionalGroupSegment: functionalGroupSegments){
            // Trim down any white spaces in the segment
            functionalGroupSegment = functionalGroupSegment.trim();
            // get all the individual elements of the segment in a String array
            // Note: The first element in the array will be the name of the segment (E.g. ISA, GS, IEA, GE etc.)
            String [] elements = functionalGroupSegment.split("\\*");
            String segmentName = elements[0];
            // check if the first element is "GS"
            if(segmentName.equals("GS")){
                // The segment that we are dealing with here is the GS segment
                populateFunctionalGroupHeader(functionalGroup, functionalGroupSegment);
            }else if(segmentName.equals("ST") || processingST){
                // This part of the code will be executed if we are starting off with a new transaction (ST_
                // or we are currently in the middle of collecting all the
                // segments within the respective transaction (ST)
                if(segmentName.equals("GS")){
                    // This means we are starting the ST segment so initialize the transaction segment list
                    // and add the first segment
                    transactionSegments = new ArrayList<>(Arrays.asList(functionalGroupSegment));
                    // Set the processingST to TRUE to indicate that next set of segments will all be
                    // segments that were received under the respective transaction (ST)
                    processingST = true;
                }else if(segmentName.equals("SE")){
                    // This means that we have come the end of the ST segments and the
                    // transaction segment list should now have all the segments
                    // within ST and SE and hence we can start to build the individual
                    // segment objects within the transaction
                    transactionSegments.add(functionalGroupSegment);
                    // Create the transaction object
                    Transaction transaction = transactionHelper.createTransaction(transactionSegments);
                    // Set the sequence of the transaction
                    transaction.setTransactionSequence(++transactionSequence);
                    // Add the transaction to the group set
                    functionalGroup.getTransactionSet().add(transaction);
                    processingST = false;
                }else{
                    // This means that this segment is not a ST OR SE, but a segment that within ST and SE
                    // so continue adding to the transaction segment list
                    transactionSegments.add(functionalGroupSegment);
                }
            }else if(segmentName.equals("GE")){
                // if the segment name is GE then it is last segment of the functional group
                // populate the functional group trailer details
                populateFunctionalTrailer(functionalGroup, functionalGroupSegment);
            }
        }
        return functionalGroup;
    }

    /**
     * The method to populate the functional group header elements
     * @param functionalGroup
     * @param gsSegment
     */
    private void populateFunctionalGroupHeader(FunctionalGroup functionalGroup, String gsSegment){
        // Get individual list of elements
        List<String> gsElements = new ArrayList<String>(Arrays.asList(gsSegment.split("\\*")));
        Iterator<String> iterator = gsElements.iterator();
        int gsElement = 0;
        while(iterator.hasNext()){
            String elementValue = iterator.next();
            switch (gsElement){
                case 1:
                    functionalGroup.setGs01(elementValue);
                    break;
                case 2:
                    functionalGroup.setGs02(elementValue);
                    break;
                case 3:
                    functionalGroup.setGs03(elementValue);
                    break;
                case 4:
                    functionalGroup.setGs04(elementValue);
                    break;
                case 5:
                    functionalGroup.setGs05(elementValue);
                    break;
                case 6:
                    functionalGroup.setGs06(elementValue);
                    break;
                case 7:
                    functionalGroup.setGs07(elementValue);
                    break;
                case 8:
                    functionalGroup.setGs08(elementValue);
                    break;
                default:
                    gsElement++;
            }
        }
    }

    /**
     * The method to populate the functional group trailer elements
     * @param functionalGroup
     * @param geSegment
     */
    private void populateFunctionalTrailer(FunctionalGroup functionalGroup, String geSegment){
        // Get individual list of elements
        List<String> gsElements = new ArrayList<String>(Arrays.asList(geSegment.split("\\*")));
        Iterator<String> iterator = gsElements.iterator();
        int geElement = 0;
        while(iterator.hasNext()){
            String elementValue = iterator.next();
            switch (geElement){
                case 1:
                    functionalGroup.setGe01(elementValue);
                    break;
                case 2:
                    functionalGroup.setGe02(elementValue);
                    break;
                default:
                    geElement++;
            }
        }
    }
}
