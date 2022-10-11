package com.brihaspathee.zeus.broker.producer;

import com.brihaspathee.zeus.domain.entity.PayloadTracker;
import com.brihaspathee.zeus.helper.interfaces.PayloadTrackerHelper;
import com.brihaspathee.zeus.message.MessageMetadata;
import com.brihaspathee.zeus.message.ZeusMessagePayload;
import com.brihaspathee.zeus.util.ZeusRandomStringGenerator;
import com.brihaspathee.zeus.web.model.RawTransactionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 07, October 2022
 * Time: 4:21 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.broker.producer
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class TransactionPublisher {

    /**
     * Kafka template to produce and send messages
     */
    private final KafkaTemplate<String, ZeusMessagePayload<RawTransactionDto>> kafkaTemplate;

    /**
     * ListenableFutureCallback class that is used after success or failure of publishing the message
     */
    private final TransactionPublisherCallback transactionPublisherCallback;

    /**
     * Object mapper to convert the payload to string
     */
    private final ObjectMapper objectMapper;

    /**
     * Payload tracker helper instance to create the payload tracker record
     */
    private final PayloadTrackerHelper payloadTrackerHelper;

    /**
     * Publish the message to Kafka Topic
     * @param rawTransactionDto
     */
    public void publishTransaction(RawTransactionDto rawTransactionDto) throws JsonProcessingException {
        String[] messageDestinations = {"DATA-TRANSFORM-SERVICE", "TRANSACTION-STORAGE-SERVICE"};
        String payloadId = ZeusRandomStringGenerator.randomString(15);
        ZeusMessagePayload<RawTransactionDto> messagePayload = ZeusMessagePayload.<RawTransactionDto>builder()
                .messageMetadata(MessageMetadata.builder()
                        .messageSource("TRANSACTION-ORIG-SERVICE")
                        .messageDestination(messageDestinations)
                        .messageCreationTimestamp(LocalDateTime.now())
                        .build())
                .payloadId(payloadId)
                .payload(rawTransactionDto)
                .build();
        transactionPublisherCallback.setRawTransactionDto(rawTransactionDto);
        createPayloadTracker(messagePayload);
        ProducerRecord<String, ZeusMessagePayload<RawTransactionDto>> producerRecord =
                buildProducerRecord(messagePayload);
        kafkaTemplate.send(producerRecord).addCallback(transactionPublisherCallback);
        log.info("After the send method is called");
    }

    /**
     * The method to build the producer record
     * @param messagePayload
     */
    private ProducerRecord<String, ZeusMessagePayload<RawTransactionDto>> buildProducerRecord(
            ZeusMessagePayload<RawTransactionDto> messagePayload){
        RecordHeader messageHeader = new RecordHeader("payload-id",
                "test payload id".getBytes());
        return new ProducerRecord<>("ZEUS.RAW.TRANSACTION.QUEUE",
                null,
                "test payload id 2",
                messagePayload,
                Arrays.asList(messageHeader));
    }

    /**
     * Create the payload tracker detail record
     * @param messagePayload
     * @throws JsonProcessingException
     */
    private void createPayloadTracker(ZeusMessagePayload<RawTransactionDto> messagePayload)
            throws JsonProcessingException {
        String payloadAsString = objectMapper.writeValueAsString(messagePayload);
        PayloadTracker payloadTracker = PayloadTracker.builder()
                .payloadDirectionTypeCode("OUTBOUND")
                .payload_key("TRANSACTION")
                .payload_key_type_code(messagePayload.getPayload().getZtcn())
                .payload(payloadAsString)
                .payloadId(messagePayload.getPayloadId())
                .sourceDestinations(StringUtils.join(
                        messagePayload.getMessageMetadata().getMessageDestination()))
                .build();
        payloadTrackerHelper.createdPayloadTracker(payloadTracker);
    }
}
