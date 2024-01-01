package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.dto.transaction.FileDetailDto;
import com.brihaspathee.zeus.web.model.FileResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, April 2022
 * Time: 2:05 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface FileDataProcessingService {

    FileResponseDto processFileData(FileDetailDto fileDetailDto) throws JsonProcessingException;
}
