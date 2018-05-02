package com.kotlin.siddiqz.kotlin_android_helloworld

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Message
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import android.support.v4.app.NotificationCompat.getExtras
import java.lang.reflect.Array.getDouble
import android.content.Intent
import android.content.BroadcastReceiver
import android.support.v4.content.LocalBroadcastManager
import android.content.IntentFilter
import android.net.Uri
import com.google.firebase.messaging.FirebaseMessaging
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private var mTextMessage: TextView? = null
    private var mOutput: TextView? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mTextMessage!!.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mTextMessage!!.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                mTextMessage!!.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,  IntentFilter("MainActivity") )
    }

    override fun onStop() {
        super.onStop()
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val data: HashMap<*, *>? = intent.getSerializableExtra("data") as HashMap<*, *>?
            if(data!!.containsKey("command") && data.containsKey("server_ip")) {

                //call-for-tokens a request made by the server to tell all devices subscribed to /topics/server to send their tokens
                if(data["command"]!!.equals("call-for-tokens")) {
                    sendToServer(data["server_ip"].toString())
                }
            }

            printOutput("in main activity")
            Toast.makeText(applicationContext, "test ${data!!["command"]}", Toast.LENGTH_SHORT).show()
        }
    }



    private fun printOutput(message: String, heading: String = "") {
        //heading = Html.fromHtml("<strong>")
        mOutput!!.text = ""+ mOutput!!.text + "\n" + message
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_SMS, Manifest.permission.INTERNET)
                .withListener( object: MultiplePermissionsListener{
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {

                    }
                }).check()

        val cursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)

        if (cursor!!.moveToFirst()) { // must check the result to prevent exception
            do {
                var msgData = ""
                for (idx in 0 until cursor.columnCount) {
                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx)
                }
                // use msgData
                println(msgData)
            } while (cursor.moveToNext())
        } else {
            // empty box, no SMS
        }

        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
        mTextMessage = findViewById(R.id.textMessage) as TextView
        mOutput = findViewById(R.id.output) as TextView
        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        Log.d("******","MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("server")
    }

    fun sendToServer(ip: String) {
        val utils = HttpUtils()
        utils.post("http://$ip:8080/api/device/add",
                "{\"name\":\"Android Device\", \"token\": \""+FirebaseInstanceId.getInstance().getToken().toString()+"\"}")
    }

}
