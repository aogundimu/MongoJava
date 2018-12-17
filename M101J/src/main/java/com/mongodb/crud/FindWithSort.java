package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Sorts.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Projections.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Yemi on 10/25/15.
 */
public class FindWithSort {

    public  static void main(String ... args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("findWithFilterTest");

        collection.drop();

        for (int i = 0; i < 10; ++i) {
            for(int j = 0; j < 10; ++j) {
                collection.insertOne(new Document()
                        .append("i", i)
                        .append("j", j));
            }
        }

        Bson projection = fields(include("i", "j"), excludeId());

        // The following will sort by "i"
        //Bson sort = new Document("i", 1);

        // The following will sort by "i" and "j"
        // Bson sort = new Document("i", 1).append("j", 1);

        // The following will sort by "i" and "j", j in descending order
        //Bson sort = new Document("i", 1).append("j", -1);

        // Doing the same things using a builder
        /// "ascending" is a static method in Sorts
        //Bson sort = ascending("i");

        Bson sort = orderBy(ascending("i"), descending("j"));


        List< Document > all = collection.find()
                .projection(projection)
                .sort(sort)
                .skip(20) // skip the first 20
                .limit(50) // the first 50 results
                .into(new ArrayList<Document>());

        for (Document cur : all) {
            Helpers.printJson(cur);
        }
    }
}
