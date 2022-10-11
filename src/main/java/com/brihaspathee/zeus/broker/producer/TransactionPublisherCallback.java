package com.brihaspathee.zeus.broker.producer;

import com.brihaspathee.zeus.message.ZeusMessagePayload;
import com.brihaspathee.zeus.web.model.RawTransactionDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 07, October 2022
 * Time: 4:56 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.broker.producer
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class TransactionPublisherCallback implements ListenableFutureCallback<SendResult<String, ZeusMessagePayload<RawTransactionDto>>> {

    /**
     * The transaction detail that was published
     */
    private RawTransactionDto rawTransactionDto;

    /**
     * Invoked if there is any failure in publishing the message
     * @param ex
     */
    @Override
    public void onFailure(Throwable ex) {
        log.info("The message failed to publish");
    }

    /**
     * Invoked after publishing the message
     * @param result
     */
    @Override
    public void onSuccess(SendResult<String, ZeusMessagePayload<RawTransactionDto>> result) {
        log.info("The message successfully published");
    }
}
