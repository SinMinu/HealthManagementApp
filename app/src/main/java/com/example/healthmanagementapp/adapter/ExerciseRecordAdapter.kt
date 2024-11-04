package com.example.healthmanagementapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthmanagementapp.R
import com.example.healthmanagementapp.data.ExerciseRecord

class ExerciseRecordAdapter(private val records: List<ExerciseRecord>) :
    RecyclerView.Adapter<ExerciseRecordAdapter.ExerciseRecordViewHolder>() {

    class ExerciseRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textExerciseType: TextView = itemView.findViewById(R.id.textExerciseType)
        val textDuration: TextView = itemView.findViewById(R.id.textDuration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseRecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise_record, parent, false)
        return ExerciseRecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseRecordViewHolder, position: Int) {
        val record = records[position]
        holder.textExerciseType.text = record.exerciseType
        holder.textDuration.text = "${record.duration} minutes"
    }

    override fun getItemCount(): Int = records.size
}