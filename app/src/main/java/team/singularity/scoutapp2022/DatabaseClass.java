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

        ArrayList jsonObjectArray = new ArrayList();
        String currentJSONString = "";
/*///
        try {
            FileInputStream fis = new FileInputStream("teamData.json");
            //FileInputStream fis = bluetooth.activity.openFileInput("teamData");
            int dat = fis.read();
            Log.i(TAG, String.format("Dat: %d :: Str: %s", dat, fis.toString()));
            teamData = new JSONArray(dat);
            /*while( (currentJSONString = fis.read()) != null) {
                JSONObject currentObject = new JSONObject(currentJSONString);

                jsonObjectArray.add(currentObject);
            }*//*///
            Log.i(TAG, "finished the fis.read()");
            fis.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Got exception!");
            try {
                FileOutputStream fos = bluetooth.activity.openFileOutput("teamData.json", Context.MODE_PRIVATE);
                //fos.write("".getBytes());
                fos.close();
                FileInputStream fis = bluetooth.activity.openFileInput("teamData.json");
                int dat = fis.read();
                Log.i(TAG, String.format("Data: %d :: String: %s", dat, fis.toString()));
                teamData = new JSONArray(fis.read());
                fis.close();
            } catch (IOException | JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            Log.i(TAG, "caught JSONException");
        }///*/
    }

    public static void makeTeam(int teamNumber, String teamName) {
        try {
            tempTeamData.put(new JSONObject("{" +
                    "\"team\":" + teamNumber + "," +
                    "\"name\":\"" + teamName + "\"" +
                    "}"));
            send();
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
        return -1;
    }

    public static String getTeamName(int index) {
        try {
            return teamData.getJSONObject(index).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public static void dataSent(String data) {
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

    private static void send() {
        if (bluetooth == null) {
            Log.e(TAG, "BLUETOOTH IS NULL! (seems like an issue)");
        } else {
            Log.i(TAG, "Sending data " + robotMatchData.toString());
            bluetooth.send("{\"matchData\":" + robotMatchData.toString() + ",\"teamData\":[]}");
            Log.i(TAG, "Sent data");
        }

        // For the newest and greatest SSSS.py (newest, don't know about greatest)
        //bluetooth.send_byte(new byte[] {'\0'});
    }

    public static void createRobotMatch(double version, long time, int matchNum, int teamNum,
                                        boolean isBlue, int startingPos, boolean taxi, 
                                        int autoLowerHub, int autoUpperHub, int teleLowerHub, 
                                        int teleUpperHub, int hangar) {
        try {
            tempRobotMatchData = new JSONObject();

            tempRobotMatchData.put("version", version);
            tempRobotMatchData.put("time", time);
            tempRobotMatchData.put("matchNum", matchNum);
            tempRobotMatchData.put("teamNum", teamNum);
            tempRobotMatchData.put("isBlue", isBlue);
            tempRobotMatchData.put("startingPos", startingPos);
            tempRobotMatchData.put("taxi", taxi);
            tempRobotMatchData.put("autoLowerHub", autoLowerHub);
            tempRobotMatchData.put("autoUpperHub", autoUpperHub);
            tempRobotMatchData.put("teleLowerHub", teleLowerHub);
            tempRobotMatchData.put("teleUpperHub", teleUpperHub);
            tempRobotMatchData.put("hanger", hangar);

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
