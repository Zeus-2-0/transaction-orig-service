package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.DTP;
import com.brihaspathee.zeus.edi.models.common.INS;
import com.brihaspathee.zeus.edi.models.common.REF;
import com.brihaspathee.zeus.edi.models.enrollment.*;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 29, April 2022
 * Time: 2:55 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2000DataHelperImpl implements Loop2000DataHelper {

    /**
     * INS Segment helper that creates the INS object
     */
    private final INSSegmentHelper insSegmentHelper;

    /**
     * REF Segment helper to populate the REF segment object
     */
    private final REFSegmentHelper refSegmentHelper;

    /**
     * DTP Segment helper to populate the DTP Segment Helper
     */
    private final DTPSegmentHelper dtpSegmentHelper;

    /**
     * Populates and created the 2100 loops
     */
    private final Loop2100DataHelper loop2100DataHelper;

    /**
     * Populates and creates the 2200 loop
     */
    private final Loop2200DataHelper loop2200DataHelper;

    /**
     * Populates and creates the 2300 loop
     */
    private final Loop2300DataHelper loop2300DataHelper;

    /**
     * Populates and creates the 2700 loop
     */
    private final Loop2700DataHelper loop2700DataHelper;

    /**
     * NM1 Qualifiers for identifying the appropriate loop 2100
     */
    private static final List<String> nm1Qualifiers = Arrays.asList("IL", "70", "31", "36", "M8", "S3", "QD", "6Y",
            "9K", "E1", "EI", "EXS", "GB", "J6", "LR", "S1", "TZ", "X4", "45");

    /**
     * This method gathers all the member details in a transaction and creates a Set
     * @param memberSegments
     * @return
     */
    @Override
    public Set<Loop2000> populateMembers(List<String> memberSegments) {
        // Set that will hold all the members in the transaction
        Set<Loop2000> members = new HashSet<>();
        // List that will hold all the segments of an individual member
        List<String> member = new ArrayList<>();
        // Loop through the individual segments to populate the member detail object
        for (String memberSegment: memberSegments){
            // Trim the whitespace in the segment
            memberSegment = memberSegment.trim();
            // Get the individual elements of the segment and get the name of the segment
            String segmentName = memberSegment.split("\\*")[0];
            // Gather all the segments of each member.
            // The information of a member in an 834 is between the INS and LE segments
            // if we reached a LE segment that means it is the end of the details of the member
            // so we populate the member details
            if(segmentName.equals("LE")){
                member.add(memberSegment);
                // Now all the segments are added, so populate the member information
                Loop2000 memberInfo = populateMemberInfo(member);
                // Add the member info to the set of members in the transaction
                members.add(memberInfo);
                // clear the list of the previous member so that the next member can be populated and created
                member.clear();
            }else{
                // if it is not an LE segment keep collecting the segments in the list
                member.add(memberSegment);
            }
        }
        return members;
    }

    /**
     * This method takes the segments of an individual member in a transaction and creates the data from the following
     * Loops
     * Loop 2000 - Member Basic Details
     * Loop 2100A - Member Demographics
     * Loop 2100B - Incorrect Member Name
     * Loop 2100C - Member Mailing Address
     * Loop 2100D - Employer Details
     * Loop 2100E - School Details
     * Loop 2100F - Custodial Parent
     * Loop 2100G - Responsible Person
     * Loop 2100H - Drop Off Location
     * Loop 2200 - Disability Information
     * Loop 2300 - Health Coverage Details
     * Loop 2310 - Provider Information
     * Loop 2320 - Coordination of Benefits
     * Loop 2330 - COBs related Entity
     * Loop 2700 - Reporting Categories
     *
     * @param member
     * @return
     */
    private Loop2000 populateMemberInfo(List<String> member) {
        Loop2000 memberLoop = Loop2000.builder().build();
        // List to contain all the 2100 segments
        List<String> loop2100Segments = new ArrayList<>();
        // List to contain all the 2200 segments
        List<String> loop2200Segments = new ArrayList<>();
        // List to contain all the 2300 segments
        List<String> loop2300Segments = new ArrayList<>();
        // List to contain all the 2700 segments
        List<String> loop2700Segments = new ArrayList<>();

        // Boolean that indicates that we are currently looping through the basic member details
        AtomicBoolean memberLevelDetail = new AtomicBoolean(false);
        // Boolean that indicates that we are currently looping through the member demographics
        AtomicBoolean loop2100 = new AtomicBoolean(false);
        // Boolean that indicates that we are currently looping through the disability information
        AtomicBoolean loop2200 = new AtomicBoolean(false);
        // Boolean that indicates that we are currently looping through the health coverage
        AtomicBoolean loop2300 = new AtomicBoolean(false);
        // Boolean that indicates that we are currently looping through the reporting categories
        AtomicBoolean loop2700 = new AtomicBoolean(false);

        // Loop through the individual segments and collect the data in the respective lists
        for(String memberSegment: member){
            // trim down any white spaces
            memberSegment = memberSegment.trim();
            // Get the individual elements of the segment
            String [] elements = memberSegment.split("\\*");
            // Get the segment name
            String segmentName = elements[0];
            // If the segment is NM1, this will have the NM1 qualifier
            String nm1Qualifier = elements[1];
            // Check if the segment name is equal to NM1
            if(segmentName.equals("INS")){
                memberLevelDetail.set(true);
                // since the member level detail i.e. loop 2000 is being iterated, set the rest of the loops to false
                loop2100.set(false);
                loop2200.set(false);
                loop2300.set(false);
                loop2700.set(false);
            }else if (segmentName.equals("NM1") && nm1Qualifiers.contains(nm1Qualifier)){
                // if the segment is NM1 and the qualifier is one of the ones in the nm1Qualifiers list, then
                // we are processing one of the loop 2100s (A, B, C, D, E, F, G and H)
                loop2100.set(true);
                // set false for rest of the loops
                memberLevelDetail.set(false);
                loop2200.set(false);
                loop2300.set(false);
                loop2700.set(false);

            }else if(segmentName.equals("DSB")){
                // if the segment name is DSB then we are processing loop 2200
                // so loop2100 boolean is set to true
                loop2200.set(true);
                // the rest are set to false
                memberLevelDetail.set(false);
                loop2100.set(false);
                loop2300.set(false);
                loop2700.set(false);
            }else if (segmentName.equals("HD")){
                // if the segment name is "HD" then we are processing health coverage loop2300
                // so loop2300 boolean is set to true
                loop2300.set(true);
                // the rest are set to false
                memberLevelDetail.set(false);
                loop2100.set(false);
                loop2200.set(false);
                loop2700.set(false);
            }else if (segmentName.equals("LS")){
                // if the segment name is "LS" then we are processing the reporting categories
                // so loop2700 is set to true
                loop2700.set(true);
                // the rest are set to false
                memberLevelDetail.set(false);
                loop2100.set(false);
                loop2200.set(false);
                loop2300.set(false);
            }
            if(memberLevelDetail.get()){
                // send each segment of Loop 2000 to this method to create each segment object for the loop
                populateMemberLevelDetail(memberLoop, elements, segmentName);
            }else if (loop2100.get()){
                // Gather all the segments of loop 2100 in a list
                loop2100Segments.add(memberSegment);
            }else if (loop2200.get()){
                // Gather all the segments of loop 2200 in a list
                loop2200Segments.add(memberSegment);
            }else if (loop2300.get()){
                // Gather all the segments of loop 2300 in a list
                loop2300Segments.add(memberSegment);
            }else if (loop2700.get()){
                // Gather all the segments of loop 2700 in a list
                loop2700Segments.add(memberSegment);
            }
        }
        // Collect and populate all the loop 2100s in a object
        Loop2100 memberData = loop2100DataHelper.populateLoop2100(loop2100Segments);
        // Get the member demographic information and set it in the member loop
        memberLoop.setMemberDemographics(memberData.getMemberDemographics());
        // Get the incorrect member demographic information and set it in the member loop
        memberLoop.setIncorrectMemberDemographics(memberData.getIncorrectMemberDemographics());
        // Get the member mailing address and set it in the member loop
        memberLoop.setMemberMailingAddress(memberData.getMemberMailingAddress());
        // Get the member employer information and set it in the member loop
        memberLoop.setEmployers(memberData.getEmployers());
        // Get the member school information and set it in the member loop
        memberLoop.setSchools(memberData.getSchools());
        // Get the custodial parent information and set it in the member loop
        memberLoop.setCustodialParent(memberData.getCustodialParent());
        // Get the responsible person information and set it in the member loop
        memberLoop.setResponsiblePersons(memberData.getResponsiblePersons());
        // Get the drop off location and set it in the member loop
        memberLoop.setDropOffLocation(memberData.getDropOffLocation());
        // Create and populate disability segments if present in the transaction
        Set<Loop2200> disabilities = loop2200DataHelper.populateDisabilityInformation(loop2200Segments);
        memberLoop.setDisabilities(disabilities);
        // Create and populate the health coverage segments
        Set<Loop2300> healthCoverages = loop2300DataHelper.populatedHealthCoverageSegments(loop2300Segments);
        memberLoop.setHealthCoverages(healthCoverages);
        // Create and populate the reporting categories
        Loop2700 reportingCategories = loop2700DataHelper.populateLoop2700(loop2700Segments);
        memberLoop.setReportingCategories(reportingCategories);
        return memberLoop;
    }

    private void populateMemberLevelDetail(Loop2000 memberLoop, String [] segmentElements, String segmentName) {
        List<String> elements = Arrays.asList(segmentElements);
        // check if the segment is INS
        if(segmentName.equals("INS")){
            INS insured = insSegmentHelper.populateINSSegment(elements);
            memberLoop.setMemberDetail(insured);
        }else if (segmentName.equals("REF")){
            REF ref = refSegmentHelper.populateREFSegment(elements);
            if(ref.getRef01().equals("0F")){
                // this is the exchange subscriber id
                memberLoop.setSubscriberIdentifier(ref);
            }else if (ref.getRef01().equals("1L")){
                // this is the member's group policy id
                memberLoop.setGroupPolicyId(ref);
            }else{
                memberLoop.getMemberSupplementalIdentifiers().add(ref);
            }
        }else if (segmentName.equals("DTP")){
            DTP dtp = dtpSegmentHelper.populateDTPSegment(elements);
            memberLoop.getMemberLevelDates().add(dtp);
        }
    }
}
