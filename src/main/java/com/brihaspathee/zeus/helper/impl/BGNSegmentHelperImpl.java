package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.BGN;
import com.brihaspathee.zeus.helper.interfaces.BGNSegmentHelper;
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
public class BGNSegmentHelperImpl implements BGNSegmentHelper {
    @Override
    public BGN populateBeginSegment(List<String> bgnSegmentElements) {
        BGN beginSegment = BGN.builder().build();
//        List<String> segmentDetails = new ArrayList<String>(Arrays.asList(bgnSegment.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = bgnSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()){
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    beginSegment.setBgn01(elementValue);
                    element++;
                    break;
                case 2:
                    beginSegment.setBgn02(elementValue);
                    element++;
                    break;
                case 3:
                    beginSegment.setBgn03(elementValue);
                    element++;
                    break;
                case 4:
                    beginSegment.setBgn04(elementValue);
                    element++;
                    break;
                case 5:
                    beginSegment.setBgn05(elementValue);
                    element++;
                    break;
                case 6:
                    beginSegment.setBgn06(elementValue);
                    element++;
                    break;
                case 8:
                    beginSegment.setBgn08(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return beginSegment;
    }
}
