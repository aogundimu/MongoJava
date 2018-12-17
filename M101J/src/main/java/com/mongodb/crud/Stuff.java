package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Random;

/**
 * Created by Yemi on 12/6/15.
 */
public class Stuff {
    public  static void main(String ... args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("stuff");

        collection.drop();
        int b = 200001;
        int c = 300000;

        for(int i = 0; i < 100000; i++) {
            collection.insertOne(new Document()
                    .append("a",i)
                    .append("b", b)
                    .append("c", c));
            b++;
            c++;
        }

    }
}
