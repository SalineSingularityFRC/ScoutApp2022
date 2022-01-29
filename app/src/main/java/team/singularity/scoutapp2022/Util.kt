package team.singularity.scoutapp2022

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

const val VERSION: String = "0.0.1"

fun <T : AppCompatActivity> alert(obj: T, title: String, message: String, listener: DialogInterface.OnClickListener) {
    // make a popup message to alert the user
    val builder = AlertDialog.Builder(obj)
    builder.setMessage(message)
        .setCancelable(false)
        // set the button so we can try to recover later
        .setPositiveButton("OK", listener)

    // create the alert from the builder we made
    val alert = builder.create()
    alert.setTitle(title)
    alert.show()
}
