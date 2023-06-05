package com.other.mcTesty.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.other.mcTesty.entities.Product;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class McTestyService {

    private Firestore db;

    @Autowired
    public McTestyService(Firestore db) { this.db = db; }

    public ResponseEntity<Object> addDocument(Product product){
        try {
            DocumentReference docRef = db.collection("products").document(product.getName());
            ApiFuture<WriteResult> result = docRef.set(product);
            result.get();
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success! " + product.getName() + " was added!", HttpStatus.OK);
    }
    public ResponseEntity<Object> addJsonObject(JSONObject jsonObject){
        Map<String, Object> data = new HashMap<>();
        try {
            DocumentReference docRef = db.collection("products").document(jsonObject.getString("name"));
            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);
                data.put(key, value);
            }
            ApiFuture<WriteResult> result = docRef.set(data);
            result.get();
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success! " + jsonObject.getString("name")
                + " was added!", HttpStatus.OK);
    }
}
