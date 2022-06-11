package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.*;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2300;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2310;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2320;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 10:12 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2300DataHelperImpl implements Loop2300DataHelper {

    /**
     * Creates and populates HD Segment
     */
    private final HDSegmentHelper hdSegmentHelper;

    /**
     * Creates and populates DTP Segment
     */
    private final DTPSegmentHelper dtpSegmentHelper;

    /**
     * Creates and populates AMT Segment
     */
    private final AMTSegmentHelper amtSegmentHelper;

    /**
     * Creates and populates REF Segment
     */
    private final REFSegmentHelper refSegmentHelper;

    /**
     * Creates and populates IDC Segment
     */
    private final IDCSegmentHelper idcSegmentHelper;

    /**
     * Creates and populated Loop 2310
     */
    private final Loop2310DataHelper loop2310DataHelper;

    /**
     * Creates and populates Loop 2320
     */
    private final Loop2320DataHelper loop2320DataHelper;

    /**
     * Creates and populates all the healthcare segments that are present in the transaction
     * @param healthCareSegments
     * @return
     */
    @Override
    public Set<Loop2300> populatedHealthCoverageSegments(List<String> healthCareSegments) {

        // A set that will contain all the health coverage loops that are present in the transaction
        Set<Loop2300> healthCoverageLoops = new HashSet<>();
        // The list that will contain the segments of the single health coverage segment
        List<String> healthCoverageSegments = new ArrayList<>();
        for(String healthCareSegment: healthCareSegments){
            // trim any white spance
            healthCareSegment=healthCareSegment.trim();
            // Get the segment name
            String [] segment = healthCareSegment.split("\\*");
            String segmentName = segment[0];
            if(segmentName.equals("HD")){
                // if the healthCoverageSegments list is not empty when encountering an HD Segment
                // means that there was already a health coverage segment
                if(!healthCoverageSegments.isEmpty()){
                    // Create the loop 2300 before we gather the data for the next health coverage loop
                    Loop2300 loop2300 = populateLoop2300(healthCoverageSegments);
                    //log.info("Loop 2300:{}", loop2300);
                    // Add the created object to healthCoverageLoops set
                    healthCoverageLoops.add(loop2300);
                    // Clear the health coverage segment list to make way for the next health coverage
                    healthCoverageSegments.clear();
                }
            }
            healthCoverageSegments.add(healthCareSegment);
        }

        // populate the last health coverage
        Loop2300 loop2300 = populateLoop2300(healthCoverageSegments);

        // add it to the set
        healthCoverageLoops.add(loop2300);
        //log.info("HealthCoverage Loop:{}", healthCoverageLoops);
        return healthCoverageLoops;
    }

    /**
     * Create the health coverage segment object
     * @param loop2300Segments
     * @return
     */
    private Loop2300 populateLoop2300(List<String> loop2300Segments){
        //log.info("Loop2300Segments:{}", loop2300Segments);
        // Creation of the health coverage loop
        Loop2300 healthCoverageLoop = Loop2300.builder().build();
        // An health coverage loop contains three sections
        // 1. Health Coverage Details

        List<String> healthCoverageSegments = new ArrayList<>();
        // 2. Provider Information
        List<String> providerSegments = new ArrayList<>();
        // 3. COB Information
        List<String> cobSegments = new ArrayList<>();
        // Initially set all the three to False
        AtomicBoolean healthCoverageSegment = new AtomicBoolean(false);
        AtomicBoolean providerInformation = new AtomicBoolean(false);
        AtomicBoolean cob= new AtomicBoolean(false);
        for(String loop2300Segment: loop2300Segments){
            // Trim any white space
            loop2300Segment=loop2300Segment.trim();
            // get segment name
            List<String> loop2300SegmentElements = new ArrayList<>(Arrays.asList(loop2300Segment.split("\\*")));
            String segmentName = loop2300SegmentElements.get(0);
            //log.info("segmentName:{}, providerInformation:{}", segmentName, providerInformation);
            if(segmentName.equals("HD")){
                // gathering data for Health care segment
                healthCoverageSegment.set(true);
                providerInformation.set(false);
                cob.set(false);
            }else if(segmentName.equals("LX")){
                // gathering data for provider information
                providerInformation.set(true);
                healthCoverageSegment.set(false);

                cob.set(false);
            }else if (segmentName.equals("COB")){
                // gathering data for COB
                cob.set(true);
                healthCoverageSegment.set(false);
                providerInformation.set(false);

            }

            if (healthCoverageSegment.get()){
                healthCoverageSegments.add(loop2300Segment);
            }else if(providerInformation.get()){
                providerSegments.add(loop2300Segment);
            }else if(cob.get()){
                cobSegments.add(loop2300Segment);
            }
        }
        // Create HealhtCoverage object
        populateHealthCoverage(healthCoverageLoop, healthCoverageSegments);
        //log.info("Provider Segments:{}",providerSegments);
        // Create provider object
        Set<Loop2310> providers = loop2310DataHelper.populateProviderInformation(providerSegments);
        healthCoverageLoop.setProviders(providers);
        // Create COB Object
        Set<Loop2320> cobs = loop2320DataHelper.populateCOBs(cobSegments);
        healthCoverageLoop.setCobs(cobs);
        return healthCoverageLoop;
    }

    /**
     * Populate the single health coverage loop
     * @param healthCoverageLoop
     * @param loop2300Segments
     */
    private void populateHealthCoverage(Loop2300 healthCoverageLoop, List<String> loop2300Segments) {
        //Loop2300 healthCoverageLoop = Loop2300.builder().build();
        Set<DTP> healthCoverageDates = new HashSet<>();
        Set<AMT> policyAmounts = new HashSet<>();
        Set<REF> policyNumbers = new HashSet<>();
        for(String loop2300Segment: loop2300Segments){
            loop2300Segment=loop2300Segment.trim();
            List<String> healthCoverageSegments = new ArrayList<>(Arrays.asList(loop2300Segment.split("\\*")));
            String segmentName = healthCoverageSegments.get(0);
            switch (segmentName){
                case "HD":
                    HD hd = hdSegmentHelper.populateHDSegment(healthCoverageSegments);
                    healthCoverageLoop.setHealthCoverage(hd);
                    break;
                case "DTP":
                    DTP coverageDate = dtpSegmentHelper.populateDTPSegment(healthCoverageSegments);
                    healthCoverageDates.add(coverageDate);
                    break;
                case "AMT":
                    AMT policyAmount = amtSegmentHelper.populateAMTSegment(healthCoverageSegments);
                    policyAmounts.add(policyAmount);
                    break;
                case "REF":
                    REF ref = refSegmentHelper.populateREFSegment(healthCoverageSegments);
                    String qualifier = healthCoverageSegments.get(1);
                    if(qualifier.equals("QQ")){
                        healthCoverageLoop.setPriorCoverageMonths(ref);
                    }else{
                        policyNumbers.add(ref);
                    }
                    break;
                case "IDC":
                    IDC idCard = idcSegmentHelper.populateIDCSegment(healthCoverageSegments);
                    healthCoverageLoop.setIdentificationCard(idCard);
            }
        }
        healthCoverageLoop.setHealthCoverageDates(healthCoverageDates);
        healthCoverageLoop.setHealthCoveragePolicyAmounts(policyAmounts);
        healthCoverageLoop.setHealthCoveragePolicyNumbers(policyNumbers);
    }
}
