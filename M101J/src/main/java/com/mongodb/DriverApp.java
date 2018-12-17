package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import static java.util.Arrays.asList;

/**
 * Created by Yemi on 10/25/15.
 */
public class DriverApp {
    public static void main( String ... args) {
        //MongoClient client = new MongoClient(asList(new ServerAddress("localHost", 27017)));
        // MongoClient client = new MongoClient("locahost", 27017);
        //MongoClient client2 = new MongoClient(asList(new ServerAddress("localhost", 27017)));

        MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(500).build();
        //MongoDatabase db = client.getDatabase("test");
        MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());

        // MongoCollection is the gateway to all CRUD operations
        // MongoCollection<BsonDocument> coll = db.getCollection("test",BsonDocument.class);
        MongoCollection<Document> coll = db.getCollection("test", Document.class);
    }
}
