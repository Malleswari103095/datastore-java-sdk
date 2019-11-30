# datastore-java-sdk
A java client library for storing key-value based data in file

#### Technical Stack
- Java
- Gradle
- IntelliJ IDE

#### Dependencies
- Jackson Databind [Download](http://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)
- org.json [Download](https://mvnrepository.com/artifact/org.json/json)

#### Download 
The latest release 1.0 is available [here](https://github.com/Malleswari103095/datastore-java-sdk/releases/download/v1.0/datastore-java-sdk-1.0.jar)

#### Available services
##### Initialization
```
Storage service = DatastoreService.init();

(or)

Storage service = DatastoreService.init("/Users/Alex/Downloads");  // Directory path
```
##### Put an item 
```
service.put("key", {json obj}); 

(or)

service.put("key", {json obj}, 500); // expiry in seconds
```

##### Get an item 
```
service.get("key"); 
```

##### Remove an item 
```
service.remove("key");
```

#### Usage
```
Storage service = DatastoreService.init(); // optional directory path

JSONObject obj = new JSONObject();
obj.put("name", "Alex");
service.put("12345", obj); // optional expiry in seconds

System.out.println(service.get("12345"));
```
