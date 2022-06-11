package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.EC;
import com.brihaspathee.zeus.helper.interfaces.ECSegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:49 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ECSegmentHelperImpl implements ECSegmentHelper {

    /**
     * Populates and creates EC Segment
     * @param ecSegmentElements
     * @return
     */
    @Override
    public EC populateECSegment(List<String> ecSegmentElements) {
        EC employmentClass = EC.builder().build();
//        ArrayList<String> segmentDetails = new ArrayList<String>(Arrays.asList(seg.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = ecSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    employmentClass.setEc01(elementValue);
                    element++;
                    break;
                case 2:
                    employmentClass.setEc02(elementValue);
                    element++;
                    break;
                case 3:
                    employmentClass.setEc03(elementValue);
                    element++;
                    break;

                default:
                    element++;
            }
        }
        return employmentClass;
    }
}
