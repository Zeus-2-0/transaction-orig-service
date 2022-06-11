package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.DMG;
import com.brihaspathee.zeus.helper.interfaces.DMGSegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:47 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DMGSegmentHelperImpl implements DMGSegmentHelper {

    /**
     * Populates and creates DMG Segment
     * @param dmgSegmentElements
     * @return
     */
    @Override
    public DMG populateDMGSegment(List<String> dmgSegmentElements) {
        DMG demographics = DMG.builder().build();
//        ArrayList<String> segmentDetails = new ArrayList<String>(Arrays.asList(seg.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = dmgSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    demographics.setDmg01(elementValue);
                    element++;
                    break;
                case 2:
                    demographics.setDmg02(elementValue);
                    element++;
                    break;
                case 3:
                    demographics.setDmg03(elementValue);
                    element++;
                    break;
                case 4:
                    demographics.setDmg04(elementValue);
                    element++;
                    break;
                case 5:
                    Set<String> raceCodes = getRaceEthnicityCodes(elementValue);
                    demographics.setDmg05(raceCodes);
                    element++;
                    break;
                case 6:
                    demographics.setDmg06(elementValue);
                    element++;
                    break;
                case 10:
                    demographics.setDmg10(elementValue);
                    element++;
                    break;
                case 11:
                    demographics.setDmg11(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return demographics;
    }

    /**
     * Race and ethnicity code comes as composite elements in the EDI file
     * @param elementValue
     * @return
     */
    private Set<String> getRaceEthnicityCodes(String elementValue) {
        List<String> raceAndEthnicityCodes = new ArrayList<String>(Arrays.asList(elementValue.split(":")));
        Set<String> raceCodes = new HashSet<>();
        for(String raceCode: raceAndEthnicityCodes){
            if(!raceCode.equals("RET")){
                raceCodes.add(raceCode);
            }
        }
        return raceCodes;
    }
}
