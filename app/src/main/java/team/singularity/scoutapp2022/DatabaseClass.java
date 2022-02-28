package team.singularity.scoutapp2022;

/**
 * Created by nhwlt on 1/10/2020.
 */

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseClass {

    public static BluetoothClass bluetooth;
    private static JSONObject tempRobotMatchData = new JSONObject();
    public static JSONArray robotMatchData = new JSONArray();
    private static JSONArray tempTeamData = new JSONArray();
    public static JSONArray teamData;
    public static String TAG = "Database Class";

    public static void setup(BluetoothClass b) {
        DatabaseClass.bluetooth = b;
    }

    public static void makeTeam(int teamNumber, String teamName) {
        //make a team and send it to the pi with manually created json (eww)
        try {
            tempTeamData.put(new JSONObject("{" +
                    "\"team\":" + teamNumber + "," +
                    "\"name\":\"" + teamName + "\"" +
                    "}"));
            teamData = tempTeamData;
            send();
            tempTeamData = new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static int getTeamNumber(int index) {
        try {
            return teamData.getJSONObject(index).getInt("team");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "failed to get team number, returning -1");
        return -1;
    }

    public static String getTeamName(int index) {
        //probably shouldn't really use this but I guess it works
        try {
            return teamData.getJSONObject(index).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public static void dataSent(String data) {
        //sends data in correct format given json in string form
        try {
            teamData = new JSONArray(data);
            FileOutputStream fos = App.getContext().openFileOutput("teamData", Context.MODE_PRIVATE);
            fos.write(teamData.toString().getBytes());
            fos.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return;
        }
        robotMatchData = new JSONArray();
        tempTeamData = new JSONArray();
    }

    public static void send() {
        //send what's stored
        send(robotMatchData.toString(), teamData.toString());
    }

    public static void send(String match, String team) {
        //format data and send it, again with manually created json (eww)
        if (bluetooth == null) {
            Log.e(TAG, "BLUETOOTH IS NULL! (seems like an issue)");
        } else {
            Log.i(TAG, "Sending data " + match);
            bluetooth.send("{\"matchData\":" + match + ",\"teamData\":" + team + "}");
            Log.i(TAG, "Sent data");
        }
    }

    public static void createRobotMatch(long time, int matchNum, int teamNum,
                                        boolean isBlue, int startingPos, int finalScore,
                                        boolean defensive, boolean defendedAgainst,
                                        boolean collision, boolean failedHangar,
                                        boolean disconnection, boolean taxi, int autoLowerHub,
                                        int autoUpperHub, int teleLowerHub, int teleUpperHub,
                                        int hangar) {
        //this will need to be changed for newer games
        //create json for a match and send it

        final double VERSION = 1.1;

        try {
            tempRobotMatchData = new JSONObject();

            tempRobotMatchData.put("version", VERSION);
            tempRobotMatchData.put("time", time);
            tempRobotMatchData.put("matchNum", matchNum);
            tempRobotMatchData.put("team", teamNum);
            tempRobotMatchData.put("isBlue", isBlue);
            tempRobotMatchData.put("startingPos", startingPos);

            tempRobotMatchData.put("defensive", defensive);
            tempRobotMatchData.put("defendedAgainst", defendedAgainst);
            tempRobotMatchData.put("collisionWithAlly", collision);
            tempRobotMatchData.put("failedHangar", failedHangar);
            tempRobotMatchData.put("disconnection", disconnection);

            tempRobotMatchData.put("taxi", taxi);
            tempRobotMatchData.put("autoLowerHub", autoLowerHub);
            tempRobotMatchData.put("autoUpperHub", autoUpperHub);
            tempRobotMatchData.put("teleLowerHub", teleLowerHub);
            tempRobotMatchData.put("teleUpperHub", teleUpperHub);
            tempRobotMatchData.put("hanger", hangar);
            tempRobotMatchData.put("finalScore", finalScore);

            //DatabaseClass.send();
        } catch (JSONException e) {
            Log.e(TAG, "Failed to create robot match");
            e.printStackTrace();
        }

        if(tempRobotMatchData==null){
            Log.e(TAG, "tempRobotMatchData is null. Weird.");
            return;
        }
        robotMatchData.put(tempRobotMatchData);
        Log.i(TAG, robotMatchData.toString());
        DatabaseClass.send();
        tempRobotMatchData=null;
    }
}
