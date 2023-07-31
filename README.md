# Firestore POC

## Dependencies:
```xml
    <dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>com.google.cloud</groupId>
				<artifactId>libraries-bom</artifactId>
				<version>26.15.0</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<dependencies>

		<dependency>
			<groupId>com.google.cloud</groupId>
			<artifactId>google-cloud-firestore</artifactId>
		</dependency>
		
	</dependencies>
```

## Config
```java
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
```

## ENV VARS

1. PROJECT_ID = *name of the GCP project*

## Service

### Autowire the Firestore:
```java
    private final Firestore db;
	
    @Autowired
    public McTestyService(Firestore db) { this.db = db; }
```

### Map the incoming object to Firestore
Firestore cannot accept raw JSON so we need to convert it to a DocumentReference.
[Google Docs]("https://firebase.google.com/docs/firestore/query-data/get-data#java_1")

```java
    public ResponseEntity<Object> addJsonObject(JSONObject jsonObject){
        Map<String, Object> data = new HashMap<>();
        try {
            
                // Null check
        	if(jsonObject.has("Table_Name") && jsonObject.getString("Table_Name") != null) {
            
                        #Call the Collection and create a new table named: "Table_Name" or replace "Table_Name" if exists
        		DocumentReference docRef = db.collection("Collection_Name").document(jsonObject.getString("Table_Name"));
                
                        // Loop through incoming jsonObject and map the key value pairs to a Map<String, Object>
        		for (String key : jsonObject.keySet()) {
        			Object value = jsonObject.get(key);
        			data.put(key, value);
        		}
                
                        // Write the result to Firestore
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
        return new ResponseEntity<>("Success! " + jsonObject.getString("Table_Name")
                + " was added!", HttpStatus.OK);
    }
```

### Above code works with a POST request that has the following Request Body:
```json
{
  "Table_name": "Name of my Table",
  "Field1": "This is my first field",
  "IsBoolean": true,
  "RandomNumber": 84,
  "canMakeAsManyFieldsAsWeWant": true
}

```

## Next Steps
1. Create a reusable toJson and fromJson converter that allows for nested JSONObjects and Lists of JSONObjects
2. Learn more about GCP Credentials that don't require a local JSON file
3. Test with Terraform
4. Make similar changes in a new branch for Admin API