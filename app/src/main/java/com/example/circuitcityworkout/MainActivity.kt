package com.example.circuitcityworkout

import android.animation.AnimatorInflater
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.size
import com.google.android.material.R.animator.m3_btn_state_list_anim

class MainActivity : AppCompatActivity() {
    private lateinit var c:Context
    private lateinit var btnStopWatch:Button
    private var isRunning = false
    private var rowParams: TableRow.LayoutParams = TableRow.LayoutParams(
        TableRow.LayoutParams.MATCH_PARENT,
        TableRow.LayoutParams.MATCH_PARENT)
    private lateinit var TVCount:TextView
    private lateinit var countDownTimer: CountDownTimer
    private var remainingTimeMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val day1Btn = findViewById<Button>(R.id.btnDay1)
        val day2Btn = findViewById<Button>(R.id.btnDay2)
        val day3Btn = findViewById<Button>(R.id.btnDay3)
        val day4Btn = findViewById<Button>(R.id.btnDay4)
        TVCount = findViewById(R.id.TVStopWatch)
        btnStopWatch = findViewById(R.id.btnStopWatch)
        c = this

        with(rowParams){
            weight = 1F

        }

        btnStopWatch.setOnClickListener {

            toggleCountdown()
        }
        day1Btn.setOnClickListener {
            val getData = GenerateWorkoutData()
            generateWorkoutTable(getData.generateDay1Workout())
        }
        day2Btn.setOnClickListener {
            val getData = GenerateWorkoutData()
            generateWorkoutTable(getData.generateDay2Workout())
        }
        day3Btn.setOnClickListener {
            val getData = GenerateWorkoutData()
            generateWorkoutTable(getData.generateDay3Workout())
        }
        day4Btn.setOnClickListener {
            val getData = GenerateWorkoutData()
            generateWorkoutTable(getData.generateDay4Workout())
        }
    }

    private fun toggleCountdown() {
        if (isRunning) {

            stopCountdown()
        } else {

            startCountdown()
        }
    }
    private fun updateCountdownText() {
        val seconds = (remainingTimeMillis / 1000).toInt() % 60
        val minutes = (remainingTimeMillis / 60000).toInt()
        val timeString = String.format("%02d:%02d", minutes, seconds)
        TVCount.text = timeString
    }

    private fun stopCountdown() {
        isRunning = false
        btnStopWatch.text = "Start Rest"
        countDownTimer.cancel()
    }

    private fun startCountdown() {
        val minutes = 3
        val millisInFuture = minutes * 60 * 1000L // Convert minutes to milliseconds

        countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeMillis = millisUntilFinished
                updateCountdownText()
            }

            override fun onFinish() {
                NotificationHelper(c).Notif()
                isRunning = false
                btnStopWatch.text = "Start Rest"
            }
        }

        isRunning = true
        btnStopWatch.text = "Stop Rest"
        countDownTimer.start()
    }

    private fun generateWorkoutTable(d:MutableList<workoutData>) {
        var mainLayout:LinearLayout = findViewById(R.id.mainLayout)

        if (mainLayout.size > 0){
            mainLayout.removeAllViews()
        }
        val displayMetrics = c.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        val width = (screenWidth * 0.9f).toInt()
        val height = (screenHeight * 0.5f).toInt()

        var tableParam: TableLayout.LayoutParams = TableLayout.LayoutParams(
            width,
            height)
        with(tableParam){
            bottomMargin = 10
            leftMargin = 10
            rightMargin = 10
        }
        var dataTable = TableLayout(c)
        dataTable.layoutParams = tableParam

        for (i in 0 until d.size){
            dataTable.addView(generateRowWithData(d[i]))
        }

        mainLayout.addView(dataTable)

    }

    private fun generateRowWithData(r:workoutData):View{
        val dataTableRow = TableRow(c)
        with(dataTableRow){
            gravity = Gravity.CENTER
            layoutParams = rowParams
            setBackgroundResource(R.drawable.border)

        }
        dataTableRow.addView(generateTVFromData(r.workout))
        dataTableRow.addView(generateTVforSets(r.sets))
        dataTableRow.addView(generateButtonForInstructions(r.dbImage))
        return dataTableRow
    }

    private fun generateButtonForInstructions(instructionID:Int):View {
        val layoutBtn = LinearLayout(c)
        with(layoutBtn){
            layoutParams = rowParams
            gravity = Gravity.CENTER
            setBackgroundResource(R.drawable.border)
            setPadding(5,5,5,5)
        }
        val instrucBtn = Button(c)
        with(instrucBtn){
            text = "Instructions"
            setBackgroundResource(R.drawable.buttonshape)
            setTextColor(getColor(R.color.white))
            textSize = 14F
            gravity = Gravity.CENTER
            layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
        }

        instrucBtn.setOnClickListener {
            val newActivityIntent = Intent(c,Instructions::class.java)
            newActivityIntent.putExtra("ImageID",instructionID)
            startActivity(newActivityIntent)
        }
        layoutBtn.addView(instrucBtn)
        return layoutBtn
    }

    private fun generateTVforSets(P_content:String):View{
        val generatedTV = TextView(c)
        val displayMetrics = c.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        val width = (screenWidth * 0.34f).toInt()
        val height = (screenHeight * 0.2f).toInt()

        with(generatedTV){
            text = P_content
            textSize = 14F
            //width = LayoutParams.WRAP_CONTENT
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            setPadding(5,5,5,5)
            layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT)
            setBackgroundResource(R.drawable.border)
        }
        return generatedTV
    }

    private fun generateTVFromData(P_content:String):View{
        val generatedTV = TextView(c)
        val displayMetrics = c.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        val width = (screenWidth * 0.34f).toInt()
        val height = (screenHeight * 0.2f).toInt()

        with(generatedTV){
            text = P_content
            textSize = 14F
            //width = LayoutParams.WRAP_CONTENT
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            setPadding(5,5,5,5)
               layoutParams = TableRow.LayoutParams(width,TableRow.LayoutParams.MATCH_PARENT)
            setBackgroundResource(R.drawable.border)
        }
        return generatedTV
    }

}