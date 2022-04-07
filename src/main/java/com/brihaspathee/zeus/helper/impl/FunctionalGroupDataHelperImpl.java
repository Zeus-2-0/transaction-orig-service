package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.FunctionalGroup;
import com.brihaspathee.zeus.helper.interfaces.FunctionalGroupDataHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 06, April 2022
 * Time: 2:16 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FunctionalGroupDataHelperImpl implements FunctionalGroupDataHelper {
    @Override
    public FunctionalGroup createFunctionalGroup(List<String> functionalGroupSegments, int totalTransactions) {
        return null;
    }
}
