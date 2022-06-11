package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.N3;
import com.brihaspathee.zeus.helper.interfaces.N3SegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:39 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class N3SegmentHelperImpl implements N3SegmentHelper {

    /**
     * Populates and creates N3 Segment
     * @param n3SegmentElements
     * @return
     */
    @Override
    public N3 populateN3Segment(List<String> n3SegmentElements) {
        N3 n3Segment = N3.builder().build();
        Iterator<String> iterator = n3SegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    n3Segment.setN301(elementValue);
                    element++;
                    break;
                case 2:
                    n3Segment.setN302(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return n3Segment;
    }
}
