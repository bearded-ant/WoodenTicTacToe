package com.minigames.woodentictactoe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.minigames.woodentictactoe.databinding.ActivityMainBinding
import com.minigames.woodentictactoe.game.Board
import com.minigames.woodentictactoe.game.GameState

class MainActivity : AppCompatActivity() {

    private var board = Board(3)
    private var gameState = GameState()
    private lateinit var binding: ActivityMainBinding
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
    }

    private fun initButtons() {
        binding.cell00.itemCard.setOnClickListener {
            playerHint(0, binding.cell00.itemImage)
        }
        binding.cell01.itemCard.setOnClickListener {
            playerHint(1, binding.cell01.itemImage)
        }
        binding.cell02.itemCard.setOnClickListener {
            playerHint(2, binding.cell02.itemImage)
        }
        binding.cell10.itemCard.setOnClickListener {
            playerHint(3, binding.cell10.itemImage)
        }
        binding.cell11.itemCard.setOnClickListener {
            playerHint(4, binding.cell11.itemImage)
        }
        binding.cell12.itemCard.setOnClickListener {
            playerHint(5, binding.cell12.itemImage)
        }
        binding.cell20.itemCard.setOnClickListener {
            playerHint(6, binding.cell20.itemImage)
        }
        binding.cell21.itemCard.setOnClickListener {
            playerHint(7, binding.cell21.itemImage)
        }
        binding.cell22.itemCard.setOnClickListener {
            playerHint(8, binding.cell22.itemImage)
        }
    }

    private fun playerHint(space: Int, img: ImageView) {
        if (gameState.isBlankSpace(space))
            if (flag) {
                img.setImageResource(R.drawable.xf)
                img.visibility = View.VISIBLE
                gameState.makeMove(space, 'x')
//                board.placePiece('x', space)
                flag = false
            } else {
                img.setImageResource(R.drawable.of)
                img.visibility = View.VISIBLE
                gameState.makeMove(space, 'o')
                flag = true
//                board.placePiece('0', space)
            }

        if (gameState.isOver())
            if (gameState.winnerExists()) {
                showCustomAlertDialog(this, "${gameState.winner} won!")
            } else Toast.makeText(this, "ничья", Toast.LENGTH_LONG).show()
    }

    fun showCustomAlertDialog(context: Context, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_game_over, null)
        alertDialogBuilder.setView(view)

        view.findViewById<TextView>(R.id.dialog_message).text = message
        view.findViewById<AppCompatImageButton>(R.id.item_alert_btn_ok)
            .setOnClickListener { }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}