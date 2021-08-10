package com.gaurav.a7minutesworkout

import android.app.Dialog
import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.io.IOException
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener,
    MediaPlayer.OnPreparedListener {
    private var restTimer:CountDownTimer?=null
    private var exerciseTimer:CountDownTimer?=null
    private var restProgress=0
    private var exerciseProgress=0
    private var exerciseTimerDuration:Long=30
    private var restTimerDuration:Long=10;
    private var mMediaPlayer:MediaPlayer? = null
    private var musicMediaPlayer:MediaPlayer? =null
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1
    private var tts:TextToSpeech?=null
    private var currentExerciseSongLink:String?=null
    private var songList = Songs()
    private var musicMode:Boolean=true

    private var exerciseAdapter:ExerciseStatusAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("tag913","Oncreate me to kumsekum aa hi raha hu")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionbar = supportActionBar
        if(actionbar!= null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogForBackButton()
        }
        tts= TextToSpeech(this,this)

        Log.i("tag913","Oncreate me smMusic swithc k uper hu")
        swMusicSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            musicMode = swMusicSwitch.isChecked
            if (!musicMode){
                stopAndResetMusic()
            }else{
                Snackbar.make(buttonView,"If your internet is slow turn off music mode for better experience",Snackbar.LENGTH_LONG).show()
                playMusic(currentExercisePosition)
            }
        }
        exerciseList=Constants.defaultExerciseList()
        setupExerciseRecyclerView()
        Log.i("tag913","On create me setuprestview k uper hu")
        setUpRestView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        if (exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }
        if (tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(mMediaPlayer!=null){
            mMediaPlayer!!.pause()
            mMediaPlayer!!.reset()
            mMediaPlayer!!.stop()
        }
        if(musicMediaPlayer!=null){
            musicMediaPlayer!!.pause()
            musicMediaPlayer!!.reset()
            musicMediaPlayer!!.stop()
        }
    }
    private fun setRestProgressBar(){
        Log.i("tag912","setuprestprogressbar bhi karne aa raha hu")
        progressBar.progress=restProgress
        restTimer = object : CountDownTimer(restTimerDuration*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                playSound()
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                Log.i("tag912","iske baad setupExerciseView karuga")
                setupExerciseView()
            }
        }.start()
    }
    private fun setUpRestView(){
        Log.i("tag912","setuprestview karne bhi aa raha hu")
        Log.i("tag913","restview me if musicmode k uper hu")
        if(musicMode){
            playMusic(currentExercisePosition)
        }
        Log.i("tag913","Setup restview k if musicmode wala condition paar kar gaya hu")
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
    private fun playMusic(times:Int){
        Log.i("tag912","Music wale function me to aa raha hu aur times ka value hai $times")
        if (times==-1){
            currentExerciseSongLink=songList.getRandomSong()
        }else{
            currentExerciseSongLink=songList.getRandomSong(currentExerciseSongLink!!)
        }
        Log.i("tag912",currentExerciseSongLink!!)
        try {
            musicMediaPlayer= MediaPlayer()
            musicMediaPlayer!!.setDataSource(currentExerciseSongLink)
            musicMediaPlayer!!.setOnPreparedListener(this@ExerciseActivity)
            musicMediaPlayer!!.prepareAsync()
//            musicMediaPlayer!!.start()
        }catch(e:IOException){
            Log.i("tag912","Link hi nahi le pa raha hu")
            e.printStackTrace()
        }
    }

    private fun stopAndResetMusic(){
//        musicMediaPlayer!!.stop()
        musicMediaPlayer!!.reset()
    }


    private fun setExerciseProgressBar(){
        exerciseProgressBar.progress=exerciseProgress
        Log.i("tag912","Iske baad music wale function ko bulauga")
//        if(musicMode){
//            playMusic(currentExercisePosition)
//        }
        exerciseTimer = object : CountDownTimer(exerciseTimerDuration*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                playSound()
                exerciseProgress++
                exerciseProgressBar.progress = 30 - exerciseProgress
                tvExerciseTimer.text = (30-exerciseProgress).toString()
            }
            override fun onFinish(){
                if(currentExercisePosition<exerciseList?.size!!-1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    stopAndResetMusic()
                    setUpRestView()
                }
                else{
                    finish()
                    val intent= Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)

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

    private fun setupExerciseRecyclerView(){
        rvExerciseStatus.layoutManager= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!,this)
        rvExerciseStatus.adapter=exerciseAdapter
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun customDialogForBackButton(){
        val customDialog=Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)
        customDialog.tvYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()

    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp!!.start()
    }
}