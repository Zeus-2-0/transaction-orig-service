package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.N3;
import com.brihaspathee.zeus.edi.models.common.N4;
import com.brihaspathee.zeus.edi.models.common.NM1;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2100C;
import com.brihaspathee.zeus.helper.interfaces.Loop2100CDataHelper;
import com.brihaspathee.zeus.helper.interfaces.N3SegmentHelper;
import com.brihaspathee.zeus.helper.interfaces.N4SegmentHelper;
import com.brihaspathee.zeus.helper.interfaces.NM1SegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:15 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 *
 * Loop 2100C contains member mailing address
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2100CDataHelperImpl implements Loop2100CDataHelper {

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


    @Override
    public Loop2100C populateLoop2100C(List<String> loop2100CValues) {

        // Mailing address is not always present in the transaction
        // populate as NULL if not present in the transaction
        if(loop2100CValues.isEmpty()){
            return null;
        }
        Loop2100C loop2100C = Loop2100C.builder().build();
        for(String mailingAddressSegment: loop2100CValues){
            mailingAddressSegment=mailingAddressSegment.trim();
            List<String> elements = Arrays.asList(mailingAddressSegment.split("\\*"));
            String segmentName = elements.get(0);
            switch (segmentName){
                case "NM1":
                    NM1 memberName = nm1SegmentHelper.populateNM1Segment(elements);
                    loop2100C.setMemberName(memberName);
                    break;
                case "N3":
                    N3 memberAddressLines = n3SegmentHelper.populateN3Segment(elements);
                    loop2100C.setMemberAddressLine(memberAddressLines);
                    break;
                case "N4":
                    N4 memberCityStateZip = n4SegmentHelper.populateN4Segment(elements);
                    loop2100C.setMemberCityStateZip(memberCityStateZip);
                    break;
            }
        }
        return loop2100C;
    }
}
