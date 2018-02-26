package services;

import bdd.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.GregorianCalendar;

public class FriendServices {

    public static JSONObject addComment(String key, String str_post_id, String text) {
        //TODO addComment
        try {
            boolean connected = ConnexionDB.isConnexion(key);
            if (!connected)
                return MsgTools.serviceRefused("Bad connexion key", -1);
            int user_id = UserTools.getID(key);
            int post_id = Integer.parseInt(str_post_id);

            //mongo call
            Mongo m = new Mongo("localhost", 27017);
            DB db = m.getDB("TwistaMongoDB");
            DBCollection messages = db.getCollection("messages");

            BasicDBObject post = new BasicDBObject();
            post.put("post_id", post_id);

            BasicDBObject comment = new BasicDBObject();

            comment.put("user_id", user_id);
            GregorianCalendar calendar = new java.util.GregorianCalendar();
            Date date = calendar.getTime();
            comment.put("date", date);
            comment.put("comment", text);

            BasicDBObject add = new BasicDBObject();
            add.put("$push",new BasicDBObject().append("comments", comment));
            messages.update(post, add);
            return MsgTools.serviceAccepted();
        } catch (DBException e) {
            return MsgTools.serviceRefused(e.toString(), 1000);
        } catch (Exception e) {
            return MsgTools.serviceRefused(e.toString(), 10000);
        }
    }

    public static JSONObject addFriend(String key, String valueFriendID) {
        try {
            boolean connected = ConnexionDB.isConnexion(key);
            if (!connected)
                return MsgTools.serviceRefused("Bad connexion key", -1);
            if (UserTools.userExists(valueFriendID))
                return MsgTools.serviceRefused("Invalid friend ID", -1);
            int from_id = UserTools.getID(key);
            int to_id = Integer.parseInt(valueFriendID);
            if (from_id == to_id)
                return MsgTools.serviceRefused("Can't friend yorself..", -1);

            //DB call
            FriendTools.follow(from_id, to_id);
            return MsgTools.serviceAccepted();
        } catch (DBException e) {
            return MsgTools.serviceRefused(e.toString(), 1000);
        } catch (Exception e) {
            return MsgTools.serviceRefused(e.toString(), 10000);
        }
    }

    public static JSONObject removeFriend(String key, String valueFriendID) {
        try {
            boolean connected = ConnexionDB.isConnexion(key);
            if (!connected)
                return MsgTools.serviceRefused("Bad connexion key", -1);
            if (UserTools.userExists(valueFriendID))
                return MsgTools.serviceRefused("Invalid friend ID", -1);
            int from_id = UserTools.getID(key);
            int to_id = Integer.parseInt(valueFriendID);
            if (from_id == to_id)
                return MsgTools.serviceRefused("Can't unfriend yorself..", -1);
            FriendTools.unfollow(from_id, to_id);
            return MsgTools.serviceAccepted();
        } catch (DBException e) {
            return MsgTools.serviceRefused(e.toString(), 1000);
        } catch (Exception e) {
            return MsgTools.serviceRefused(e.toString(), 10000);
        }
    }

    public static JSONObject search(String valueKey, String valueQuery, String valueFriends) {
        //TODO finir la partie Search
        return new JSONObject();
    }
}
