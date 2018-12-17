package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import static com.mongodb.client.model.Projections.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Yemi on 10/25/15.
 */
public class FindWithProjectionTest {
    public  static void main(String ... args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("findWithFilterTest");

        collection.drop();

        for(int i = 0; i < 10; ++i) {
            collection.insertOne(new Document()
                    .append("x", new Random().nextInt(2) )
                    .append("y", new Random().nextInt(100))
                    .append("i", i));
        }

        Bson filter = and(eq("x",0), gt("y",10), lt("y", 90));

        // We already know what the value of x is we can exclude the "x" field from
        // what we are printin. this is done with a projection.
        // This projection will exclude "x" and "_id" from what we print
       // Bson projection = new Document("x", 0).append("_id", 0);

        // If we want to include we pass the second argument as 1, the example below will
        // include both x and y in the documents printed.
        //Bson projection = new Document("x", 1).append("y", 1);

        // MongoDB will by default always print the "_id", exclusion of the id has to be
        // done explicitly as shown below.
        //Bson projection = new Document("x", 1).append("y", 1).append("_id", 0);

        // The next example uses the Projection class to accomplish the same things.
        // This will exclude "x" and "_id" from the output
        //Bson projection = Projections.exclude("x", "_id");

        // The following is for inclusions.
        //Bson projection = Projections.include("y","i");

        // This will include both y and i but exclude _id
        //Bson projection = Projections.fields( Projections.include("y", "i"),
          //                                    Projections.exclude("_id") );

        // the following is the same as the above, but we have used static import
        Bson projection = fields(include("y", "i"), exclude("_id") );



        List < Document > all = collection.find(filter)
                        .projection(projection)
                        .into(new ArrayList<Document>());
        for (Document cur : all) {
            Helpers.printJson(cur);
        }

        // Filter is passed as argument to the count() method.
        long count = collection.count(filter);
        System.out.println("Count: ");
        System.out.println(count);
    }
}
