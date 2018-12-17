package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Yemi on 10/25/15.
 */
public class FindWithFilterTest {
    public  static void main(String ... args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("findWithFilterTest");

        collection.drop();

        for(int i = 0; i < 10; i++) {
            collection.insertOne(new Document()
                            .append("x", new Random().nextInt(2))
                            .append("y", new Random().nextInt(100)));
        }

        // This creates a filter - all documents with x == 0
        // Bson filter = new Document("x", 0);

        // This creates a filter - x == 0 and y > 10
        //Bson filter = new Document("x", 0)
        //      .append("y", new Document("$gt",10));

        // This creates filter "x == 0", "y > 10" and "y < 90"
        //Bson filter = new Document("x", 0)
          //      .append("y", new Document("$gt",10)
            //    .append("$lt", 90));

        // Builder for query filters
        //Bson filter = Filters.eq("x", 0);

        // This is after static import of Filters
        Bson filter = and(eq("x",0), gt("y", 10), lt("y", 90));

        // Filter is passed as argument to the find() call
        List<Document> all = collection.find(filter).into(new ArrayList<Document>());
        for (Document cur : all) {
            Helpers.printJson(cur);
        }

        // Filter is passed as argument to the count() method.
        long count = collection.count(filter);
        System.out.println("Count: ");
        System.out.println(count);
    }
}
