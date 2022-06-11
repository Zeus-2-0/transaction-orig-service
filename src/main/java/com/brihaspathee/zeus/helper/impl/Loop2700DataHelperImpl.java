package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.LE;
import com.brihaspathee.zeus.edi.models.common.LS;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2700;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2710;
import com.brihaspathee.zeus.helper.interfaces.LESegmentHelper;
import com.brihaspathee.zeus.helper.interfaces.LSSegmentHelper;
import com.brihaspathee.zeus.helper.interfaces.Loop2700DataHelper;
import com.brihaspathee.zeus.helper.interfaces.Loop2710DataHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 5:11 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2700DataHelperImpl implements Loop2700DataHelper {

    /**
     *  Creates and populates the LS Segment
     */
    private final LSSegmentHelper lsSegmentHelper;

    /**
     * Creates and populates the Loop 2710
     */
    private final Loop2710DataHelper loop2710DataHelper;

    /**
     * Creates and populates the LE Segment
     */
    private final LESegmentHelper leSegmentHelper;


    /**
     * Create the loop 2700 segment
     * @param loop2700Segments
     * @return
     */
    @Override
    public Loop2700 populateLoop2700(List<String> loop2700Segments) {
        Loop2700 reporting = Loop2700.builder().build();
        List<String> loop2710Segments = new ArrayList<>();
        for(String seg: loop2700Segments){
            seg=seg.trim();
            List<String> elements = new ArrayList(Arrays.asList(seg.split("\\*")));
            String segmentName = elements.get(0);
            if(segmentName.equals("LS")){
                LS ls = lsSegmentHelper.populateLSSegment(elements);
                reporting.setAdditionalReportingCategory(ls);
            }else if(segmentName.equals("LE")){
                LE le = leSegmentHelper.populateLESegment(elements);
                reporting.setLoopTermination(le);
            }else{
                loop2710Segments.add(seg);
            }
        }
        Set<Loop2710> reportingCategories = loop2710DataHelper.populateLoop2710(loop2710Segments);
        reporting.setReportingCategories(reportingCategories);
        return reporting;
    }
}
