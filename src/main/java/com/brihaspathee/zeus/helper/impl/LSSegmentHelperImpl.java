package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.LS;
import com.brihaspathee.zeus.helper.interfaces.LSSegmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 5:03 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class LSSegmentHelperImpl implements LSSegmentHelper {

    /**
     * Creates and populates the LS Segment
     * @param lsSegmentElements
     * @return
     */
    @Override
    public LS populateLSSegment(List<String> lsSegmentElements) {
        LS ls = LS.builder().build();
        ls.setLs01(lsSegmentElements.get(1));
        return ls;
    }
}
