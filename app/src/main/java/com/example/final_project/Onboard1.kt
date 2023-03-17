package com.example.final_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.final_project.databinding.ActivityOnboard1Binding

class Onboard1 : AppCompatActivity() {

    private lateinit var binding: ActivityOnboard1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboard1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onboard2button.setOnClickListener{
            startActivity(Intent(this, Onboard2::class.java))
            finish()
        }
    }
}