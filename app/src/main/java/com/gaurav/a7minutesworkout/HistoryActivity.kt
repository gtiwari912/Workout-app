package com.gaurav.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbar_history_activity)

        val actionbar= supportActionBar
        if (actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title="HISTORY"
        }
        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllCompletedDates()
    }

    private fun getAllCompletedDates(){
        val dbHandler=SqliteOpenHelper(this,null)
        val allCompletedDateList=dbHandler.getAllCompleteDateList()

        if (allCompletedDateList.size>0){
            tvHistory.visibility= View.VISIBLE
            rvHistory.visibility=View.VISIBLE
            tvNoDataAvailable.visibility=View.GONE

            rvHistory.layoutManager=LinearLayoutManager(this)
            val historyAdaper=HistoryAdapter(this,allCompletedDateList)
            rvHistory.adapter=historyAdaper
        }else{
            tvHistory.visibility= View.GONE
            rvHistory.visibility=View.GONE
            tvNoDataAvailable.visibility=View.VISIBLE
        }
    }
}