package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Yemi on 10/25/15.
 */
public class UpdateTest {
    public  static void main(String ... args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("findWithFilterTest");

        collection.drop();

        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document()
                    .append("_id", i)
                    .append("x", i));
        }

        // Update the document with x = 5; set x to 20 and add a new
        // attribute "update" with value = true
        // this replaces the whole document
        collection.replaceOne( eq("x",5),
                               new Document("_id",5)
                                       .append("x",20)
                                       .append("update", true) );

        // The following just changes the attribute(s) in the document
        collection.updateOne( eq("x",7),
                              new Document("$set", new Document("x",20)),
                               new UpdateOptions().upsert(true));

        collection.updateOne( eq("x",9), new Document("$set", new Document("x",20) ) );

        // this updates a bunch of arguments
        collection.updateMany( gte("_id",5),
                               new Document("$inc", new Document("x", 1))
                            );

        for (Document cur : collection.find().into(new ArrayList<Document>())) {
            Helpers.printJson(cur);
        }
    }
}
