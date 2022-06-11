package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.AMT;
import com.brihaspathee.zeus.helper.interfaces.AMTSegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 4:02 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AMTSegmentHelperImpl implements AMTSegmentHelper {

    /**
     * Populates and creates the AMT Segment
     * @param amtSegmentElements
     * @return
     */
    @Override
    public AMT populateAMTSegment(List<String> amtSegmentElements) {
        AMT policyAmount = AMT.builder().build();
//        ArrayList<String> segmentDetails = new ArrayList<String>(Arrays.asList(seg.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = amtSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            elementValue = elementValue.replaceAll("~", "");
            switch (element){
                case 1:
                    policyAmount.setAmt01(elementValue);
                    element++;
                    break;
                case 2:
                    policyAmount.setAmt02(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return policyAmount;
    }
}
