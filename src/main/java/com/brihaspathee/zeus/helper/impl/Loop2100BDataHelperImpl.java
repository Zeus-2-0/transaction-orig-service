package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.DMG;
import com.brihaspathee.zeus.edi.models.common.NM1;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2100B;
import com.brihaspathee.zeus.helper.interfaces.DMGSegmentHelper;
import com.brihaspathee.zeus.helper.interfaces.Loop2100BDataHelper;
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
 * Time: 3:14 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 *
 * Loop 2100B contains Incorrect Member Name
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2100BDataHelperImpl implements Loop2100BDataHelper {

    /**
     * Populates and creates NM1 segment
     */
    private final NM1SegmentHelper nm1SegmentHelper;

    /**
     * Populates and creates DMG segment
     */
    private final DMGSegmentHelper dmgSegmentHelper;

    @Override
    public Loop2100B populateLoop2100B(List<String> loop2100BValues) {
        // Loop2100B is not always present for a transaction
        // Return NULL if there are no values present for Loop2100B
        if(loop2100BValues.isEmpty()){
            return null;
        }
        Loop2100B loop2100B = Loop2100B.builder().build();
        // Loop through the segments present in loop 2100B
        for(String incorrectMemberNameSegment: loop2100BValues){
            incorrectMemberNameSegment=incorrectMemberNameSegment.trim();
            List<String> elements = Arrays.asList(incorrectMemberNameSegment.split("\\*"));
            String segmentName = elements.get(0);
            switch (segmentName){
                case "NM1":
                    NM1 memberName = nm1SegmentHelper.populateNM1Segment(elements);
                    loop2100B.setMemberName(memberName);
                    break;
                case "DMG":
                    DMG memberDemographics = dmgSegmentHelper.populateDMGSegment(elements);
                    loop2100B.setMemberDemographics(memberDemographics);
                    break;
            }
        }
        return loop2100B;
    }
}
