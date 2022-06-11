package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.N4;
import com.brihaspathee.zeus.helper.interfaces.N4SegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:41 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class N4SegmentHelperImpl implements N4SegmentHelper {

    /**
     * Populates and creates N4 Segment
     * @param n4SegmentElements
     * @return
     */
    @Override
    public N4 populateN4Segment(List<String> n4SegmentElements) {
        N4 citySegment = N4.builder().build();
//        ArrayList<String> segmentDetails = new ArrayList<String>(Arrays.asList(seg.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = n4SegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    citySegment.setN401(elementValue);
                    element++;
                    break;
                case 2:
                    citySegment.setN402(elementValue);
                    element++;
                    break;
                case 3:
                    citySegment.setN403(elementValue);
                    element++;
                    break;
                case 4:
                    citySegment.setN404(elementValue);
                    element++;
                    break;
                case 5:
                    citySegment.setN405(elementValue);
                    element++;
                    break;
                case 6:
                    citySegment.setN406(elementValue);
                    element++;
                    break;
                case 7:
                    citySegment.setN407(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return citySegment;
    }
}
