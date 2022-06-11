package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.edi.models.common.DSB;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 8:59 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface DSBSegmentHelper {

    DSB populateDSBSegment(List<String> dmgSegmentElements);
}
