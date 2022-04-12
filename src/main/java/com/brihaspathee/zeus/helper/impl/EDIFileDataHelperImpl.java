package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.FunctionalGroup;
import com.brihaspathee.zeus.edi.models.common.Interchange;
import com.brihaspathee.zeus.helper.interfaces.EDIFileDataHelper;
import com.brihaspathee.zeus.helper.interfaces.FunctionalGroupDataHelper;
import com.brihaspathee.zeus.helper.interfaces.InterchangeDataHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, April 2022
 * Time: 2:12 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EDIFileDataHelperImpl implements EDIFileDataHelper {

    /**
     * Builds the interchange segment
     */
    private final InterchangeDataHelper interchangeDataHelper;

    /**
     * Builds the segments between each GS-GE segments
     */
    private final FunctionalGroupDataHelper functionalGroupDataHelper;

    /**
     * This methods gets as input the list of all the segments that are present in the
     * EDI file
     * @param fileSegments
     * @return
     */
    @Override
    public Interchange processEDIFileData(List<String> fileSegments) {
        log.info("List of all file segments:{}", fileSegments);
        Interchange interchange = Interchange.builder().build();
        /*
        This boolean value will be set to TRUE when a GS segment is encountered and will be set back to FALSE
        when a GE segment is encountered. Thus, all the segments between a GS and GE will be captured and
        populated in the functional group set object.
        */
        boolean processingGS = false;
        long numberOfFunctionalGroups = fileSegments.stream().filter(segment -> segment.startsWith("GS")).count();
        long numberOfTransactions = fileSegments.stream().filter(segment -> segment.startsWith("ST")).count();
        long numberOfMembers = fileSegments.stream().filter(segment -> segment.startsWith("INS")).count();
        //log.info("Total functional groups:{}", numberOfFunctionalGroups);

        // Transaction sequence of the transaction in the file
        int transactionSequence = 0;

        // This will contain all the segments within the GS - GE Segments
        List<String> functionalGroupSegments = null;

        // Loop through all the segments in the file
        for (String segment: fileSegments){
            // Trim down any white spaces in the segment
            segment = segment.trim();
            // get all the individual elements of the segment in a String array
            // Note: The first element in the array will be the name of the segment (E.g. ISA, GS, IEA, GE etc.)
            String [] elements = segment.split("\\*");
            String segmentName = elements[0];
            // check if the first element is "ISA"
            if(segmentName.equals("ISA")){
                // The segment that we are dealing with here is the ISA segment
                interchange = interchangeDataHelper.populateInterchangeHeader(interchange,segment);
                log.info("Interchange: {}", interchange);
            }else if(segmentName.equals("GS") || processingGS){
                // This part of the code will be executed if we are starting off with a new set of
                // functional group (GS) or we are currently in the middle of collecting all the
                // segments within the respective GS
                if(segmentName.equals("GS")){
                    // This means we are starting the GS segment so initialize the functional group segment list
                    // and add the first segment
                    functionalGroupSegments = new ArrayList<>(Arrays.asList(segment));
                    // Set the processingGS to TRUE to indicate that next set of segments will all be
                    // segments that were received under the respective GS
                    processingGS = true;
                }else if(segmentName.equals("GE")){
                    // This means that we have come the end of the GS segments and the
                    // functional group segment list should now have all the segments
                    // within GS and GE and hence we can start to build the individual
                    // segment objects within the functional group
                    functionalGroupSegments.add(segment);
                    // Get the total number of transactions that are present within this GS-GE
                    String groupTransactionCount = elements[1];
                    // For the first functional group in the file the transaction sequence will be sent as zero
                    FunctionalGroup functionalGroup = functionalGroupDataHelper.createFunctionalGroup(functionalGroupSegments,
                            transactionSequence);
                    // Once the first functional group is created all transactions would have been created
                    // So the transaction sequence will be updated to the total number of transactions in the
                    // previous functional group to keep a running count
                    transactionSequence = transactionSequence + Integer.parseInt(groupTransactionCount);
                    interchange.getFunctionalGroupSet().add(functionalGroup);
                    processingGS = false;
                }else{
                    // This means that this segment is not a GS OR GE, but a segment that within GS and GE
                    // so continue adding to the functional group segment list
                    functionalGroupSegments.add(segment);
                }
            }else if (segmentName.equals("IEA")){
                // if the segment name is IEA then it is last segment of the file
                // populate the interchange trailer details
                interchangeDataHelper.populateInterchangeTrailer(interchange, segment);
            }
        }
        return interchange;
    }
}
