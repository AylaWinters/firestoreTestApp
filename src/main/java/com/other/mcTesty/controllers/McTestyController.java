package com.other.mcTesty.controllers;

import com.dep.layer.services.LoggingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.dep.layer.entites.APILogEntity;
import com.other.mcTesty.services.McTestyService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.RandomStringUtils;


@RestController
@RequestMapping(path = "/")
public class McTestyController {

    private McTestyService service;
    private LoggingService logger;

    @Autowired
    public McTestyController(McTestyService service){ 
    	this.service = service;
    	this.logger = new LoggingService();
    }

    @PostMapping(path = "/addJson")
    public ResponseEntity<Object> addJsonObject(@RequestBody String json) 
    		throws JsonProcessingException{
        ResponseEntity<Object> response;
        JSONObject jsonObject = new JSONObject(json);
        APILogEntity logEntity = new APILogEntity();
        long transStartTime = System.currentTimeMillis();
        logEntity.setTransactionId(RandomStringUtils.randomAlphanumeric(7));
        logEntity.setEnv("DEV");
        try {
	        if (json != null){
	            response = service.addJsonObject(jsonObject);
	        } else {
	            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
        
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
        logger.pushLogToBucket(logEntity, response, transStartTime);
        return response;
    }

    @PostMapping(path = "/addCollection")
    public ResponseEntity<Object> addCollection(@RequestParam String collectionName) 
    		throws JsonProcessingException{
        ResponseEntity<Object> response;
        APILogEntity logEntity = new APILogEntity();
        long transStartTime = System.currentTimeMillis();
        logEntity.setTransactionId(RandomStringUtils.randomAlphanumeric(7));
        logEntity.setEnv("DEV");
        try {
        if (collectionName != null){
            response = service.addCollection(collectionName);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
        logger.pushLogToBucket(logEntity, response, transStartTime);
        return response;
    }

    @GetMapping(path = "/getDocument")
    public ResponseEntity<Object> getDocument(@RequestParam String docName){
        ResponseEntity<Object> response;
        APILogEntity logEntity = new APILogEntity();
        long transStartTime = System.currentTimeMillis();
        logEntity.setTransactionId(RandomStringUtils.randomAlphanumeric(7));
        logEntity.setEnv("DEV");
        System.out.println(docName);
        try {
            response = service.getDocument(docName);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

}
