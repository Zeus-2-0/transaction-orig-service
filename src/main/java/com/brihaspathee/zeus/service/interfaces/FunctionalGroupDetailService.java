package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.domain.entity.FunctionalGroupDetail;
import com.brihaspathee.zeus.domain.entity.InterchangeDetail;
import com.brihaspathee.zeus.edi.models.common.FunctionalGroup;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, July 2022
 * Time: 7:52 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface FunctionalGroupDetailService {

    /**
     * Takes the interchange detail that the functional groups belongs to and saves it
     * @param interchangeDetail
     * @param functionalGroup
     * @return
     */
    FunctionalGroupDetail saveFunctionalGroupDetail(InterchangeDetail interchangeDetail, FunctionalGroup functionalGroup);
}
