package com.other.mcTesty.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FireStoreConfig {

    @Bean
    public Firestore initializeFirestore() throws IOException {
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId(System.getenv("PROJECT_ID"))
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build();
        Firestore db = firestoreOptions.getService();
        return db;
    }


}
