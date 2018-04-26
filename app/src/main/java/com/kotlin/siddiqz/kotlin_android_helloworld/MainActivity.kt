package com.kotlin.siddiqz.kotlin_android_helloworld

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
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private var mTextMessage: TextView? = null
    private var mOutput: TextView? = null
    val client = OkHttpClient()


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
            intent.extras.getString("command")
            printOutput("in main activity")
            Toast.makeText(applicationContext, "test", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Dexter.withActivity(this)
                .withPermission("android.permission.INTERNET")
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {/* ... */
                        print("yes")
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {/* ... */
                        print("no")
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {/* ... */
                        print("should be shown")
                    }
                }).check()
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
        mTextMessage = findViewById(R.id.textMessage) as TextView
        mOutput = findViewById(R.id.output) as TextView
        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        Log.d("******","MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());


        run("http://192.168.0.82:8080/api/device/add",
                "{\"name\":\"emulator\", \"token\": \""+FirebaseInstanceId.getInstance().getToken().toString()+"\"}")

        //arraySnippets()
        //rangeSnippets()
        //conditionSnippets()
        //loopingSnippets()
    }

    val JSON = MediaType.parse("application/json; charset=utf-8")
    fun run(url: String, postBody: String) {
        val body = RequestBody.create(JSON, postBody)
        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
        })
    }



    private fun printOutput(message: String, heading: String = "") {
        //heading = Html.fromHtml("<strong>")
        mOutput!!.text = ""+ mOutput!!.text + "\n" + message
    }

    private fun arraySnippets() {

        val myArray = arrayListOf(1, 1.33, "String")
        printOutput(myArray.toString())

        myArray[1] = 1.00

        printOutput("The length of myArray is ${myArray.size}")
        printOutput("Is String in myArray? ${myArray.contains("String")} And is String 2 there? ${myArray.contains("String2")}")
        printOutput("Where is String in myArray? ${myArray.indexOf("String")} and String2? ${myArray.indexOf("String2")} ")

        val sqArray = Array(5, { i-> i * i } )
        printOutput(sqArray[1].toString())

        val intArray : Array<Int> = arrayOf(1,2,3,4,5)
        printOutput(intArray[3].toString())
    }

    private fun rangeSnippets() {
        val oneTo10 = 1..10
        val alpha = "A".."Z"
        printOutput("R in alpha :  ${ "R" in alpha }")

        val tenTo1 = 10.downTo(1)
        val twoTo20 = 2.rangeTo(20)

        val rng3 = oneTo10.step(3)

        for(x in rng3) printOutput("rng3 : $x")
        for(x in tenTo1.reversed()) printOutput("Reverse : $x")
    }

    private fun conditionSnippets() {
        val age = 8

        if(age < 5) {
            printOutput("Go to Preschool")
        } else if (age == 5) {
            printOutput("Go to Kindergarten")
        } else if ( (age > 5) && (age <= 17) ) {
            val grade = age - 5
            printOutput( "Go to Grade $grade" )
        } else {
            printOutput("Go to College")
        }

        when(age) {
            0,1,2,3,4 -> printOutput("Go to Preschool")
            5 -> printOutput("Go to Kindergarten")
            in 6..17 -> {
                val grade = age - 5
            }
        }
    }

    private fun loopingSnippets() {
        for(x in 1..10) {
            printOutput("Loop: $x")
        }

        val arr: Array<Int> = arrayOf(1,2,3,4,5)
        for(index in arr.indices) {
            printOutput("Index : $index, Value : ${arr[index]}")
        }

        for( (index, value) in arr.withIndex() ) {
            printOutput("Index: $index Value: $value")
        }
    }

    private fun functionSnippets() {
        printOutput(addNumbers(1,2).toString())
        printOutput(subtractNumbers(2,1).toString())
        printOutput(subtractNumbers(2).toString())
        printOutput(subtractNumbers(num2 = 3, num1 = 4).toString())
        sayHello("")
        val (two, three) = nextTwo(1)
        printOutput( "1, $two, $three" )
    }

    private fun addNumbers(num1 : Int, num2 : Int) : Int = num1 + num2
    private fun subtractNumbers(num1 : Int = 1, num2 : Int = 1) : Int = num1 - num2
    private fun sayHello(message: String = "") : Unit = printOutput(message)
    private fun nextTwo(num1: Int) : Pair<Int, Int> {
        return Pair(num1+1, num1+2)
    }

}
