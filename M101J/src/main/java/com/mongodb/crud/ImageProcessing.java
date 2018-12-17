package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Yemi on 12/6/15.
 */
public class ImageProcessing {

    public  static void main(String ... args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("final7");
        MongoCollection<Document> albums = database.getCollection("albums");
        MongoCollection<Document> images = database.getCollection("images");

        List<Document> allImages = images.find().into(new ArrayList<Document>());

        int total_deleted = 0;

        for(Document document : allImages) {
            int imageId = document.getInteger("_id");
            Bson filter = new Document("images", imageId);
            List<Document> albumList = albums.find(filter).into(new ArrayList<Document>());

            if ( albumList.size() == 0 ) {
                images.deleteOne(eq("_id", imageId));
                System.out.println("Image not in an album was deleted - " + imageId);
            } else {
                System.out.println("Image in album!!");
            }
        }

        System.out.println("Total Images delete = " + total_deleted);
    }

}
