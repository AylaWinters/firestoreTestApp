package com.other.mcTesty.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class McTestyService {

    private final Firestore db;
	
    public McTestyService(Firestore db) { this.db = db; }

    public ResponseEntity<Object> addJsonObject(JSONObject jsonObject){
        Map<String, Object> data = new HashMap<>();
        try {
        	if(jsonObject.has("name") && jsonObject.getString("name") != null) {
        		DocumentReference docRef = db.collection("products").document(jsonObject.getString("name"));
        		for (String key : jsonObject.keySet()) {
        			Object value = jsonObject.get(key);
        			data.put(key, value);
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
}
