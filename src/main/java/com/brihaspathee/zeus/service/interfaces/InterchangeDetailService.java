package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.domain.entity.InterchangeDetail;
import com.brihaspathee.zeus.edi.models.common.Interchange;
import com.brihaspathee.zeus.web.model.FileDetailDto;

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

    InterchangeDetail saveInterchangeDetail(FileDetailDto fileDetailDto, Interchange interchange);
}
