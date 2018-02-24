package bdd;

import com.mongodb.*;

import java.net.UnknownHostException;

public class MongoConnector {
    Mongo mongo;

    {
        try {
            mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("TwistaMongoDB");
            DBCollection messages = db.getCollection("messages");
            BasicDBObject bson = new BasicDBObject();
            bson.put("db", "val");
            messages.insert(bson);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
