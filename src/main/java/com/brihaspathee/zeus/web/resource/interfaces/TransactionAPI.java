package com.brihaspathee.zeus.web.resource.interfaces;

import com.brihaspathee.zeus.web.model.FileDetailDto;
import com.brihaspathee.zeus.web.model.FileResponseDto;
import com.brihaspathee.zeus.web.model.TransactionDto;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, March 2022
 * Time: 5:11 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.resource.interfaces
 * To change this template use File | Settings | File and Code Template
 */
@RequestMapping("api/v1/trans-orig")
@Validated
public interface TransactionAPI {

    /**
     * Return the transaction of the transaction id that is passed in as input
     * @param transactionId
     * @return
     */
    @Operation(
            method = "GET",
            description = "Get the details of the transaction by its id",
            tags = {"transaction-origination"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the details of the transaction",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ZeusApiResponse.class))
                            }
                    )
            }
    )
    @GetMapping("/{transactionId}")
    ResponseEntity<ZeusApiResponse<TransactionDto>> getTransactionById(@PathVariable("transactionId") String transactionId);

    /**
     * This method will receive the details of the file with the transactions.
     * @param fileDetailDto
     * @return
     * @throws JsonProcessingException
     */
    @Operation(
            method = "POST",
            description = "Receive the details of the file with all the transactions in the file",
            tags = {"transaction-origination"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully received the details of the file and started to process",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ZeusApiResponse.class))
                            }
                    )
            }
    )
    @PostMapping
    ResponseEntity<ZeusApiResponse<FileResponseDto>> postFileDetails(@RequestBody FileDetailDto fileDetailDto) throws JsonProcessingException;
}
