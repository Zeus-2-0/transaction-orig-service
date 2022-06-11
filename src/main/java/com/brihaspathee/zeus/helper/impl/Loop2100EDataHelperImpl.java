package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.N3;
import com.brihaspathee.zeus.edi.models.common.N4;
import com.brihaspathee.zeus.edi.models.common.NM1;
import com.brihaspathee.zeus.edi.models.common.PER;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2100E;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:16 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 *
 * Loop2100E contains the members school details
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2100EDataHelperImpl implements Loop2100EDataHelper {

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
     * @param loop2100EValues
     * @return
     */
    @Override
    public Set<Loop2100E> populateLoop2100E(List<String> loop2100EValues) {

        // Member School Details is not always present in the transaction
        // populate as NULL if not present in the transaction
        if(loop2100EValues.isEmpty()){
            return null;
        }
        // There can be more than one school details loop, so all the details will be captured in the set
        Set<Loop2100E> memberSchools = new HashSet<>();
        // This list will contain the segments (NM1, PER, N3 and N4) of one school detail
        // This list will be cleared after each school detail segment is formed and added to the set
        List<String> schoolData = new ArrayList<>();
        for(String schoolDetail: loop2100EValues){
            // Trim of white spaces
            schoolDetail=schoolDetail.trim();
            List<String> elements = Arrays.asList(schoolDetail.split("\\*"));
            // Get the segment name
            String segmentName = elements.get(0);
            // School details begins with NM1 segment
            if(segmentName.equals("NM1")){
                // If the segment is NM1, then a school detail is available
                if(!schoolData.isEmpty()){
                    // If the school data list is not empty then there was a previous school data segments
                    // these segments have to be parsed and the Loop2100E object has to be created by invoking
                    // the populateMemberSchool method
                    Loop2100E memberEmployer = populateMemberSchool(schoolData);
                    // Once the Loop2100E object is created, it is added to the memberSchools set
                    memberSchools.add(memberEmployer);
                    // Once added to the set the schoolData List is cleared so that the next school detail can be
                    // created
                    schoolData.clear();
                }
            }
            // This keeps adding the segment data to school data list
            schoolData.add(schoolDetail);
        }
        // The last school detail is created
        Loop2100E loop2100E = populateMemberSchool(schoolData);
        // The last school details is added to the set
        memberSchools.add(loop2100E);
        return memberSchools;
    }


    /**
     * This method takes the segments of the single Loop2100E and creates the Loop2100E object
     * @param schoolData
     * @return
     */
    private Loop2100E populateMemberSchool(List<String> schoolData){
        Loop2100E loop2100E = Loop2100E.builder().build();
        for(String memberSchoolDetail: schoolData){
            memberSchoolDetail=memberSchoolDetail.trim();
            List<String> elements = Arrays.asList(memberSchoolDetail.split("\\*"));
            String segmentName = elements.get(0);
            switch (segmentName){
                case "NM1":
                    NM1 name = nm1SegmentHelper.populateNM1Segment(elements);
                    loop2100E.setMemberSchoolName(name);
                    break;
                case "PER":
                    PER communication = perSegmentHelper.populatePERSegment(elements);
                    loop2100E.setMemberSchoolCommunications(communication);
                    break;
                case "N3":
                    N3 addressLines = n3SegmentHelper.populateN3Segment(elements);
                    loop2100E.setMemberSchoolAddressLine(addressLines);
                    break;
                case "N4":
                    N4 cityStateZip = n4SegmentHelper.populateN4Segment(elements);
                    loop2100E.setMemberSchoolCityStateZip(cityStateZip);
                    break;
            }
        }
        return loop2100E;
    }
}
