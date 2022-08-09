package com.bluepi.loan.controller;

import com.bluepi.loan.base.IntegratorConstants;
import com.bluepi.loan.processor.LoanProcessorCreate;
import com.bluepi.loan.processor.LoanProcessorUpdate;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author dell
 */
@Controller("/loan")
public class LoanApplicationController {

    private Logger log = LoggerFactory.getLogger(LoanApplicationController.class);

    /**
     * @param payload for create Loan Application
     * @return response code with application id and uuid
     */
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Post("/application")
    public CompletableFuture<Map<String, Object>> createLoan(@Body @NotNull Map<String, Object> payload, @Header("correlationId") String correlationID) {
        MDC.put(IntegratorConstants.UNIQUE_IDENTIFICATION_ID, correlationID);
        log.info("create loan Request thread started with  payload = {}  ", payload);

        CompletableFuture<Map<String, Object>> cf = new CompletableFuture<Map<String, Object>>();

        LoanProcessorCreate processor = new LoanProcessorCreate();
        Map<String, Object> processorContextMap = new HashMap<>();
        processorContextMap.put(IntegratorConstants.SERVICE_EXECUTOR, processor);
        processorContextMap.put(IntegratorConstants.DATA, payload);
        processorContextMap.put(IntegratorConstants.COMPLETABLE_FUTURE, cf);
        processorContextMap.put(IntegratorConstants.UNIQUE_IDENTIFICATION_ID, correlationID);
        processor.setContextMap(processorContextMap);

        // IntegratorConstants.CREATE_IOD.executeProcess(processorContextMap);
        IntegratorConstants.CREATE_IOD.execute(processor);

        log.info("Request thread Released for create loan ");
        MDC.clear();
        return cf;

    }

    /**
     * @param payload
     * @param id
     * @return
     */
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Put("/applicationupdate/{id}")
    public CompletableFuture<Map<String, Object>> updateLoan(@Body @NotNull Map<String, Object> payload, @PathVariable @NotNull String id, @Header("correlationId") String correlationID) {
        long start = System.currentTimeMillis();
      //  id = id.substring(id.lastIndexOf('-') + 1);
        MDC.put(IntegratorConstants.UNIQUE_IDENTIFICATION_ID, correlationID);
        MDC.put(IntegratorConstants.APPLICATION_ID, id);

        log.info("update loan Request thread started with given payload = {} ", payload);

        CompletableFuture<Map<String, Object>> cf = new CompletableFuture<Map<String, Object>>();

        LoanProcessorUpdate processor = new LoanProcessorUpdate();

        Map<String, Object> processorContextMap = new HashMap<>();
        processorContextMap.put(IntegratorConstants.SERVICE_EXECUTOR, processor);
        processorContextMap.put(IntegratorConstants.DATA, payload);
        processorContextMap.put(IntegratorConstants.COMPLETABLE_FUTURE, cf);
        processorContextMap.put(IntegratorConstants.APPLICATION_ID, id);
        processorContextMap.put(IntegratorConstants.UNIQUE_IDENTIFICATION_ID, correlationID);
        processor.setContextMap(processorContextMap);

        IntegratorConstants.UPDATE_IOD.execute(processor);

        log.info("Request thread Released for update loan request");
        long end = System.currentTimeMillis();
        log.info("Total time in create request controller {}", (end - start));
        MDC.clear();
        return cf;

    }

}
