package com.gaurav.a7minutesworkout

import android.media.MediaPlayer
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private var restTimer:CountDownTimer?=null
    private var exerciseTimer:CountDownTimer?=null
    private var restProgress=0
    private var exerciseProgress=0
    private var mMediaPlayer:MediaPlayer? = null
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1
    private var tts:TextToSpeech?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionbar = supportActionBar
        if(actionbar!= null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        tts= TextToSpeech(this,this)
        exerciseList=Constants.defaultExerciseList()
        setUpRestView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
    }
    private fun setRestProgressBar(){
        progressBar.progress=restProgress
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                playSound()
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setupExerciseView()
            }
        }.start()
    }
    private fun setUpRestView(){
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        llRestView.visibility=View.VISIBLE
        llExerciseView.visibility=View.GONE
        tvNextExercise.text=exerciseList!![currentExercisePosition+1].getName()
        setRestProgressBar()
    }
    private fun playSound(){
        if (mMediaPlayer==null){
            mMediaPlayer= MediaPlayer.create(this@ExerciseActivity,R.raw.tick_sound)
            mMediaPlayer!!.start()
        }else{
            mMediaPlayer!!.start()
        }
    }


    private fun setExerciseProgressBar(){
        exerciseProgressBar.progress=exerciseProgress
        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                playSound()
                exerciseProgress++
                exerciseProgressBar.progress = 30 - exerciseProgress
                tvExerciseTimer.text = (30-exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"Exercise Done!",Toast.LENGTH_SHORT).show()
                if (currentExercisePosition<11){
                    setUpRestView()
                }else{
                    Toast.makeText(applicationContext,"Congratulations! You finished the 7 Minutes Activity.\nHappy Healthy Life!",Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }
    private fun setupExerciseView(){
        if (exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }
        llRestView.visibility=View.GONE
        llExerciseView.visibility=View.VISIBLE
        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseProgressBar()
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text=exerciseList!![currentExercisePosition].getName()
    }

    override fun onInit(status: Int) {
        if (status==TextToSpeech.SUCCESS){
            tts!!.setLanguage(Locale.ENGLISH)
            if (status==TextToSpeech.LANG_NOT_SUPPORTED||status==TextToSpeech.LANG_MISSING_DATA){
                Log.e("Tag912","Language not supported or not installed")
            }
        }else{
            Log.e("Tag912","tts not initialized !")
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
}