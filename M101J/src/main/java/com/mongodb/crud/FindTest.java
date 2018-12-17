package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yemi on 10/25/15.
 */
public class FindTest {

    public static void main(String ... args ) {

        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("findTest");

        collection.drop();

        for( int i = 0; i < 10; ++i) {
            collection.insertOne(new Document("x", i));
        }

        System.out.println("Find one: " );
        Document first = collection.find().first();
        Helpers.printJson(first);

        System.out.println("Find all with into: ");
        List<Document> all = collection.find().into(new ArrayList<Document>());
        for(Document cur : all) {
            Helpers.printJson(cur);
        }

        System.out.println("Find all with iteration: ");
        MongoCursor<Document> cursor = collection.find().iterator();

        try {
            while( cursor.hasNext() ) {
                Document cur = cursor.next();
                Helpers.printJson(cur);
            }
        } finally {
            cursor.close();
        }

        System.out.println("Count: ");
        long count = collection.count();
        System.out.println(count);
    }
}
