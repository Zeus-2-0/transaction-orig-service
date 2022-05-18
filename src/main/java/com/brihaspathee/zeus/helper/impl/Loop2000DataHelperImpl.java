package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.enrollment.Loop2000;
import com.brihaspathee.zeus.helper.interfaces.Loop2000DataHelper;
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
        }
        return memberLoop;
    }
}
