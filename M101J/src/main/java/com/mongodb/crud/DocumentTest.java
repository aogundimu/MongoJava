package com.mongodb.crud;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Date;

//import static com.mongodb.crud.Helpers.printJson;

/**
 * Created by Yemi on 10/25/15.
 */
public class DocumentTest {

    public static void main(String ... args) {


        Document document = new Document().append("str", "MongoDB, Hello");
        // This gets an attribute of the document
        String str = (String) document.get("str");

        Document document1 = new Document()
                .append("str", "MongoDB, Hello")
                .append("int", 42)
                .append("double", 1.1)
                .append("b", false)
                .append("date", new Date())
                .append("objectId", new ObjectId())
                .append("null", null)
                .append("embeddedDoc", new Document("x",0))
                .append("list", Arrays.asList(1, 2, 3));

        Helpers.printJson(document1);
    }
}
