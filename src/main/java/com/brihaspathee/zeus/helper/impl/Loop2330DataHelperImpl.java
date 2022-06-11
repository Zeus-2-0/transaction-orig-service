package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.N3;
import com.brihaspathee.zeus.edi.models.common.N4;
import com.brihaspathee.zeus.edi.models.common.NM1;
import com.brihaspathee.zeus.edi.models.common.PER;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2330;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 10:29 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2330DataHelperImpl implements Loop2330DataHelper {

    /**
     * Creates and populates the NM1 Segment
     */
    private final NM1SegmentHelper nm1SegmentHelper;

    /**
     * Create and populates the PER segment
     */
    private final PERSegmentHelper perSegmentHelper;

    /**
     * Creates and populates the N3 Segment
     */
    private final N3SegmentHelper n3SegmentHelper;

    /**
     * Creates and populates the N4 Segment
     */
    private final N4SegmentHelper n4SegmentHelper;


    /**
     * Populate the data from Loop 2330 COBRelatedEntities
     * @param cobRelatedEntities
     * @return
     */
    @Override
    public Set<Loop2330> populateRelatedEntities(List<String> cobRelatedEntities) {
        // The cob related entity will not be present in all the transactions
        // If it is not present then return null
        if(cobRelatedEntities.isEmpty()){
            return null;
        }
        // The set contains the list of cob related entity segments received
        Set<Loop2330> cobRelatedEntitySegments = new HashSet<>();
        // This list contains the segment of a single cob related entity
        List<String> cobRelatedEntityData = new ArrayList<>();
        for(String cobRelatedEntitySegment: cobRelatedEntities){
            // Trim any white spaces
            cobRelatedEntitySegment=cobRelatedEntitySegment.trim();
            // Get the name of the segment
            String [] segment = cobRelatedEntitySegment.split("\\*");
            String segmentName = segment[0];
            if(segmentName.equals("NM1")){
                // Check if the cobRelatedEntityData is not empty
                if(!cobRelatedEntityData.isEmpty()){
                    // if it is not empty then the loop 2330 needs to be created before the next one is added
                    Loop2330 cobRelatedEntity = populateCOBRelatedEntity(cobRelatedEntityData);
                    // Add the created cob related entity to the set
                    cobRelatedEntitySegments.add(cobRelatedEntity);
                    // Clear the list so that next once can be added
                    cobRelatedEntityData.clear();
                }
            }
            cobRelatedEntityData.add(cobRelatedEntitySegment);
        }
        // Populate the last CON Related entity
        Loop2330 loop2330 = populateCOBRelatedEntity(cobRelatedEntityData);
        // Add the created cob related entity to the set
        cobRelatedEntitySegments.add(loop2330);
        return cobRelatedEntitySegments;
    }

    /**
     * Creates and populates the single Loop 2330 object
     * @param cobRelatedEntityData
     * @return
     */
    private Loop2330 populateCOBRelatedEntity(List<String> cobRelatedEntityData) {
        Loop2330 loop2330 = Loop2330.builder().build();
        for(String cobRelatedEntitySegment: cobRelatedEntityData){
            cobRelatedEntitySegment=cobRelatedEntitySegment.trim();
            List<String> cobRelatedEntityElements = new ArrayList<>(Arrays.asList(cobRelatedEntitySegment.split("\\*")));
            String segmentName = cobRelatedEntityElements.get(0);
            switch (segmentName){
                case "NM1":
                    NM1 name = nm1SegmentHelper.populateNM1Segment(cobRelatedEntityElements);
                    loop2330.setCobRelatedEntity(name);
                    break;
                case "PER":
                    PER commn = perSegmentHelper.populatePERSegment(cobRelatedEntityElements);
                    loop2330.setAdministrationContact(commn);
                    break;
                case "N3":
                    N3 addressLines = n3SegmentHelper.populateN3Segment(cobRelatedEntityElements);
                    loop2330.setCobRelatedEntityAddress(addressLines);
                    break;
                case "N4":
                    N4 cityStateZip = n4SegmentHelper.populateN4Segment(cobRelatedEntityElements);
                    loop2330.setCobRelatedEntityCityStateZip(cityStateZip);
                    break;
            }
        }
        return loop2330;
    }
}
