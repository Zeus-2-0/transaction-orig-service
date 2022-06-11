package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.LE;
import com.brihaspathee.zeus.helper.interfaces.LESegmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 5:19 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class LESegmentHelperImpl implements LESegmentHelper {

    /**
     * Populates and creates LE Segment
     * @param lsElements
     * @return
     */
    @Override
    public LE populateLESegment(List<String> lsElements) {
        LE le = LE.builder().build();
        le.setLe01(lsElements.get(1));
        return le;
    }
}
