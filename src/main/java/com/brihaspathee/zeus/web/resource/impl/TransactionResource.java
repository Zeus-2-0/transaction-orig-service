package com.brihaspathee.zeus.web.resource.impl;

import com.brihaspathee.zeus.constants.ApiResponseConstants;
import com.brihaspathee.zeus.service.interfaces.FileDataProcessingService;
import com.brihaspathee.zeus.web.model.FileDetailDto;
import com.brihaspathee.zeus.web.model.FileResponseDto;
import com.brihaspathee.zeus.web.model.TransactionDto;
import com.brihaspathee.zeus.web.resource.interfaces.TransactionAPI;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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

    private final FileDataProcessingService fileDataProcessingService;

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

    @Override
    public ResponseEntity<ZeusApiResponse<FileResponseDto>> postFileDetails(FileDetailDto fileDetailDto) throws JsonProcessingException {
        // fileDataProcessingService.processFileData(fileDetailDto.getFileData());
        log.info("Got the file details:{}", fileDetailDto);
        fileDataProcessingService.processFileData(fileDetailDto.getFileData());
        FileResponseDto fileResponseDto = FileResponseDto.builder()
                .fileReceiptAck("File Receipt Ack")
                .build();
        ZeusApiResponse<FileResponseDto> apiResponse = ZeusApiResponse.<FileResponseDto>builder()
                .response(fileResponseDto)
                .message(ApiResponseConstants.SUCCESS)
                .status(HttpStatus.CREATED)
                .statusCode(201)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
