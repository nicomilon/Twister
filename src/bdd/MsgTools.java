package bdd;

import org.json.JSONException;
import org.json.JSONObject;

public class MsgTools {

    public static JSONObject serviceRefused(String msg, int i) {
        // version argument manqg
        String error_type = "";
        switch (i) {
            case -1:
                error_type = "Client error";
                break;
            case 100:
                error_type = "JSON error";
                break;
            case 1000:
                error_type = "SQL error";
                break;
            case 10000:
                error_type = "Java error";
                break;
            default:
                error_type = "Unknown error code : " + i;
                break;
        }
        JSONObject Jerror = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json.put("error_type", error_type);
            json.put("error_message", msg);
            Jerror.append("error", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Jerror;
    }

    public static JSONObject serviceAccepted() {
        JSONObject json = new JSONObject();
        try {
            json.put("output", "OK");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

}
