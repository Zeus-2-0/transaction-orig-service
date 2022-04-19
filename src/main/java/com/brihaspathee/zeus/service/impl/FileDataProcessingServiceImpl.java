package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.edi.models.common.Interchange;
import com.brihaspathee.zeus.helper.interfaces.EDIFileDataHelper;
import com.brihaspathee.zeus.service.interfaces.FileDataProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, April 2022
 * Time: 2:06 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileDataProcessingServiceImpl implements FileDataProcessingService {

    private final EDIFileDataHelper ediFileDataHelper;

    @Override
    public void processFileData(String fileData) {
        List<String> fileSegments = new ArrayList<>(Arrays.asList(fileData.split("~")));
        Interchange interchange = ediFileDataHelper.processEDIFileData(fileSegments);
        log.info("Interchange Info:{}", interchange);
    }
}
