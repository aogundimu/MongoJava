package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static  java.util.Arrays.asList;

/**
 * Created by Yemi on 10/25/15.
 */
public class InsertTest {
    public static void main(String ... args) {

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("mycollection");

        //coll.drop();

        Document smith = new Document("name", "smith")
                .append("age", "30")
                .append("profession", "programmer");

        //Helpers.printJson(smith);

        // Insert it into the database
        //coll.insertOne(smith);

        //Helpers.printJson(smith);

        Document jones = new Document("name", "jones")
                .append("age", "25")
                .append("profession", "hacker");

        Document james = new Document("name", "james")
                .append("age", "29")
                .append("profession", "jive turkey");

        coll.insertMany(asList(smith, jones, james));

        Helpers.printJson(smith);
        Helpers.printJson(jones);
        Helpers.printJson(james);

        // coll.insertOne( smith );
    }
}
