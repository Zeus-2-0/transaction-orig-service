package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.edi.models.common.LS;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 5:02 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface LSSegmentHelper {

    LS populateLSSegment(List<String> lsSegmentElements);
}
