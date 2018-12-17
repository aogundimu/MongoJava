package course;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Sorts.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class BlogPostDAO {
    MongoCollection<Document> postsCollection;

    public BlogPostDAO(final MongoDatabase blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public Document findByPermalink(String permalink) {

        // todo  XXX
        Document post = null;
        Bson filter = new Document("permalink", permalink);

        post = postsCollection.find(filter).first();

        return post;
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<Document> findByDateDescending(int limit) {

        // todo,  XXX
        // Return a list of Documents, each one a post from the posts collection
        Bson sort = descending("date");
        List<Document> posts = postsCollection.find()
                                              .sort(sort)
                                              .limit(limit)
                                              .into(new ArrayList<Document>());

        return posts;
    }


    public String addPost(String title, String body, List tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();
        permalink = permalink+ (new Date()).getTime();


        // todo XXX
        // Remember that a valid post has the following keys:
        // author, body, permalink, tags, comments, date
        //
        // A few hints:
        // - Don't forget to create an empty list of comments
        // - for the value of the date key, today's datetime is fine.
        // - tags are already in list form that implements suitable interface.
        // - we created the permalink for you above.

        // Needed for the document - "title", "author", "body", "permalink", "tags" "comments" "date"
        // Both "comments" and "tags" are arrays

        // Build the post object and insert it
        Date now = new Date();
        Document post = new Document("title", title)
                                    .append("author", username)
                                    .append("body", body)
                                    .append("permalink", permalink)
                                    .append("tags", tags)
                                    .append("comments", new ArrayList<Document>())
                .append("date", now);

        postsCollection.insertOne(post);

        return permalink;
    }


    // White space to protect the innocent

    // Append a comment to a blog post
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {

        // todo  XXX
        // Hints:
        // - email is optional and may come in NULL. Check for that.
        // - best solution uses an update command to the database and a suitable
        //   operator to append the comment on to any existing list of comments
        // postsCollection.updateOne( eq("permalink", permalink),
        BasicDBObject query = new BasicDBObject("permalink", permalink);

        BasicDBObject values;

        if ( email == null ) {
            values = new BasicDBObject("author", name).append("body", body);
        } else {
            values = new BasicDBObject("author", name).append("body", body).append("email",email);
        }

        BasicDBObject fields = new BasicDBObject("comments", values);
        BasicDBObject update = new BasicDBObject("$push", fields);

        postsCollection.updateOne(query, update);
    }
}
