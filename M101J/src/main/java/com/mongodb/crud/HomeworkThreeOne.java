package com.mongodb.crud;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Filters.lt;

/**
 * Created by Yemi on 11/1/15.
 */
public class HomeworkThreeOne {

    public static void main(String ... args ) {

        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("school");
        MongoCollection<Document> collection = database.getCollection("students");

       // Bson filter = new Document("type", "homework");
       // Bson sort = new Document("student_id", 1).append("score", -1);


        List<Document> all = collection.find().into(new ArrayList<Document>());

        for ( Document cur : all ) {
            //Helpers.printJson(cur);
            Object grades = cur.get("scores");
            int studentId = cur.getInteger("_id");
            //System.out.println();
            System.out.println("Student ID = " + studentId);
            ArrayList<Document> arrayList = (ArrayList<Document>)grades;

            ArrayList<Document> newGrades = new ArrayList<>();

            double lowest_homework_grade = 100;

            int total_scores = 0;

            //System.out.println("newGrades.size() == " + newGrades.size());

            for(Document doc : arrayList ) {
                System.out.println("Total scores == " + ++total_scores);
                double curr_score = doc.getDouble("score");
                String type = doc.getString("type");
                if ( type.equals("homework") ) {
                   System.out.println("Type == " + type);

                    if ( curr_score < lowest_homework_grade)
                        lowest_homework_grade = curr_score;

                } else {
                    //System.out.println("Type == " + type);
                    //System.out.println("BEFORE newGrades.size() == " + newGrades.size());
                    newGrades.add( new Document().append("type", type)
                                                 .append("score", curr_score)
                            );

                }
            }


            System.out.println(lowest_homework_grade);
            BasicDBObject query = new BasicDBObject("_id", studentId);
            BasicDBObject fields = new BasicDBObject("scores",
                    new BasicDBObject("type","homework").append("score", lowest_homework_grade) );
            BasicDBObject update = new BasicDBObject("$pull", fields);

            collection.updateOne( query, update );
        }
    }
}
