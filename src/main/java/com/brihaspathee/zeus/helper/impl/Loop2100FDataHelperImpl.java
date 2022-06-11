package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.N3;
import com.brihaspathee.zeus.edi.models.common.N4;
import com.brihaspathee.zeus.edi.models.common.NM1;
import com.brihaspathee.zeus.edi.models.common.PER;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2100F;
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
 * Time: 3:16 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 *
 * Loop2100F contains the custodial parent detail
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2100FDataHelperImpl implements Loop2100FDataHelper {

    /**
     * Populates and creates NM1 segment
     */
    private final NM1SegmentHelper nm1SegmentHelper;

    /**
     * Populates and creates PER segment
     */
    private final PERSegmentHelper perSegmentHelper;

    /**
     * Populates and creates N3 segment
     */
    private final N3SegmentHelper n3SegmentHelper;

    /**
     * Populates and creates N4 segment
     */
    private final N4SegmentHelper n4SegmentHelper;

    /**
     * Populates and creates the Loop2100F (Custodial Parent Segment)
     * @param loop2100FValues
     * @return
     */
    @Override
    public Loop2100F populateLoop2100F(List<String> loop2100FValues) {

        // Custodial Parent is not always present in the transaction
        // populate as NULL if not present in the transaction
        if(loop2100FValues.isEmpty()){
            return null;
        }
        Loop2100F loop2100F = Loop2100F.builder().build();
        for(String custodialParentSegment: loop2100FValues){
            custodialParentSegment=custodialParentSegment.trim();
            List<String> elements = Arrays.asList(custodialParentSegment.split("\\*"));
            String segmentName = elements.get(0);
            switch (segmentName){
                case "NM1":
                    NM1 memberName = nm1SegmentHelper.populateNM1Segment(elements);
                    loop2100F.setCustodialParentName(memberName);
                    break;
                case "PER":
                    PER communication = perSegmentHelper.populatePERSegment(elements);
                    loop2100F.setCustodialParentCommunications(communication);
                    break;
                case "N3":
                    N3 memberAddressLines = n3SegmentHelper.populateN3Segment(elements);
                    loop2100F.setCustodialParentAddressLine(memberAddressLines);
                    break;
                case "N4":
                    N4 memberCityStateZip = n4SegmentHelper.populateN4Segment(elements);
                    loop2100F.setCustodialParentCityStateZip(memberCityStateZip);
                    break;
            }
        }
        return loop2100F;
    }
}
