package com.other.mcTesty.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dep.layer.gcp.FireStoreConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class McTestyService {

    private final Firestore db;

    public McTestyService() { this.db = FireStoreConfig.getInstance(); }
    
   

    public ResponseEntity<Object> addJsonObject(JSONObject jsonObject){
        Map<String, Object> data = new HashMap<>();
        try {
        	if(jsonObject.has("name") && jsonObject.getString("name") != null) {
        		DocumentReference docRef = db.collection("products").document(jsonObject.getString("name"));
        		for (String key : jsonObject.keySet()) {
                    if(jsonObject.get(key).getClass() == JSONObject.class){
                        System.out.println("is JSONObject");
                        System.out.println(jsonObject.get(key).getClass());
                        List list = new ArrayList<>();
                        list.
                    } else {
                        Object value = jsonObject.get(key);
        			    data.put(key, value);
                    }
        		}
        		ApiFuture<WriteResult> result = docRef.set(data);
        		result.get();
           	} else {
           		return new ResponseEntity<>("Key: 'name' was not provided",
           				HttpStatus.BAD_REQUEST);
           	}
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success! " + jsonObject.getString("name")
                + " was added!", HttpStatus.OK);
    }

	public ResponseEntity<Object> addCollection(String collectionName){
        try {
            db.collection(collectionName).add(new HashMap<>());
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success! " + collectionName + " was added!", HttpStatus.OK);
    }

    public ResponseEntity<Object> getDocument(String docName){
        ResponseEntity response;
        try {

            DocumentReference docRef = db.collection("products").document(docName);
            DocumentSnapshot docObj = docRef.get().get();
            if(docObj != null){
                System.out.println(docObj.getData());
            }
            JSONObject json = new JSONObject(docObj.getData());
            System.out.println("json = " + json);
            response = new ResponseEntity(json, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
