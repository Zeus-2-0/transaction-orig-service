package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.Interchange;
import com.brihaspathee.zeus.helper.interfaces.InterchangeDataHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 05, April 2022
 * Time: 6:27 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InterchangeDataHelperImpl implements InterchangeDataHelper {
    @Override
    public Interchange populateInterchangeHeader(Interchange interchange, String segment) {
        List<String> elements = new ArrayList<String>(Arrays.asList(segment.split("\\*")));
        interchange.setIsa01(elements.get(1));
        interchange.setIsa02(elements.get(2));
        interchange.setIsa03(elements.get(3));
        interchange.setIsa04(elements.get(4));
        interchange.setIsa05(elements.get(5));
        interchange.setIsa06(elements.get(6));
        interchange.setIsa07(elements.get(7));
        interchange.setIsa08(elements.get(8));
        interchange.setIsa09(elements.get(9));
        interchange.setIsa10(elements.get(10));
        interchange.setIsa11(elements.get(11));
        interchange.setIsa12(elements.get(12));
        interchange.setIsa13(elements.get(13));
        interchange.setIsa14(elements.get(14));
        interchange.setIsa15(elements.get(15));
        interchange.setIsa16(elements.get(16));
        return interchange;
    }

    @Override
    public Interchange populateInterchangeTrailer(Interchange interchange, String segment) {
        List<String> elements = new ArrayList<String>(Arrays.asList(segment.split("\\*")));
        interchange.setIea01(elements.get(1));
        interchange.setIea02(elements.get(2));
        return interchange;
    }
}
