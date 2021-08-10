package com.gaurav.a7minutesworkout

import androidx.core.graphics.rotationMatrix

class Constants {

    companion object{
        fun defaultExerciseList():ArrayList<ExerciseModel>{
            val exerciseList = ArrayList<ExerciseModel>()
            val jumpingJack =ExerciseModel(1,"Jumping Jack",R.drawable.ic_jumping_jacks,false,false)
            exerciseList.add(jumpingJack)
            val wallSit = ExerciseModel(2,"Wall Sit",R.drawable.ic_wall_sit,false,false)
            exerciseList.add(wallSit)
            val pushup = ExerciseModel(3,"Push up",R.drawable.ic_push_up,false,false)
            exerciseList.add(pushup)
            val abdominalCrunch=ExerciseModel(4,"Abdominal Crunch",R.drawable.ic_abdominal_crunch,false,false)
            exerciseList.add(abdominalCrunch)
            val stepUpOnChair = ExerciseModel(5,"SetUp on Chair",R.drawable.ic_step_up_onto_chair,false,false)
            exerciseList.add(stepUpOnChair)
            val squat =ExerciseModel(6,"Squat",R.drawable.ic_squat,false,false)
            exerciseList.add(squat)
            val tricepDipOnChair = ExerciseModel(7,"Tricep Dip On Chair",R.drawable.ic_triceps_dip_on_chair,false,false)
            exerciseList.add(tricepDipOnChair)
            val plank = ExerciseModel(8,"Plank",R.drawable.ic_plank,false,false)
            exerciseList.add(plank)
            val highKneesRunningInPlace = ExerciseModel(9,"High Knees Running in place",R.drawable.ic_high_knees_running_in_place,false,false)
            exerciseList.add(highKneesRunningInPlace)
            val lunges =ExerciseModel(10,"Lunges",R.drawable.ic_lunge,false,false)
            exerciseList.add(lunges)
            val pushUpAndRotation= ExerciseModel(11,"Pushup and Rotation",R.drawable.ic_push_up_and_rotation,false,false)
            exerciseList.add(pushUpAndRotation)
            val sidePlank=ExerciseModel(12,"Side Plank",R.drawable.ic_side_plank,false,false)
            exerciseList.add(sidePlank)
            return exerciseList
        }
    }
}