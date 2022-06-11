package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.PLA;
import com.brihaspathee.zeus.helper.interfaces.PLASegmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 3:22 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class PLASegmentHelperImpl implements PLASegmentHelper {

    /**
     * Creates and populates the PLA Segment
     * @param plaSegmentElements
     * @return
     */
    @Override
    public PLA populatePLASegment(List<String> plaSegmentElements) {
        PLA plaSegment = PLA.builder().build();
        Iterator<String> iterator = plaSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()){
            String elementValue = iterator.next();
            elementValue = elementValue.replaceAll("~", "");
            switch (element){
                case 1:
                    plaSegment.setPla01(elementValue);
                    element++;
                    break;
                case 2:
                    plaSegment.setPla02(elementValue);
                    element++;
                    break;
                case 3:
                    plaSegment.setPla03(elementValue);
                    element++;
                    break;
                case 5:
                    plaSegment.setPla05(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return plaSegment;
    }
}
