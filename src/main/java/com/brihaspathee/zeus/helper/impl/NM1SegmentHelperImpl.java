package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.NM1;
import com.brihaspathee.zeus.helper.interfaces.NM1SegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:33 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NM1SegmentHelperImpl implements NM1SegmentHelper {

    /**
     * Populates and creates NM1 Segment
     * @param nm1SegmentElements
     * @return
     */
    @Override
    public NM1 populateNM1Segment(List<String> nm1SegmentElements) {
        NM1 nm1Segment = NM1.builder().build();
//        ArrayList<String> segmentDetails = new ArrayList<String>(Arrays.asList(seg.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = nm1SegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    nm1Segment.setNm101(elementValue);
                    element++;
                    break;
                case 2:
                    nm1Segment.setNm102(elementValue);
                    element++;
                    break;
                case 3:
                    nm1Segment.setNm103(elementValue);
                    element++;
                    break;
                case 4:
                    nm1Segment.setNm104(elementValue);
                    element++;
                    break;
                case 5:
                    nm1Segment.setNm105(elementValue);
                    element++;
                    break;
                case 6:
                    nm1Segment.setNm106(elementValue);
                    element++;
                    break;
                case 7:
                    nm1Segment.setNm107(elementValue);
                    element++;
                    break;
                case 8:
                    nm1Segment.setNm108(elementValue);
                    element++;
                    break;
                case 9:
                    nm1Segment.setNm109(elementValue);
                    element++;
                    break;
                case 10:
                    nm1Segment.setNm110(elementValue);
                    element++;
                    break;
                case 11:
                    nm1Segment.setNm111(elementValue);
                    element++;
                    break;
                case 12:
                    nm1Segment.setNm112(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return nm1Segment;
    }
}
