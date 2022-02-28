package team.singularity.scoutapp2022;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    //a hack to get the app context from anywhere, not just an activity
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
