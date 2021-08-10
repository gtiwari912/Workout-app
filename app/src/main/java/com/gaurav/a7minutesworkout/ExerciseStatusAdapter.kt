package com.gaurav.a7minutesworkout

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusAdapter(val items:ArrayList<ExerciseModel>,val context:Context) : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>(){
    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val tvItem=view.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exercise_status,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:ExerciseModel=items[position]

        holder.tvItem.text=model.getID().toString()

        if(model.getIsSelected()){
            holder.tvItem.background=ContextCompat.getDrawable(context,R.drawable.item_circular_thin_color_accent_border)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
            Log.i("Tag912","White set karne ki koshish karta hu")
        } else if(model.getIsCompleted()){
            Log.i("Tag912","Green set karne ki koshish karta hu")
            holder.tvItem.background=ContextCompat.getDrawable(context,R.drawable.item_circular_color_ascent_background)
            holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            holder.tvItem.background=ContextCompat.getDrawable(context,R.drawable.item_circular_color_gray_background)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}