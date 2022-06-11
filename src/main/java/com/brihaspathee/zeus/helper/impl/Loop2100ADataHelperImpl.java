package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.*;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2100A;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:14 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 *
 * Loop2100A contains Member demographics
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2100ADataHelperImpl implements Loop2100ADataHelper {

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
     * Populates and creates DMG segment
     */
    private final DMGSegmentHelper dmgSegmentHelper;

    /**
     * Populates and creates EC segment
     */
    private final ECSegmentHelper ecSegmentHelper;

    /**
     * Populates and creates ICM segment
     */
    private final ICMSegmentHelper icmSegmentHelper;

    /**
     * Populates and creates AMT segment
     */
    private final AMTSegmentHelper amtSegmentHelper;

    /**
     * Populates and creates HLH segment
     */
    private final HLHSegmentHelper hlhSegmentHelper;

    /**
     * Populates and creates LUI segment
     */
    private final LUISegmentHelper luiSegmentHelper;

    /**
     * Creates the 2100A Loop
     * @param loop2100AValues
     * @return
     */
    @Override
    public Loop2100A populateLoop2100A(List<String> loop2100AValues) {
        Loop2100A loop2100A = Loop2100A.builder().build();
        // There can be multiple EC segment, the set is to contain those segments
        Set<EC> employmentClasses = new HashSet<>();
        // There can be multiple policy amounts, the set is to contain those segments
        Set<AMT> policyAmounts = new HashSet<>();
        // There can be multiple member languages, the set is to contain those segments
        Set<LUI> memberLanguages = new HashSet<>();
        // Loop through each segment that was received for loop 2100A
        for (String memberDemographicSegment: loop2100AValues){
            memberDemographicSegment = memberDemographicSegment.trim();
            List<String> elements = Arrays.asList(memberDemographicSegment.split("\\*"));
            String segmentName = elements.get(0);
            switch (segmentName){
                case "NM1":
                    NM1 memberName = nm1SegmentHelper.populateNM1Segment(elements);
                    loop2100A.setMemberName(memberName);
                    break;
                case "PER":
                    PER memberCommunications = perSegmentHelper.populatePERSegment(elements);
                    loop2100A.setMemberCommunication(memberCommunications);
                    break;
                case "N3":
                    N3 memberAddressLines = n3SegmentHelper.populateN3Segment(elements);
                    loop2100A.setMemberAddressLine(memberAddressLines);
                    break;
                case "N4":
                    N4 memberCityStateZip = n4SegmentHelper.populateN4Segment(elements);
                    loop2100A.setMemberCityStateZip(memberCityStateZip);
                    break;
                case "DMG":
                    DMG memberdemographics = dmgSegmentHelper.populateDMGSegment(elements);
                    loop2100A.setMemberDemographics(memberdemographics);
                    break;
                case "EC":
                    EC employmentClass = ecSegmentHelper.populateECSegment(elements);
                    employmentClasses.add(employmentClass);
                    break;
                case "ICM":
                    ICM memberIncome = icmSegmentHelper.populateICMSegment(elements);
                    loop2100A.setMemberIncome(memberIncome);
                    break;
                case "AMT":
                    AMT policyAmount = amtSegmentHelper.populateAMTSegment(elements);
                    policyAmounts.add(policyAmount);
                    break;
                case "HLH":
                    HLH healthInfo = hlhSegmentHelper.populateHLHSegment(elements);
                    loop2100A.setMemberHealthInformation(healthInfo);
                    break;
                case "LUI":
                    LUI language = luiSegmentHelper.populateLUISegment(elements);
                    memberLanguages.add(language);
            }
        }
        loop2100A.setEmploymentClass(employmentClasses);
        loop2100A.setMemberPolicyAmounts(policyAmounts);
        loop2100A.setMemberLanguages(memberLanguages);
        return loop2100A;
    }
}
