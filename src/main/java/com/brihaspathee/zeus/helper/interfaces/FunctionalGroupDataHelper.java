package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.edi.models.common.FunctionalGroup;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 06, April 2022
 * Time: 2:14 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface FunctionalGroupDataHelper {

    FunctionalGroup createFunctionalGroup(List<String> functionalGroupSegments, int totalTransactions);
}
