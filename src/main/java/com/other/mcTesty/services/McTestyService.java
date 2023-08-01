package com.other.mcTesty.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class McTestyService {

    private final Firestore db;

    @Autowired
    public McTestyService(Firestore db) { this.db = db; }

    public ResponseEntity<Object> addDocument(Map<String, Object> reqMap){
        Map<String, Object> data = new HashMap<>();
        try {
        	if(reqMap.containsKey("Table_Name") && reqMap.get("Table_Name") != null) {
        		DocumentReference docRef = db.collection("Collection_Name").document(reqMap.get("Table_Name").toString());
        		for (String key : reqMap.keySet()) {
        			Object value = reqMap.get(key);
        			data.put(key, value);
        		}
        		ApiFuture<WriteResult> result = docRef.set(data);
        		result.get();
           	} else {
           		return new ResponseEntity<>("Key: 'Table_Name' was not provided",
           				HttpStatus.BAD_REQUEST);
           	}
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success! " + reqMap.get("Table_Name")
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
	public ResponseEntity<Object> getDocument(String documentName){

        ResponseEntity<Object> response;
        try {
            DocumentSnapshot document = db.collection("Collection_Name").document(documentName).get().get();
            if (document.exists()){
                System.out.println(document.getData());
                response = new ResponseEntity<>(document.getData(), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>("Document " + documentName + " does not exist!", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
