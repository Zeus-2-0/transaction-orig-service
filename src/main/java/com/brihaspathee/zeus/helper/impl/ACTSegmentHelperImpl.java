package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.ACT;
import com.brihaspathee.zeus.helper.interfaces.ACTSegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 26, April 2022
 * Time: 11:40 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ACTSegmentHelperImpl implements ACTSegmentHelper {

    /**
     * Method to populate the ACT object
     * @param actSegmentElements act segment elements
     * @return ACT Object
     */
    @Override
    public ACT populateACTSegment(List<String> actSegmentElements) {
        ACT act = ACT.builder().build();
        // Get the individual elements of the account
//        List<String> segmentDetails = new ArrayList<String>(Arrays.asList(actSegment.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = actSegmentElements.iterator();
        int element = 0;
        // Iterate through individual elements
        // As per 834 Implementation guide only element 1 and element 6 will be populated
        while (iterator.hasNext()){
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    act.setAct01(elementValue);
                    element++;
                    break;
                case 6:
                    act.setAct06(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return act;
    }
}
