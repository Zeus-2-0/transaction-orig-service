package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.N3;
import com.brihaspathee.zeus.edi.models.common.N4;
import com.brihaspathee.zeus.edi.models.common.NM1;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2100H;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:17 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2100HDataHelperImpl implements Loop2100HDataHelper {

    /**
     * Populates and creates NM1 segment
     */
    private final NM1SegmentHelper nm1SegmentHelper;

    /**
     * Populates and creates N3 segment
     */
    private final N3SegmentHelper n3SegmentHelper;

    /**
     * Populates and creates N4 segment
     */
    private final N4SegmentHelper n4SegmentHelper;

    /**
     * Populates and creates the Loop2100H (Drop off location Segment)
     * @param loop2100HValues
     * @return
     */
    @Override
    public Loop2100H populateLoop2100H(List<String> loop2100HValues) {

        // Drop off location is not always present in the transaction
        // populate as NULL if not present in the transaction
        if(loop2100HValues.isEmpty()){
            return null;
        }
        Loop2100H loop2100H = Loop2100H.builder().build();
        for(String dropOffLocation: loop2100HValues){
            dropOffLocation=dropOffLocation.trim();
            List<String> elements = Arrays.asList(dropOffLocation.split("\\*"));
            String segmentName = elements.get(0);
            switch (segmentName){
                case "NM1":
                    NM1 memberName = nm1SegmentHelper.populateNM1Segment(elements);
                    loop2100H.setDropOffLocationName(memberName);
                    break;
                case "N3":
                    N3 memberAddressLines = n3SegmentHelper.populateN3Segment(elements);
                    loop2100H.setDropOffLocationAddressLine(memberAddressLines);
                    break;
                case "N4":
                    N4 memberCityStateZip = n4SegmentHelper.populateN4Segment(elements);
                    loop2100H.setDropOffLocationCityStateZip(memberCityStateZip);
                    break;
            }
        }
        return loop2100H;
    }
}
