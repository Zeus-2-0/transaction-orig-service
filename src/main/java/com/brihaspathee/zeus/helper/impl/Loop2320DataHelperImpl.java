package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.COB;
import com.brihaspathee.zeus.edi.models.common.DTP;
import com.brihaspathee.zeus.edi.models.common.REF;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2320;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2330;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 10:28 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2320DataHelperImpl implements Loop2320DataHelper {

    /**
     * Creates and populates the COB Segment
     */
    private final COBSegmentHelper cobSegmentHelper;

    /**
     * Creates and populates the REF Segment
     */
    private final REFSegmentHelper refSegmentHelper;

    /**
     * Create and populate the DTP Segment
     */
    private final DTPSegmentHelper dtpSegmentHelper;

    /**
     * Creates and populate the Loop 2330
     */
    private final Loop2330DataHelper loop2330DataHelper;

    /**
     * Creates all the COB Segments that are present in the transaction
     * @param cobSegments
     * @return
     */
    @Override
    public Set<Loop2320> populateCOBs(List<String> cobSegments) {
        // COB segment is not mandatory
        // if not available in the transaction it will be sent as null
        if(cobSegments.isEmpty()){
            return null;
        }
        // The set contains all the COBs that are present in the transaction
        Set<Loop2320> cobs = new HashSet<>();
        // The list will contain the segments of a single COB
        List<String> singleCOBSegments = new ArrayList<>();
        for(String cobSegment: cobSegments){
            // Trim the white space
            cobSegment=cobSegment.trim();
            // Get the segment name
            String [] segment = cobSegment.split("\\*");
            String segmentName = segment[0];
            if(segmentName.equals("COB")){
                // Check if a COB is already present in the list
                if(!singleCOBSegments.isEmpty()){
                    // If it is already present then create the Loop 2320 object
                    Loop2320 singleCOB = populateLoop2320(singleCOBSegments);
                    // Add the COB to the set
                    cobs.add(singleCOB);
                    // Clear the list so that the next one can be created
                    singleCOBSegments.clear();
                }
            }
            singleCOBSegments.add(cobSegment);
        }
        // Create the last COB Segment
        Loop2320 loop2320 = populateLoop2320(singleCOBSegments);
        // Add it to the set
        cobs.add(loop2320);
        return cobs;
    }

    /**
     * Creates and populates the single loop 2320
     * @param singleCOBSegments
     * @return
     */
    private Loop2320 populateLoop2320(List<String> singleCOBSegments) {
        Loop2320 singleCOB = Loop2320.builder().build();
        // A loop 2320 can also contain loop 2330
        // List to contain the loop 2320 segments
        List<String> cobSegments = new ArrayList<>();
        // List to contain the loop 2330 segments
        List<String> cobRelatedEntitySegments = new ArrayList<>();
        // Set both the entities as FALSE to begin with
        AtomicBoolean cob = new AtomicBoolean(false);
        AtomicBoolean cobRelatedEntity = new AtomicBoolean(false);
        for(String cobSegment: singleCOBSegments){
            // Trim any white spaces
            cobSegment=cobSegment.trim();
            String [] segment = cobSegment.split("\\*");
            String segmentName = segment[0];
            if(segmentName.equals("COB")){
                // if segment name is "COB" then we are dealing with Loop 2320
                // so set COB to true and COB Related Entity to FALSE
                cob.set(true);
                cobRelatedEntity.set(false);
            }else if (segmentName.equals("NM1")){
                // if segment name is "NM1" then we are dealing with Loop 2330
                // so set COB to false and COB Related Entity to TRUE
                cob.set(false);
                cobRelatedEntity.set(true);
            }
            if(cob.get()){
                // if COB segments are being gathered add it to the cobSegments list
                cobSegments.add(cobSegment);
            }else if(cobRelatedEntity.get()){
                // if COB related entity segments are being gathered add it to the cobRelatedSegments list
                cobRelatedEntitySegments.add(cobSegment);
            }
        }
        // Create the loop 2302
        populateCOB(singleCOB,cobSegments);
        // Create the Loop 2330
        Set<Loop2330> cobRelatedEntities = loop2330DataHelper.populateRelatedEntities(cobRelatedEntitySegments);
        // Set loop 2330 in loop 2320
        singleCOB.setCobRelatedEntities(cobRelatedEntities);
        return singleCOB;
    }

    /**
     * Method to populate the single COB loop
     * @param singleCOB
     * @param cobSegments
     */
    private void populateCOB(Loop2320 singleCOB, List<String> cobSegments) {
        Set<DTP> cobDates = new HashSet<>();
        Set<REF> additionalCOBIds = new HashSet<>();
        for(String cobSegment: cobSegments){
            cobSegment=cobSegment.trim();
            List<String> cobElements = new ArrayList<>(Arrays.asList(cobSegment.split("\\*")));
            String segmentName = cobElements.get(0);
            switch (segmentName){
                case "COB":
                    COB cob = cobSegmentHelper.populateCOBSegment(cobElements);
                    singleCOB.setCoordinationOfBenefits(cob);
                    break;
                case "REF":
                    REF additionalCOBId = refSegmentHelper.populateREFSegment(cobElements);
                    additionalCOBIds.add(additionalCOBId);
                    break;
                case "DTP":
                    DTP cobDate = dtpSegmentHelper.populateDTPSegment(cobElements);
                    cobDates.add(cobDate);
                    break;
            }
        }
        singleCOB.setAdditionalCOBIds(additionalCOBIds);
        singleCOB.setCobDates(cobDates);
    }
}
