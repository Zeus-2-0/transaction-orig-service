package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.DTP;
import com.brihaspathee.zeus.helper.interfaces.DTPSegmentHelper;
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
 * Time: 3:42 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DTPSegmentHelperImpl implements DTPSegmentHelper {
    @Override
    public DTP populateDTPSegment(List<String> dtpSegmentElements) {
        DTP dtp = DTP.builder().build();
//        List<String> segmentDetails = new ArrayList<String>(Arrays.asList(dtpSegment.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = dtpSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    dtp.setDtp01(elementValue);
                    element++;
                    break;
                case 2:
                    dtp.setDtp02(elementValue);
                    element++;
                    break;
                case 3:
                    dtp.setDtp03(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return dtp;
    }
}
