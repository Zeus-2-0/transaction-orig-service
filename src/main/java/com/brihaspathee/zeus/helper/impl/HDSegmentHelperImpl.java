package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.HD;
import com.brihaspathee.zeus.helper.interfaces.HDSegmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 10:15 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class HDSegmentHelperImpl implements HDSegmentHelper {

    /**
     * Populates and creates the HD Segment
     * @param hdElements
     * @return
     */
    @Override
    public HD populateHDSegment(List<String> hdElements) {
        HD healthCoverage = HD.builder().build();
        Iterator<String> iterator = hdElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    healthCoverage.setHd01(elementValue);
                    element++;
                    break;
                case 3:
                    healthCoverage.setHd03(elementValue);
                    element++;
                    break;
                case 4:
                    healthCoverage.setHd04(elementValue);
                    element++;
                    break;
                case 5:
                    healthCoverage.setHd05(elementValue);
                    element++;
                    break;
                case 9:
                    healthCoverage.setHd09(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return healthCoverage;
    }
}
