package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.REF;
import com.brihaspathee.zeus.helper.interfaces.REFSegmentHelper;
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
 * Date: 15, April 2022
 * Time: 3:41 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class REFSegmentHelperImpl implements REFSegmentHelper {
    @Override
    public REF populateREFSegment(List<String> refSegmentElements) {
        REF ref = REF.builder().build();
//        List<String> segmentDetails = new ArrayList<String>(Arrays.asList(refSegment.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = refSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    ref.setRef01(elementValue);
                    element++;
                    break;
                case 2:
                    ref.setRef02(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return ref;
    }
}
