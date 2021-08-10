package com.gaurav.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("tag913","mainactivity k on createme to aa rah hu")

        llStart.setOnClickListener{
            val intent = Intent(this, ExerciseActivity::class.java)
            Log.i("Tag912","Intent me ja raha hu")
            Log.i("tag913","on create me jana chahata hu")
            startActivity(intent)
        }
        llBMI.setOnClickListener{
            val intent= Intent(this,BMIActivity::class.java)
            startActivity(intent)
        }
        llHistory.setOnClickListener {
            val intent=Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }
        }
    }