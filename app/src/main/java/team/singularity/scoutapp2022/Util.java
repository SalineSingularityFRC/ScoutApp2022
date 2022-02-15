package team.singularity.scoutapp2022;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog.Builder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class Util {

    public static final String VERSION = "0.0.3";
    public static final String PI_MAC_ADDRESS = "B8:27:EB:E8:64:53";

    public static final void alert(AppCompatActivity obj, String title, String message, OnClickListener listener) {
        // make a popup message to alert the user
        Builder builder = new Builder(obj);
        builder.setMessage((CharSequence)message)
                .setCancelable(false)
                // set the button so we can try to recover later
                .setPositiveButton("OK", listener);

        // create the alert from the builder we made
        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }
}

