package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.LUI;
import com.brihaspathee.zeus.helper.interfaces.LUISegmentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 4:06 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LUISegmentHelperImpl implements LUISegmentHelper {

    /**
     * Populates and creates the LUI Segment
     * @param luiSegmentElements
     * @return
     */
    @Override
    public LUI populateLUISegment(List<String> luiSegmentElements) {
        LUI language = LUI.builder().build();
//        ArrayList<String> segmentDetails = new ArrayList<String>(Arrays.asList(seg.split("\\*")));
        //log.info("Begin Segment:{}", segmentDetails);
        Iterator<String> iterator = luiSegmentElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    language.setLui01(elementValue);
                    element++;
                    break;
                case 2:
                    language.setLui02(elementValue);
                    element++;
                    break;
                case 3:
                    language.setLui03(elementValue);
                    element++;
                    break;
                case 4:
                    language.setLui04(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return language;
    }
}
