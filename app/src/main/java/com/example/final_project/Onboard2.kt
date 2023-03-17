package com.example.final_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.final_project.databinding.ActivityOnboard2Binding

class Onboard2 : AppCompatActivity() {
    private lateinit var binding: ActivityOnboard2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboard2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onboard3button.setOnClickListener{
            startActivity(Intent(this, Onboard3::class.java))
            finish()
        }
    }
}