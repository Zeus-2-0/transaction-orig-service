package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.IDC;
import com.brihaspathee.zeus.helper.interfaces.IDCSegmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 10, June 2022
 * Time: 10:15 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class IDCSegmentHelperImpl implements IDCSegmentHelper {

    /**
     * Populates and creates IDC Segment
     * @param idcElements
     * @return
     */
    @Override
    public IDC populateIDCSegment(List<String> idcElements) {
        IDC idCard = IDC.builder().build();
        Iterator<String> iterator = idcElements.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            String elementValue = iterator.next();
            switch (element){
                case 1:
                    idCard.setIdc01(elementValue);
                    element++;
                    break;
                case 2:
                    idCard.setIdc02(elementValue);
                    element++;
                    break;
                case 3:
                    idCard.setIdc03(elementValue);
                    element++;
                    break;
                case 4:
                    idCard.setIdc04(elementValue);
                    element++;
                    break;
                default:
                    element++;
            }
        }
        return idCard;
    }
}
