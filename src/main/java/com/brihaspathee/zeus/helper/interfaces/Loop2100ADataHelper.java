package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.edi.models.enrollment.Loop2100A;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:09 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface Loop2100ADataHelper {

    Loop2100A populateLoop2100A(List<String> loop2100AValues);
}
