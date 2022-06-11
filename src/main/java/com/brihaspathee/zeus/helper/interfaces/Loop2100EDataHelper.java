package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.edi.models.enrollment.Loop2100A;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2100E;

import java.util.List;
import java.util.Set;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:11 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface Loop2100EDataHelper {

    Set<Loop2100E> populateLoop2100E(List<String> loop2100EValues);
}
