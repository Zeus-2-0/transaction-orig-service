package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.HLH;
import com.brihaspathee.zeus.helper.interfaces.HLHSegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 4:04 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HLHSegmentHelperImpl implements HLHSegmentHelper {

    /**
     * Populates and creates HLH Segment
     * @param hlhSegmentElements
     * @return
     */
    @Override
    public HLH populateHLHSegment(List<String> hlhSegmentElements) {
        HLH healthInfo = HLH.builder().build();
//        ArrayList<String> segmentDetails = new ArrayList<String>(Arrays.asList(seg.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = hlhSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    healthInfo.setHlh01(elementValue);
                    element++;
                    break;
                case 2:
                    healthInfo.setHlh02(elementValue);
                    element++;
                    break;
                case 3:
                    healthInfo.setHlh03(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return healthInfo;
    }
}
