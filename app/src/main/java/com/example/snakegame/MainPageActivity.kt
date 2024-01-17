package com.example.snakegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snakegame.databinding.ActivityMainPageBinding


class MainPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener{
            val intent = Intent(this, TicTacToeActivity::class.java)
            startActivity(intent)
        }
        binding.button3.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}