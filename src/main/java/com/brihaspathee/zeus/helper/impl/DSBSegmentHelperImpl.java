package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.DSB;
import com.brihaspathee.zeus.helper.interfaces.DSBSegmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 8:59 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class DSBSegmentHelperImpl implements DSBSegmentHelper {

    /**
     * Populates and creates the DSB Segment
     * @param dmgSegmentElements
     * @return
     */
    @Override
    public DSB populateDSBSegment(List<String> dmgSegmentElements) {
        DSB dsb = DSB.builder().build();
        //List<String> segmentDetails = new ArrayList<String>(Arrays.asList(dsbSegment.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = dmgSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()){
            String elementValue = iterator.next();
            elementValue = elementValue.replaceAll("~", "");
            switch (element){
                case 1:
                    dsb.setDsb01(elementValue);
                    element++;
                    break;
                case 7:
                    dsb.setDsb07(elementValue);
                    element++;
                    break;
                case 8:
                    dsb.setDsb08(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return dsb;
    }
}
