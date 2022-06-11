package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.edi.models.enrollment.Loop2200;

import java.util.List;
import java.util.Set;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 8:56 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface Loop2200DataHelper {

    Set<Loop2200> populateDisabilityInformation(List<String> disabilitySegments);
}
