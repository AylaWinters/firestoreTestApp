package com.other.mcTesty.controllers;

import com.other.mcTesty.entities.Product;
import com.other.mcTesty.services.McTestyService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/")
public class McTestyController {

    private McTestyService service;

    @Autowired
    public McTestyController(McTestyService service){ this.service = service; }

    @PutMapping(path = "/addProduct")
    public ResponseEntity<Object> addProduct(@RequestBody Product product){
        ResponseEntity<Object> response;

        if (product != null){
            response = service.addDocument(product);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @PutMapping(path = "/addJson")
    public ResponseEntity<Object> addJsonObject(@RequestBody String json){
        ResponseEntity<Object> response;
        JSONObject jsonObject = new JSONObject(json);
        if (json != null){
            response = service.addJsonObject(jsonObject);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @PutMapping(path = "/addCollection")
    public ResponseEntity<Object> addCollection(@RequestParam String collectionName){
        ResponseEntity<Object> response;
        if (collectionName != null){
            response = service.addCollection(collectionName);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

}
