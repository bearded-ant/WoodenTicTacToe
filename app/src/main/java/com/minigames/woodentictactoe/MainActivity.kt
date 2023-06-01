package com.minigames.woodentictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minigames.woodentictactoe.databinding.ActivityMainBinding
import com.minigames.woodentictactoe.game.Board

class MainActivity : AppCompatActivity() {


    private var board = Board(3)
    private lateinit var binding: ActivityMainBinding
    private var choice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loadViewButton.setOnClickListener {
            if (binding.choice.text.toString().isNotBlank()) {
                choice = binding.choice.text.toString().toInt()
                board = board.placePiece('x', choice)
            }
            binding.arrayView.text = board.toArray().toString()
        }


    }
}