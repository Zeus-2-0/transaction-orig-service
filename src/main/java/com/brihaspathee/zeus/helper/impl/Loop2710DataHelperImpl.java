package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.DTP;
import com.brihaspathee.zeus.edi.models.common.LX;
import com.brihaspathee.zeus.edi.models.common.N1;
import com.brihaspathee.zeus.edi.models.common.REF;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2710;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2750;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 5:10 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2710DataHelperImpl implements Loop2710DataHelper {

    /**
     * Creates and populates the LX Segment
     */
    private final LXSegmentHelper lxSegmentHelper;

    /**
     * Creates and populates the N1 Segment
     */
    private final N1SegmentHelper n1SegmentHelper;

    /**
     * Creates and populates the REF Segment
     */
    private final REFSegmentHelper refSegmentHelper;

    /**
     * Creates and populates the DTP Segment
     */
    private final DTPSegmentHelper dtpSegmentHelper;

    /**
     * Populates and created loop 2710
     * @param loop2710Segments
     * @return
     */
    @Override
    public Set<Loop2710> populateLoop2710(List<String> loop2710Segments) {
        // There can be more than one reporting category in a transaction
        // The reporting categories are stored in this set
        Set<Loop2710> reportingCategories = new HashSet<>();
        // Segments of a single reporting category is stored in this list
        List<String> reportingCategory = new ArrayList<>();
        for(String reportingCategorySegment: loop2710Segments){
            // Trim any white spaces in the segment
            reportingCategorySegment=reportingCategorySegment.trim();
            // Get the name of the segment
            String [] segment = reportingCategorySegment.split("\\*");
            String segmentName = segment[0];
            if(segmentName.equals("LX")){
                // Each reporting category starts with a LX segment
                // If the reportingCategory List is not empty then there already a reporting category present
                if(!reportingCategory.isEmpty()){
                    // Create the Loop 2710 object
                    Loop2710 loop2710 = populateReportingCategory(reportingCategory);
                    // Add the loop 2710 object to the set
                    reportingCategories.add(loop2710);
                    // Clear the reporting category so that the next one can be added
                    reportingCategory.clear();
                }
            }
            reportingCategory.add(reportingCategorySegment);
        }
        Loop2710 lastReportingCategory = populateReportingCategory(reportingCategory);
        reportingCategories.add(lastReportingCategory);
        return reportingCategories;
    }

    /**
     * Populating and creating the reporting categories
     * @param loop2710Segments
     * @return
     */
    private Loop2710 populateReportingCategory(List<String> loop2710Segments) {
        Loop2710 reportingCategory = Loop2710.builder().build();
        Loop2750 category = Loop2750.builder().build();
        Set<REF> categoryReferences = new HashSet<>();
        for(String seg: loop2710Segments){
            seg=seg.trim();
            List<String> elements = new ArrayList(Arrays.asList(seg.split("\\*")));
            String segmentName = elements.get(0);
            switch (segmentName){
                case "LX":
                    LX lx = lxSegmentHelper.populateLXSegment(elements);
                    reportingCategory.setReportingCategory(lx);
                case "N1":
                    N1 n1 = n1SegmentHelper.populateN1Segment(elements);
                    category.setReportingCategory(n1);
                    break;
                case "REF":
                    REF ref = refSegmentHelper.populateREFSegment(elements);
                    categoryReferences.add(ref);
                    break;
                case "DTP":
                    DTP dtp = dtpSegmentHelper.populateDTPSegment(elements);
                    category.setCategoryDate(dtp);
            }
        }
        category.setCategoryReference(categoryReferences);
        reportingCategory.setReportingCategoryDetails(category);
        return reportingCategory;
    }
}
