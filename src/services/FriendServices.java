package services;

import bdd.*;
import com.mongodb.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.GregorianCalendar;

public class FriendServices {

    public static JSONObject addComment(String key, String str_post_id, String text) {
        try {
            boolean connected = ConnexionDB.isConnexion(key);
            if (!connected)
                return MsgTools.serviceRefused("Bad connexion key", -1);
            int user_id = UserTools.getID(key);
            String login = UserTools.getLogin(user_id);
            int post_id = Integer.parseInt(str_post_id);

            //get the collection
            Mongo m = new Mongo("localhost", 27017);
            DB db = m.getDB("TwistaMongoDB");
            DBCollection messages = db.getCollection("Messages");

            //get the query to select
            BasicDBObject post = new BasicDBObject();
            post.put("post_id", post_id);

            //build the author object
            BasicDBObject author = new BasicDBObject();
            author.put("user_id", user_id);
            author.put("login", login);

            //build a date
            GregorianCalendar calendar = new java.util.GregorianCalendar();
            Date date = calendar.getTime();

            //build the comment to be added
            BasicDBObject comment = new BasicDBObject();
            comment.put("author", author);
            comment.put("date", date);
            comment.put("text", text);

            //build the add object
            BasicDBObject comments = new BasicDBObject();
            comments.put("comments", comment);
            BasicDBObject add = new BasicDBObject();
            add.put("$addToSet", comments);
            WriteResult res = messages.update(post, add);
            if(res.getN()>0)
                return MsgTools.serviceAccepted();
            else
                return MsgTools.serviceRefused("Couldn't add comment..", -1);
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
