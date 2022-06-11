package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.enrollment.*;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 2:30 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2100DataHelperImpl implements Loop2100DataHelper {

    /**
     * Populates and creates the loop2100A object
     */
    private final Loop2100ADataHelper loop2100ADataHelper;
    /**
     * Populates and creates the loop2100B object
     */
    private final Loop2100BDataHelper loop2100BDataHelper;
    /**
     * Populates and creates the loop2100C object
     */
    private final Loop2100CDataHelper loop2100CDataHelper;
    /**
     * Populates and creates the loop2100D object
     */
    private final Loop2100DDataHelper loop2100DDataHelper;
    /**
     * Populates and creates the loop2100E object
     */
    private final Loop2100EDataHelper loop2100EDataHelper;
    /**
     * Populates and creates the loop2100F object
     */
    private final Loop2100FDataHelper loop2100FDataHelper;
    /**
     * Populates and creates the loop2100G object
     */
    private final Loop2100GDataHelper loop2100GDataHelper;
    /**
     * Populates and creates the loop2100H object
     */
    private final Loop2100HDataHelper loop2100HDataHelper;

    /**
     * Static list that contains all the responsible person qualifiers that can be received in the transaction
     */
    private static final List<String> responsiblePersonQualifier  = Arrays.asList("QD", "6Y", "9K", "E1", "EI",
            "EXS", "GB", "J6", "LR", "S1", "TZ", "X4");

    /**
     * Populate the details of all the Loop 2100 segments
     * @param loop2100Segments
     * @return
     */
    @Override
    public Loop2100 populateLoop2100(List<String> loop2100Segments) {

        Loop2100 loop2100Values = Loop2100.builder().build();
        // The list that contains the values for the Loop2100A
        List<String> loop2100AValues = new ArrayList<>();
        // The list that contains the values for the Loop2100B
        List<String> loop2100BValues = new ArrayList<>();
        // The list that contains the values for the Loop2100C
        List<String> loop2100CValues = new ArrayList<>();
        // The list that contains the values for the Loop2100D
        List<String> loop2100DValues = new ArrayList<>();
        // The list that contains the values for the Loop2100E
        List<String> loop2100EValues = new ArrayList<>();
        // The list that contains the values for the Loop2100F
        List<String> loop2100FValues = new ArrayList<>();
        // The list that contains the values for the Loop2100G
        List<String> loop2100GValues = new ArrayList<>();
        // The list that contains the values for the Loop2100H
        List<String> loop2100HValues = new ArrayList<>();

        // Boolean that indicates that we are currently looping through the Loop 2100A
        AtomicBoolean loop2100A = new AtomicBoolean(false);
        // Boolean that indicates that we are currently looping through the Loop 2100B
        AtomicBoolean loop2100B = new AtomicBoolean(false);
        // Boolean that indicates that we are currently looping through the Loop 2100C
        AtomicBoolean loop2100C = new AtomicBoolean(false);
        // Boolean that indicates that we are currently looping through the Loop 2100D
        AtomicBoolean loop2100D = new AtomicBoolean(false);
        // Boolean that indicates that we are currently looping through the Loop 2100E
        AtomicBoolean loop2100E = new AtomicBoolean(false);
        // Boolean that indicates that we are currently looping through the Loop 2100F
        AtomicBoolean loop2100F = new AtomicBoolean(false);
        // Boolean that indicates that we are currently looping through the Loop 2100G
        AtomicBoolean loop2100G = new AtomicBoolean(false);
        // Boolean that indicates that we are currently looping through the Loop 2100H
        AtomicBoolean loop2100H = new AtomicBoolean(false);

        // Loop through the individual segments and collect the data in the respective lists
        for(String loop2100Segment: loop2100Segments){
            // trim down any white spaces
            loop2100Segment=loop2100Segment.trim();
            // Get the individual elements of the segment
            List<String> elements = Arrays.asList(loop2100Segment.split("\\*"));
            //String segmentName = segment[0];
            // Get the qualifier for each segment
            String segmentQualifier = elements.get(1);

            if(segmentQualifier.equals("IL") || segmentQualifier.equals("74")){
                // If the qualifier is "IL" or "74" then we are dealing with Loop2100A
                // Set the loop 2100A boolean to true and the rest to false
                loop2100A.set(true);
                // set the rest of the loops to false
                loop2100B.set(false);
                loop2100C.set(false);
                loop2100D.set(false);
                loop2100E.set(false);
                loop2100F.set(false);
                loop2100G.set(false);
                loop2100H.set(false);

            } else if(segmentQualifier.equals("70")){
                // If the qualifier is "70" then we are dealing with Loop2100B
                // Set the loop 2100B boolean to true and the rest to false
                loop2100B.set(true);
                // set the rest of the loops to false
                loop2100A.set(false);
                loop2100C.set(false);
                loop2100D.set(false);
                loop2100E.set(false);
                loop2100F.set(false);
                loop2100G.set(false);
                loop2100H.set(false);
            } else if(segmentQualifier.equals("31")){
                // If the qualifier is "31" then we are dealing with Loop2100C
                // Set the loop 2100C boolean to true and the rest to false
                loop2100C.set(true);
                // set the rest of the loops to false
                loop2100A.set(false);
                loop2100B.set(false);
                loop2100D.set(false);
                loop2100E.set(false);
                loop2100F.set(false);
                loop2100G.set(false);
                loop2100H.set(false);

            } else if(segmentQualifier.equals("36")){
                // If the qualifier is "36" then we are dealing with Loop2100D
                // Set the loop 2100D boolean to true and the rest to false
                loop2100D.set(true);
                // set the rest of the loops to false
                loop2100A.set(false);
                loop2100B.set(false);
                loop2100C.set(false);
                loop2100E.set(false);
                loop2100F.set(false);
                loop2100G.set(false);
                loop2100H.set(false);

            } else if(segmentQualifier.equals("M8")){
                // If the qualifier is "M8" then we are dealing with Loop2100E
                // Set the loop 2100E boolean to true and the rest to false
                loop2100E.set(true);
                // set the rest of the loops to false
                loop2100A.set(false);
                loop2100B.set(false);
                loop2100C.set(false);
                loop2100D.set(false);
                loop2100F.set(false);
                loop2100G.set(false);
                loop2100H.set(false);

            } else if(segmentQualifier.equals("S3")){
                // If the qualifier is "S3" then we are dealing with Loop2100F
                // Set the loop 2100F boolean to true and the rest to false
                loop2100F.set(true);
                // set the rest of the loops to false
                loop2100A.set(false);
                loop2100B.set(false);
                loop2100C.set(false);
                loop2100D.set(false);
                loop2100E.set(false);
                loop2100G.set(false);
                loop2100H.set(false);

            } else if(responsiblePersonQualifier.contains(segmentQualifier)){
                // If the qualifier is among one the responsible person qualifiers then we are dealing with Loop2100G
                // Set the loop 2100G boolean to true and the rest to false
                loop2100G.set(true);
                // set the rest of the loops to false
                loop2100A.set(false);
                loop2100B.set(false);
                loop2100C.set(false);
                loop2100D.set(false);
                loop2100E.set(false);
                loop2100F.set(false);
                loop2100H.set(false);

            } else if(segmentQualifier.equals("45")){
                // If the qualifier is "45" then we are dealing with Loop2100H
                // Set the loop 2100H boolean to true and the rest to false
                loop2100H.set(true);
                // set the rest of the loops to false
                loop2100A.set(false);
                loop2100B.set(false);
                loop2100C.set(false);
                loop2100D.set(false);
                loop2100E.set(false);
                loop2100F.set(false);
                loop2100G.set(false);
            }
            if(loop2100A.get()){
                loop2100AValues.add(loop2100Segment);
            }else if(loop2100B.get()){
                loop2100BValues.add(loop2100Segment);
            }else if(loop2100C.get()){
                loop2100CValues.add(loop2100Segment);
            }else if(loop2100D.get()){
                loop2100DValues.add(loop2100Segment);
            }else if(loop2100E.get()){
                loop2100EValues.add(loop2100Segment);
            }else if(loop2100F.get()){
                loop2100FValues.add(loop2100Segment);
            }else if(loop2100G.get()){
                loop2100GValues.add(loop2100Segment);
            }else if(loop2100H.get()){
                loop2100HValues.add(loop2100Segment);
            }

        }
        /**
         * Populating member demographics from loop 2100A
         */
        Loop2100A memberDemographics = loop2100ADataHelper.populateLoop2100A(loop2100AValues);
        loop2100Values.setMemberDemographics(memberDemographics);
        /**
         * Populating incorrect member name details from Loop 2100B
         */
        Loop2100B incorrectMemberName = loop2100BDataHelper.populateLoop2100B(loop2100BValues);
        loop2100Values.setIncorrectMemberDemographics(incorrectMemberName);
        /**
         * Populating the member's mailing address
         */
        Loop2100C memberMailingAddress = loop2100CDataHelper.populateLoop2100C(loop2100CValues);
        loop2100Values.setMemberMailingAddress(memberMailingAddress);

        /**
         * Populating the member's employer information
         */
        Set<Loop2100D> memberEmployers = loop2100DDataHelper.populateLoop2100D(loop2100DValues);
        loop2100Values.setEmployers(memberEmployers);

        /**
         * Populating the member's school information
         */
        Set<Loop2100E> memberSchools = loop2100EDataHelper.populateLoop2100E(loop2100EValues);
        loop2100Values.setSchools(memberSchools);

        /**
         * Populating the custodial parent information
         */
        Loop2100F custodialParent = loop2100FDataHelper.populateLoop2100F(loop2100FValues);
        loop2100Values.setCustodialParent(custodialParent);

        /**
         * Populating the responsible person information
         */
        Set<Loop2100G> responsiblePersons = loop2100GDataHelper.populateLoop2100G(loop2100GValues);
        loop2100Values.setResponsiblePersons(responsiblePersons);

        /**
         * Populating the dropoff location
         */
        Loop2100H dropOffLocation = loop2100HDataHelper.populateLoop2100H(loop2100HValues);
        loop2100Values.setDropOffLocation(dropOffLocation);
        return loop2100Values;
    }
}
