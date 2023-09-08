package com.example.circuitcityworkout

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class GenerateWorkoutData {
    fun generateDay1Workout():MutableList<workoutData>{
        var tempList:MutableList<workoutData> = mutableListOf()
        tempList.add(workoutData(workout = "Barbell Squats", sets = "4 x 10", dbImage = R.drawable.barbellsquat))
        tempList.add(workoutData(workout = "Machine/Dumbbell Row", sets = "4 x 10", dbImage = R.drawable.dumbbellrow))
        tempList.add(workoutData(workout = "Rear Delt fly/Face Pull", sets = "4 x 10", dbImage = R.drawable.facepullfly))
        tempList.add(workoutData(workout = "Hip Thrust", sets = "4 x 10", dbImage = R.drawable.barbellhipthrust))
        return  tempList
    }

    fun generateDay2Workout():MutableList<workoutData>{
        var tempList:MutableList<workoutData> = mutableListOf()
        tempList.add(workoutData(workout = "Barbell/Dumbbell Incline Bench Press", sets = "4 x 10", dbImage = R.drawable.inclinedbbb))
        tempList.add(workoutData(workout = "Cable Row", sets = "4 x 10", dbImage = R.drawable.cablerows))
        tempList.add(workoutData(workout = "Dumbbell Front Raise", sets = "4 x 10", dbImage = R.drawable.dumbbellfrontraise))
        tempList.add(workoutData(workout = "Vertical/Normal Leg Press", sets = "4 x 10", dbImage = R.drawable.legpress))
        return  tempList
    }

    fun generateDay3Workout():MutableList<workoutData>{
        var tempList:MutableList<workoutData> = mutableListOf()
        tempList.add(workoutData(workout = "Hack Squat", sets = "4 x 10", dbImage = R.drawable.hacksquat))
        tempList.add(workoutData(workout = "Weighted Dips", sets = "4 x 10", dbImage = R.drawable.weighteddips))
        tempList.add(workoutData(workout = "Lat Pull Down", sets = "4 x 10", dbImage = R.drawable.latpulldown))
        tempList.add(workoutData(workout = "Dumbbell Lateral Raise", sets = "4 x 10", dbImage = R.drawable.dumbbelllateralraise))
        return  tempList
    }

    fun generateDay4Workout():MutableList<workoutData>{
        var tempList:MutableList<workoutData> = mutableListOf()
        tempList.add(workoutData(workout = "Barbell/Dumbbell Bench Press", sets = "4 x 10", dbImage = R.drawable.benchpress))
        tempList.add(workoutData(workout = "Dumbbell Shoulder Press", sets = "4 x 10", dbImage = R.drawable.dumbbellshoulderpress))
        tempList.add(workoutData(workout = "Machine PullDown", sets = "4 x 10", dbImage = R.drawable.frontpulldown))
        tempList.add(workoutData(workout = "Chest fly Cable/Machine/Dumbbell", sets = "4 x 10", dbImage = R.drawable.pecfly))
        return  tempList
    }

}

class NotificationHelper(var co:Context){
    private val CHANNEL_ID = "message id"
    private val NOTIFICATION_ID = 123
    /**set notification**/
    fun Notif(){
        createNotificationChannel()
        val senInt = Intent(co,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingInt = PendingIntent.getActivities(co, 0, arrayOf(senInt), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        /**set notification dialog**/
        val isnotification = NotificationCompat.Builder(co,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Circuit City Workout")
            .setContentText("Times up ! ,Yeah Buddy")
            .setContentIntent(pendingInt)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(
                NotificationCompat.InboxStyle()
                .addLine("Get to work !")
                .addLine("Work Hard")
                .addLine("Stay Hard!")
                .addLine("Who's Gonna Carry the Boats"))
            .build()

        if (ActivityCompat.checkSelfPermission(
                co,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(co)
            .notify(NOTIFICATION_ID,isnotification)
    }
    /*create createNotificationChannel*/
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name = CHANNEL_ID
            val descrip = "Channel description"
            val imports = NotificationManager.IMPORTANCE_DEFAULT
            val channels = NotificationChannel(CHANNEL_ID,name,imports).apply {
                description = descrip
            }
            val notificationManager = co.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channels)

        }
    }
}

data class workoutData(var workout:String,
    var sets:String,var dbImage:Int)