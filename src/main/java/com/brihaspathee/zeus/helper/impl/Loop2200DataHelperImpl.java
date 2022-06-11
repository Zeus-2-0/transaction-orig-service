package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.DSB;
import com.brihaspathee.zeus.edi.models.common.DTP;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2200;
import com.brihaspathee.zeus.helper.interfaces.DSBSegmentHelper;
import com.brihaspathee.zeus.helper.interfaces.DTPSegmentHelper;
import com.brihaspathee.zeus.helper.interfaces.Loop2200DataHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 8:58 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2200DataHelperImpl implements Loop2200DataHelper {

    /**
     * Creates and populates the DSB Segment
     */
    private final DSBSegmentHelper dsbSegmentHelper;

    /**
     * Create and populates the DTP Segment
     */
    private final DTPSegmentHelper dtpSegmentHelper;

    /**
     * Populates and created the disability segment objects received in the transaction
     * @param disabilitySegments
     * @return
     */
    @Override
    public Set<Loop2200> populateDisabilityInformation(List<String> disabilitySegments) {
        // Disability segments are not always present in the transaction
        // if there are no disability segments present then NULL will be returned
        if(disabilitySegments.isEmpty()){
            return null;
        }
        // The set that contains the set of all the disabilities that are present in the transaction
        Set<Loop2200> disabilities = new HashSet<>();
        // List of elements for a single disability segment
        List<String> disabilityData = new ArrayList<>();

        for(String disabilitySegment: disabilitySegments){
            // Trim and remove the whitespaces of the segment
            disabilitySegment=disabilitySegment.trim();
            // split the segment into its elements to get the segment name
            String [] segment = disabilitySegment.split("\\*");
            String segmentName = segment[0];
            if(segmentName.equals("DSB")){
                // If this the first DSB segment then the disability data will be empty
                // If this is not the first DSB segment then the disability data will not be empty
                if(!disabilityData.isEmpty()){
                    // If the disability data is not empty then we create the disability segment that has been accumulated
                    Loop2200 disability = populateDisabilityDetail(disabilityData);
                    // Once the disability object is created it will be added to the set
                    disabilities.add(disability);
                    // Once it is added it has to be cleared before the data for the next data is added
                    disabilityData.clear();
                }
            }
            // Adding the segment elements to the list
            disabilityData.add(disabilitySegment);
        }
        // Creating the last disability segment
        Loop2200 loop2200 = populateDisabilityDetail(disabilityData);
        // Adding the created disability to the set
        disabilities.add(loop2200);
        return disabilities;
    }

    /**
     * Creating the single Loop 2200
     * @param disabilityData
     * @return
     */
    private Loop2200 populateDisabilityDetail(List<String> disabilityData) {
        Loop2200 disabilityDetail = Loop2200.builder().build();
        Set<DTP> disabilityEligibilityDates = new HashSet<>();
        for(String disabilitySegment: disabilityData){
            disabilitySegment=disabilitySegment.trim();
            List<String> disabilityElements = new ArrayList<>(Arrays.asList(disabilitySegment.split("\\*")));
            String segmentName = disabilityElements.get(0);
            switch (segmentName){
                case "DSB":
                    DSB disabilityInformation = dsbSegmentHelper.populateDSBSegment(disabilityElements);
                    disabilityDetail.setDisabilityInformation(disabilityInformation);
                    break;
                case "DTP":
                    DTP eligibilityDate = dtpSegmentHelper.populateDTPSegment(disabilityElements);
                    disabilityEligibilityDates.add(eligibilityDate);
                    break;
            }
        }
        if(!disabilityEligibilityDates.isEmpty()){
            disabilityDetail.setDisabilityEligibilityDates(disabilityEligibilityDates);
        }
        return disabilityDetail;
    }
}
