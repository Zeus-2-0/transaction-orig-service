package com.brihaspathee.zeus.consumer;

import com.brihaspathee.zeus.web.model.FileDetailDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 21, March 2022
 * Time: 11:26 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.consumer
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileInfoConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "ZEUS.FILE.STORAGE.TOPIC",groupId = "trans-orig-group")
    public void consumeFileInfo(String fileInfo) throws JsonProcessingException {
        log.info("Message from the topic: {}", fileInfo);
        FileDetailDto fileDetail = objectMapper.readValue(fileInfo, FileDetailDto.class);
        log.info("File Info Detail Object: {}", fileDetail);

    }
}
