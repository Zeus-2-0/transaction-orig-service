package com.brihaspathee.zeus.web.resource.impl;

import com.brihaspathee.zeus.constants.ApiResponseConstants;
import com.brihaspathee.zeus.dto.transaction.FileDetailDto;
import com.brihaspathee.zeus.service.interfaces.FileDataProcessingService;
import com.brihaspathee.zeus.service.interfaces.InterchangeDetailService;
import com.brihaspathee.zeus.web.model.FileResponseDto;
import com.brihaspathee.zeus.web.model.TransactionDto;
import com.brihaspathee.zeus.web.resource.interfaces.TransactionAPI;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 18, March 2022
 * Time: 11:39 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.resource.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionResource implements TransactionAPI {

    /**
     * File Data processing service instance
     */
    private final FileDataProcessingService fileDataProcessingService;

    /**
     * Interchange Detail service instance
     */
    private final InterchangeDetailService interchangeDetailService;

    /**
     * Get transaction by transaction id
     * @param transactionId
     * @return
     */
    @Override
    public ResponseEntity<ZeusApiResponse<TransactionDto>> getTransactionById(String transactionId) {
        ZeusApiResponse<TransactionDto> apiResponse = ZeusApiResponse.<TransactionDto>builder()
                .response(TransactionDto.builder()
                        .transactionId("Test Transaction Id")
                        .transactionSK("Test Transaction SK")
                        .build())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Post file details
     * @param fileDetailDto
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public ResponseEntity<ZeusApiResponse<FileResponseDto>> postFileDetails(FileDetailDto fileDetailDto) throws JsonProcessingException {
        // fileDataProcessingService.processFileData(fileDetailDto.getFileData());
        log.info("Got the file details:{}", fileDetailDto);
        FileResponseDto fileResponseDto =fileDataProcessingService.processFileData(fileDetailDto);
        ZeusApiResponse<FileResponseDto> apiResponse = ZeusApiResponse.<FileResponseDto>builder()
                .response(fileResponseDto)
                .message(ApiResponseConstants.SUCCESS)
                .status(HttpStatus.CREATED)
                .statusCode(201)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    /**
     * Clean up the data
     * @param icn
     * @return
     */
    @Override
    public ResponseEntity<ZeusApiResponse<String>> cleanUp(String icn) {
        interchangeDetailService.deleteInterchangeDetail(icn);
        ZeusApiResponse<String> apiResponse = ZeusApiResponse.<String>builder()
                .response("Data deleted successfully")
                .message(ApiResponseConstants.SUCCESS)
                .status(HttpStatus.NO_CONTENT)
                .statusCode(204)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }

    /**
     * Clean up the entire DB
     * @return
     */
    @Override
    public ResponseEntity<ZeusApiResponse<String>> cleanUp() {
        interchangeDetailService.deleteAll();
        ZeusApiResponse<String> apiResponse = ZeusApiResponse.<String>builder()
                .response("Request deleted successfully")
                .statusCode(204)
                .status(HttpStatus.NO_CONTENT)
                .developerMessage(ApiResponseConstants.SUCCESS)
                .message(ApiResponseConstants.SUCCESS_REASON)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }
}
