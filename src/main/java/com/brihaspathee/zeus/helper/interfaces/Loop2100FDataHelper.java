package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.edi.models.enrollment.Loop2100A;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2100F;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:12 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface Loop2100FDataHelper {

    Loop2100F populateLoop2100F(List<String> loop2100FValues);
}
