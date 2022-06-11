package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.COB;
import com.brihaspathee.zeus.helper.interfaces.COBSegmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 2:15 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class COBSegmentHelperImpl implements COBSegmentHelper {

    /**
     * Creates and populates the COB Segment
     * @param cobElements cob elements
     * @return COB object
     */
    @Override
    public COB populateCOBSegment(List<String> cobElements) {
        COB cobSegment = COB.builder().build();
        Iterator<String> iterator = cobElements.iterator();
        int element = 0;
        while (iterator.hasNext()){
            String elementValue = iterator.next();
            elementValue = elementValue.replaceAll("~", "");
            switch (element){
                case 1:
                    cobSegment.setCob01(elementValue);
                    element++;
                    break;
                case 2:
                    cobSegment.setCob02(elementValue);
                    element++;
                    break;
                case 3:
                    cobSegment.setCob03(elementValue);
                    element++;
                    break;
                case 4:
                    cobSegment.setCob04(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return cobSegment;
    }
}
