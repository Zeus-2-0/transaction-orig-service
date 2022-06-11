package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.PER;
import com.brihaspathee.zeus.helper.interfaces.PERSegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:35 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PERSegmentHelperImpl implements PERSegmentHelper {

    /**
     * Populates and creates PER Segment
     * @param perSegmentElements
     * @return
     */
    @Override
    public PER populatePERSegment(List<String> perSegmentElements) {
        PER perSegment = PER.builder().build();
//        ArrayList<String> segmentDetails = new ArrayList<String>(Arrays.asList(seg.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = perSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    perSegment.setPer01(elementValue);
                    element++;
                    break;
                case 3:
                    perSegment.setPer03(elementValue);
                    element++;
                    break;
                case 4:
                    perSegment.setPer04(elementValue);
                    element++;
                    break;
                case 5:
                    perSegment.setPer05(elementValue);
                    element++;
                    break;
                case 6:
                    perSegment.setPer06(elementValue);
                    element++;
                    break;
                case 7:
                    perSegment.setPer07(elementValue);
                    element++;
                    break;
                case 8:
                    perSegment.setPer08(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return perSegment;
    }
}
