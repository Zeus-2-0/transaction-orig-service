package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.ICM;
import com.brihaspathee.zeus.helper.interfaces.ICMSegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:51 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ICMSegmentHelperImpl implements ICMSegmentHelper {

    /**
     * Populates and creates ICM Segment
     * @param icmSegmentElements
     * @return
     */
    @Override
    public ICM populateICMSegment(List<String> icmSegmentElements) {
        ICM memberIncome = ICM.builder().build();
//        ArrayList<String> segmentDetails = new ArrayList<String>(Arrays.asList(seg.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = icmSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    memberIncome.setIcm01(elementValue);
                    element++;
                    break;
                case 2:
                    memberIncome.setIcm02(elementValue);
                    element++;
                    break;
                case 3:
                    memberIncome.setIcm03(elementValue);
                    element++;
                    break;
                case 4:
                    memberIncome.setIcm04(elementValue);
                    element++;
                    break;
                case 5:
                    memberIncome.setIcm05(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return memberIncome;
    }
}
