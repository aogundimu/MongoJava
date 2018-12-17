package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;

/**
 * Created by Yemi on 10/26/15.
 */
public class HelloWorldMongoDBSparkFreemarkerStyle {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(
                HelloWorldMongoDBSparkFreemarkerStyle.class, "/freemarker");

        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("hello");

        collection.drop();

        collection.insertOne(new Document("name", "MongoDB"));

        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request,
                                 final Response response) {

                StringWriter writer = new StringWriter();

                try {
                    Template helloTemplate = configuration.getTemplate("hello.ftl");
                    Document document = collection.find().first();
                    helloTemplate.process(document, writer);
                } catch (Exception e) {
                    e.printStackTrace();
                    halt(500);
                }

                return writer;
            }
        });

    }
}
