package com.example.final_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.final_project.databinding.ActivityOnboard3Binding

class Onboard3 : AppCompatActivity() {
    private lateinit var binding: ActivityOnboard3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboard3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.welcomeButton.setOnClickListener{
            startActivity(Intent(this,Welcome::class.java))
            finish()
        }
    }
}