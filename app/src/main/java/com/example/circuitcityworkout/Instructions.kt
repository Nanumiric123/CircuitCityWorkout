package com.example.circuitcityworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class Instructions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)


        val gifImageView = findViewById<ImageView>(R.id.IVInstruction)

        // Load the GIF using Glide and set it to loop
        Glide.with(this)
            .asGif()
            .load(intent.getIntExtra("ImageID",R.drawable.barbellbenchpress))
            .into(gifImageView)
    }
}