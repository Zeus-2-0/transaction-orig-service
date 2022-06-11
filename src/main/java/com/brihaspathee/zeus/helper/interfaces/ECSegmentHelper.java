package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.edi.models.common.EC;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:43 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface ECSegmentHelper {

    EC populateECSegment(List<String> ecSegmentElements);
}
