package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.N3;
import com.brihaspathee.zeus.edi.models.common.N4;
import com.brihaspathee.zeus.edi.models.common.NM1;
import com.brihaspathee.zeus.edi.models.common.PER;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2100D;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:15 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 *
 * Loop 2100D contains employer details
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2100DDataHelperImpl implements Loop2100DDataHelper {

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
     * Parses through the segments of all the Loop 2100Ds and creates the set of
     * individual loop 2100D object
     * @param loop2100DValues
     * @return
     */
    @Override
    public Set<Loop2100D> populateLoop2100D(List<String> loop2100DValues) {

        // Employer Details is not always present in the transaction
        // populate as NULL if not present in the transaction
        if(loop2100DValues.isEmpty()){
            return null;
        }
        // There can be more than one employer details loop, so all the details will be captured in the set
        Set<Loop2100D> memberEmployers = new HashSet<>();
        // This list will contain the segments (NM1, PER, N3 and N4) of one employer details
        // This list will be cleared after each employer detail segment is formed and added to the set
        List<String> employerData = new ArrayList<>();
        for(String employerDetailsSegment: loop2100DValues){
            // Trim of white spaces
            employerDetailsSegment=employerDetailsSegment.trim();
            List<String> elements = Arrays.asList(employerDetailsSegment.split("\\*"));
            // Get the segment name
            String segmentName = elements.get(0);
            // Employer details begins with NM1 segment
            if(segmentName.equals("NM1")){
                // If the segment is NM1, then an employer detail is available
                if(!employerData.isEmpty()){
                    // If the employer data list is not empty then there was a previous employer data segments
                    // these segments have to be parsed and the Loop2100D object has to be created by invoking
                    // the populateMemberEmployer method
                    Loop2100D memberEmployer = populateMemberEmployer(employerData);
                    // Once the Loop2100D object is created, it is added to the memberEmployers set
                    memberEmployers.add(memberEmployer);
                    // Once added to the set the employerData List is cleared so that the next employer detail can be
                    // created
                    employerData.clear();
                }
            }
            // This keeps adding the segment data to employer data list
            employerData.add(employerDetailsSegment);
        }
        // The last employer detail is created
        Loop2100D loop2100D = populateMemberEmployer(employerData);
        // The last employer details is added to the set
        memberEmployers.add(loop2100D);
        return memberEmployers;
    }

    /**
     * This method takes all the segments of a single Loop 2100D and creates the Loop2100D object
     * @param employerData
     * @return
     */
    private Loop2100D populateMemberEmployer(List<String> employerData){
        Loop2100D loop2100D = Loop2100D.builder().build();
        for(String employerDetailsSegment: employerData){
            employerDetailsSegment=employerDetailsSegment.trim();
            List<String> elements = Arrays.asList(employerDetailsSegment.split("\\*"));
            String segmentName = elements.get(0);
            switch (segmentName){
                case "NM1":
                    NM1 memberName = nm1SegmentHelper.populateNM1Segment(elements);
                    loop2100D.setMemberEmployerName(memberName);
                    break;
                case "PER":
                    PER communications = perSegmentHelper.populatePERSegment(elements);
                    loop2100D.setMemberEmployerCommunications(communications);
                    break;
                case "N3":
                    N3 memberAddressLines = n3SegmentHelper.populateN3Segment(elements);
                    loop2100D.setMemberEmployerAddressLine(memberAddressLines);
                    break;
                case "N4":
                    N4 memberCityStateZip = n4SegmentHelper.populateN4Segment(elements);
                    loop2100D.setMemberEmployerCityStateZip(memberCityStateZip);
                    break;
            }
        }
        return loop2100D;
    }
}
