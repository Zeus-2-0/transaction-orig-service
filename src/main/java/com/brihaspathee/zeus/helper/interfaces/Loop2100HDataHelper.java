package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.edi.models.enrollment.Loop2100A;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2100H;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:13 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface Loop2100HDataHelper {

    Loop2100H populateLoop2100H(List<String> loop2100HValues);
}
