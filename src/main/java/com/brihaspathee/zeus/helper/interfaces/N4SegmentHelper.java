package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.edi.models.common.N4;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:38 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface N4SegmentHelper {

    N4 populateN4Segment(List<String> n4SegmentElements);
}
