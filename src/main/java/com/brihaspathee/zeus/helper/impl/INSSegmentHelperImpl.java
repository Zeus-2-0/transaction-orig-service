package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.INS;
import com.brihaspathee.zeus.helper.interfaces.INSSegmentHelper;
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
 * Date: 23, May 2022
 * Time: 2:00 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class INSSegmentHelperImpl implements INSSegmentHelper {

    /**
     * Creates the INS Segment object and populates the values
     * @param segment
     * @return
     */
    @Override
    public INS populateINSSegment(List<String> insSegmentElements) {
        INS insSegment = INS.builder().build();
//        List<String> segmentDetails = new ArrayList<String>(Arrays.asList(segment.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = insSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()){
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    insSegment.setIns01(elementValue);
                    element++;
                    break;
                case 2:
                    insSegment.setIns02(elementValue);
                    element++;
                    break;
                case 3:
                    insSegment.setIns03(elementValue);
                    element++;
                    break;
                case 4:
                    insSegment.setIns04(elementValue);
                    element++;
                    break;
                case 5:
                    insSegment.setIns05(elementValue);
                    element++;
                    break;
                case 6:
                    insSegment.setIns06(elementValue);
                    element++;
                    break;
                case 7:
                    insSegment.setIns07(elementValue);
                    element++;
                    break;
                case 8:
                    insSegment.setIns08(elementValue);
                    element++;
                    break;
                case 9:
                    insSegment.setIns09(elementValue);
                    element++;
                    break;
                case 10:
                    insSegment.setIns10(elementValue);
                    element++;
                    break;
                case 11:
                    insSegment.setIns11(elementValue);
                    element++;
                    break;
                case 12:
                    insSegment.setIns12(elementValue);
                    element++;
                    break;
                case 13:
                    insSegment.setIns13(elementValue);
                    element++;
                    break;
                case 17:
                    insSegment.setIns17(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return insSegment;
    }
}
