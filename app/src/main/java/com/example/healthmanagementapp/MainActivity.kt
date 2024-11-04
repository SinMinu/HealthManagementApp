package com.example.healthmanagementapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthmanagementapp.adapter.ExerciseRecordAdapter
import com.example.healthmanagementapp.data.ExerciseRecord
import com.example.healthmanagementapp.ui.theme.HealthManagementAppTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : ComponentActivity() {
    private val exerciseRecords = mutableListOf<ExerciseRecord>()
    private lateinit var adapter: ExerciseRecordAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("exercise_records_prefs", MODE_PRIVATE)
        loadRecords()

        val spinnerExerciseType: Spinner = findViewById(R.id.spinnerExerciseType)
        val editTextDuration: EditText = findViewById(R.id.editTextDuration)
        val buttonSave: Button = findViewById(R.id.buttonSave)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewRecords)

        val exerciseTypes = listOf("Running", "Cycling", "Swimming", "Yoga", "Weightlifting")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, exerciseTypes)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerExerciseType.adapter = adapterSpinner

        adapter = ExerciseRecordAdapter(exerciseRecords)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        buttonSave.setOnClickListener {
            val exerciseType = spinnerExerciseType.selectedItem?.toString() ?: ""
            val durationText = editTextDuration.text.toString().trim()
            val duration = durationText.toIntOrNull()

            if (exerciseType.isNotEmpty() && duration != null && duration > 0) {
                val newRecord = ExerciseRecord(exerciseType, duration)
                exerciseRecords.add(newRecord)

                adapter.notifyDataSetChanged()
                editTextDuration.text.clear()

                saveRecords()
            } else {
                if (durationText.isEmpty() || duration == null || duration <= 0) {
                    editTextDuration.error = "Enter a valid positive number"
                }
            }
        }
    }

    private fun saveRecords() {
        val json = gson.toJson(exerciseRecords)
        sharedPreferences.edit().putString("exercise_records", json).apply()
    }

    private fun loadRecords() {
        val json = sharedPreferences.getString("exercise_records", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<ExerciseRecord>>() {}.type
            val savedRecords: MutableList<ExerciseRecord> = gson.fromJson(json, type)
            exerciseRecords.addAll(savedRecords)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HealthManagementAppTheme {
        Greeting("Android")
    }
}