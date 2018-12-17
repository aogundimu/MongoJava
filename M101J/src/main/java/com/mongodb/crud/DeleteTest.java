package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;

/**
 * Created by Yemi on 10/25/15.
 */
public class DeleteTest {
    public  static void main(String ... args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("findWithFilterTest");

        collection.drop();

        for (int i = 0; i < 8; i++) {
            collection.insertOne(new Document()
                    .append("_id", i));
        }

        // Delete all entries with "_id" > 4
        collection.deleteMany(gt("_id", 4));

        // Delete one entry with "_id" = 1
        collection.deleteOne(eq("_id", 1));

        for (Document cur : collection.find().into(new ArrayList<Document>())) {
            Helpers.printJson(cur);
        }
    }
}
