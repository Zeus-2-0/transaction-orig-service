package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.edi.models.common.DTP;
import com.brihaspathee.zeus.edi.models.common.QTY;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 15, April 2022
 * Time: 3:40 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface QTYSegmentHelper {
    QTY populateQTYSegment(List<String> qtySegmentElements);
}
