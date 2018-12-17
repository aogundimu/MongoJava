package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static java.util.Arrays.asList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yemi on 11/12/15.
 */
public class ZipCodeAggregationTest {

    public static void main(String ... args) {

        MongoClient client = new MongoClient();

        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("zipcode");

        List<Document> pipeline;

        // This is the same as the next statement
        /*
        pipeline = asList(new Document("$group",
                                       new Document("_id", "$state")
                                       .append("totalPop",
                                               new Document("$sum", "$pop"))),
                                        new Document("$match",
                                                     new Document("totalPop",
                                                             new Document("$gte",10000000)))); */

        // This is the same as the statement above
        pipeline = asList(Document.parse("{ $group: { _id: \"$state\", totalPop: { $sum: \"$pop\"}}}"),
                          Document.parse("{ $match: { totalPop: { $gte: 10000000 } } }"));

        List<Document> results = collection.aggregate(pipeline)
                                           .into(new ArrayList<Document>());

        for(Document cur : results) {
            System.out.println(cur.toJson());
        }
    }
}
