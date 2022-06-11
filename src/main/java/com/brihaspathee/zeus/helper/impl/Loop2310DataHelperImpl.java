package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.*;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2310;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 10:27 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2310DataHelperImpl implements Loop2310DataHelper {

    /**
     * Creates and populates NM1 Segment
     */
    private final NM1SegmentHelper nm1SegmentHelper;

    /**
     * Creates amd populates PER Segment
     */
    private final PERSegmentHelper perSegmentHelper;

    /**
     * Creates and populated N3 Segment
     */
    private final N3SegmentHelper n3SegmentHelper;

    /**
     *  Created and populates N4 Segment
     */
    private final N4SegmentHelper n4SegmentHelper;

    /**
     * Creates and populates LX Segment
     */
    private final LXSegmentHelper lxSegmentHelper;

    /**
     * Creates and populates PLA segment
     */
    private final PLASegmentHelper plaSegmentHelper;

    @Override
    public Set<Loop2310> populateProviderInformation(List<String> providerSegments) {
        // Provider information is not always present in the transaction
        // Return null if not present in the transaction
        if(providerSegments.isEmpty()){
            return null;
        }
        // This set will contain all the providers present in the transaction
        Set<Loop2310> providers = new HashSet<>();
        // This list will contain the provider segments for one provider loop
        List<String> providerData = new ArrayList<>();
        for(String providerSegment: providerSegments){
            // Trim any white spaces in the segment
            providerSegment=providerSegment.trim();
            // Get the segment name
            String [] segment = providerSegment.split("\\*");
            String segmentName = segment[0];
            if(segmentName.equals("LX")){
                // If provider data is not empty then we create provider information before we parse through the
                // next provider
                if(!providerData.isEmpty()){
                    // Create the provider information
                    Loop2310 provider = populateProvider(providerData);
                    // Add the provider to the set
                    providers.add(provider);
                    // Clear the list for the next provider
                    providerData.clear();
                }
            }
            providerData.add(providerSegment);
        }
        // Create the last provider in the transaction
        Loop2310 loop2310 = populateProvider(providerData);
        // Add to the provider set
        providers.add(loop2310);
        return providers;
    }

    /**
     * Populate the single provider loop
     * @param providerData
     * @return
     */
    private Loop2310 populateProvider(List<String> providerData) {
        Loop2310 loop2310 = Loop2310.builder().build();
        for(String providerSegment: providerData){
            providerSegment=providerSegment.trim();
            List<String> providerElements = new ArrayList<>(Arrays.asList(providerSegment.split("\\*")));
            String segmentName = providerElements.get(0);
            switch (segmentName){
                case "LX":
                    LX providerInfo = lxSegmentHelper.populateLXSegment(providerElements);
                    loop2310.setProviderInformation(providerInfo);
                    break;
                case "NM1":
                    NM1 name = nm1SegmentHelper.populateNM1Segment(providerElements);
                    loop2310.setProviderName(name);
                    break;
                case "PER":
                    PER commn = perSegmentHelper.populatePERSegment(providerElements);
                    loop2310.setProviderCommunications(commn);
                    break;
                case "N3":
                    N3 addressLines = n3SegmentHelper.populateN3Segment(providerElements);
                    loop2310.setProviderAddress(addressLines);
                    break;
                case "N4":
                    N4 cityStateZip = n4SegmentHelper.populateN4Segment(providerElements);
                    loop2310.setProviderCityStateZip(cityStateZip);
                    break;
                case "PLA":
                    PLA providerChangeReason = plaSegmentHelper.populatePLASegment(providerElements);
                    loop2310.setProviderChangeReason(providerChangeReason);
                    break;
            }
        }
        return loop2310;
    }
}
