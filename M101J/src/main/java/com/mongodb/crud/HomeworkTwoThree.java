package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;


/**
 * Created by Yemi on 10/26/15.
 */
public class HomeworkTwoThree {
    public static void main(String ... args ) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("students");
        MongoCollection<Document> collection = database.getCollection("grades");

        Bson filter = new Document("type", "homework");
        Bson sort = new Document("student_id", 1).append("score", -1);

        List<Document> all = collection.find(filter).sort(sort).into(new ArrayList<Document>());

        int prev_student_id = -1;
        double prev_score = 0;
        int total_read = 0;
        int total_deleted = 0;

        /*
        for (Document cur : all) {
            int curr_student_id = cur.getInteger("student_id");
            double curr_score = cur.getDouble("score");
            if (curr_student_id == prev_student_id) {
                // We do the delete here
                System.out.println("Student id = " + curr_student_id + ", Score = " + curr_score);
                // we are going to delete here.
                total_deleted++;
                total_read = 0;
                prev_student_id = curr_student_id;
            }
         */

        for(int i = 0; i < all.size(); ++i) {
            Document cur = all.get(i);
            int curr_student_id = cur.getInteger("student_id");
            double curr_score = cur.getDouble("score");
            System.out.println("Student id = " + curr_student_id + ", Score = " + curr_score);

            if ( curr_student_id == prev_student_id) {
                System.out.println( "we do a delete");
                collection.deleteOne(and(eq("student_id", curr_student_id),eq("score", curr_score)));
                ++total_deleted;
            } else {
                prev_student_id = curr_student_id;
            }
        }
            //System.out.println("Student_ID = " + student_id + ", Score = " + score);
            //Helpers.printJson(cur)

        System.out.println("Total documents = " + all.size());
        System.out.println("Total deleted documents = " + total_deleted);
    }
}
