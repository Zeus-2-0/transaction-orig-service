package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.domain.entity.InterchangeDetail;
import com.brihaspathee.zeus.dto.transaction.FileDetailDto;
import com.brihaspathee.zeus.edi.models.common.Interchange;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 29, June 2022
 * Time: 3:58 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface InterchangeDetailService {

    /**
     * Save the interchange detail
     * @param fileDetailDto
     * @param interchange
     * @return
     */
    InterchangeDetail saveInterchangeDetail(FileDetailDto fileDetailDto, Interchange interchange);

    /**
     * Delete the interchange detail
     * @param icn
     */
    void deleteInterchangeDetail(String icn);

    /**
     * Clean up the entire database
     */
    void deleteAll();
}
