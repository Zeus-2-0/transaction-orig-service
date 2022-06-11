package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.LX;
import com.brihaspathee.zeus.helper.interfaces.LXSegmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 3:23 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class LXSegmentHelperImpl implements LXSegmentHelper {

    /**
     * Creates and populates LX Segment
     * @param lxSegments
     * @return
     */
    @Override
    public LX populateLXSegment(List<String> lxSegments) {
        LX lx = LX.builder().build();
        lx.setLx01(lxSegments.get(1));
        return lx;
    }
}
