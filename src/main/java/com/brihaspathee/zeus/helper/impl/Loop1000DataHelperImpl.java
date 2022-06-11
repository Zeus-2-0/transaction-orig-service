package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.ACT;
import com.brihaspathee.zeus.edi.models.common.N1;
import com.brihaspathee.zeus.edi.models.enrollment.*;
import com.brihaspathee.zeus.helper.interfaces.ACTSegmentHelper;
import com.brihaspathee.zeus.helper.interfaces.Loop1000DataHelper;
import com.brihaspathee.zeus.helper.interfaces.N1SegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 26, April 2022
 * Time: 11:36 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop1000DataHelperImpl implements Loop1000DataHelper {

    /**
     * Helper class to populate the N1 Segment
     */
    private final N1SegmentHelper n1SegmentHelper;

    /**
     * Helper class to populate the ACT segment
     */
    private final ACTSegmentHelper actSegmentHelper;

    @Override
    public Loop1000 populateLoop1000(List<String> loop1000Segments) {
        Loop1000 loop1000 = Loop1000.builder().build();
        Set<Loop1000C> brokers = new HashSet<>();
        Loop1000C brokerLoop = null;
        for (String loop1000Segment: loop1000Segments){
            List<String> elements = new ArrayList<String>(Arrays.asList(loop1000Segment.split("\\*")));
            String segmentName = elements.get(0);
            String entityCode = elements.get(1);
            // Check if the segment is a broker account segment
            // Assumption here is that the broker loop would have already been build if an ACT is reached
            if(segmentName.equals("ACT")){
                ACT brokerAccount = actSegmentHelper.populateACTSegment(elements);
                Loop1100C account = Loop1100C.builder()
                        .brokerAccount(brokerAccount)
                        .build();
                brokerLoop.setAccountDetails(account);
            }else if(entityCode.equals("P5")){
                // P5 is the sponsor information
                N1 sponsor = n1SegmentHelper.populateN1Segment(elements);
                Loop1000A sponsorLoop = Loop1000A.builder()
                        .sponsor(sponsor)
                        .build();
                loop1000.setSponsor(sponsorLoop);
            }else if(entityCode.equals("IN")){
                // IN is the payer information
                N1 payer = n1SegmentHelper.populateN1Segment(elements);
                Loop1000B payerLoop = Loop1000B.builder()
                        .payer(payer)
                        .build();
                loop1000.setPayer(payerLoop);
            }else if(entityCode.equals("TV") || entityCode.equals("BO")){
                // The broker comes with entity code TV or B0
                N1 broker = n1SegmentHelper.populateN1Segment(elements);
                // Create the broker loop to set the broker information
                brokerLoop = Loop1000C.builder().build();
                brokerLoop.setBroker(broker);
                // Add the broker to the list
                brokers.add(brokerLoop);
            }
        }
        // Once all the brokers are available in the list set the list to the Loop1000 object
        loop1000.setBrokers(brokers);
        return loop1000;
    }
}
