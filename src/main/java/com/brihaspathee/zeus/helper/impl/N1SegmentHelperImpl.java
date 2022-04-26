package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.N1;
import com.brihaspathee.zeus.helper.interfaces.N1SegmentHelper;
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
 * Time: 11:38 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class N1SegmentHelperImpl implements N1SegmentHelper {

    /**
     * The method to populate the N1 Segment
     * @param n1Segment
     * @return
     */
    @Override
    public N1 populateN1Segment(String n1Segment) {
        N1 n1 = N1.builder().build();
        // Get the individual elements
        List<String> segmentDetails = new ArrayList<String>(Arrays.asList(n1Segment.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = segmentDetails.iterator();
        int element = 0;
        // Iterate through each element and populate the N1 object
        while (iterator.hasNext()){
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    n1.setN101(elementValue);
                    element++;
                    break;
                case 2:
                    n1.setN102(elementValue);
                    element++;
                    break;
                case 3:
                    n1.setN103(elementValue);
                    element++;
                    break;
                case 4:
                    n1.setN104(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return n1;
    }
}
