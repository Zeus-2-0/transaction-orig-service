package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.edi.models.common.Interchange;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 05, April 2022
 * Time: 6:26 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface InterchangeDataHelper {

    Interchange populateInterchangeHeader(Interchange interchange, String segment);
    Interchange populateInterchangeTrailer(Interchange interchange, String segment);
}
