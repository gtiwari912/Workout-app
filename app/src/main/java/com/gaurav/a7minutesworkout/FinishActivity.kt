package com.gaurav.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setSupportActionBar(toolbar_finish_activity)
        val actionbar = supportActionBar
        if (actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener {
            finish()
        }

        addDateToDatabase()
    }

    private fun addDateToDatabase(){
        val calendar=Calendar.getInstance()
        val dateTime=calendar.time
        Log.i("tag912",""+dateTime)
        val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
        val date=sdf.format(dateTime)

        val dbHandler=SqliteOpenHelper(this,null)
        dbHandler.addDate(date)
        Log.i("tag912","added date")
        }
}