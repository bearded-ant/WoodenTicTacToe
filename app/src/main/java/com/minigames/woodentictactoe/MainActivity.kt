package com.minigames.woodentictactoe

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.minigames.woodentictactoe.databinding.ActivityMainBinding
import com.minigames.woodentictactoe.game.AndroidPlayer
import com.minigames.woodentictactoe.game.EASY
import com.minigames.woodentictactoe.game.GameState
import com.minigames.woodentictactoe.game.HARD
import com.minigames.woodentictactoe.game.PLAYER_VS_BOT
import com.minigames.woodentictactoe.game.PLAYER_VS_PLAYER
import com.minigames.woodentictactoe.game.Settings

class MainActivity : AppCompatActivity() {

    private lateinit var gameState: GameState
    private lateinit var settings: Settings
    private lateinit var binding: ActivityMainBinding
    private var flag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            gameState = GameState()
            settings = Settings()
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newGame.setOnClickListener {
            startNewGame()
        }

        binding.settingsGame.setOnClickListener {
            showSettingsDialog(this)
        }

        initFieldButtons()

//        androidHint()
    }

    private fun startNewGame() {
        gameState = GameState()
        setFieldInvisible()
        flag = true
        blockField(false)
    }

    private fun initFieldButtons() {
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
                val drawable = R.drawable.xf
                img.setImageResource(drawable)
                img.visibility = View.VISIBLE
                gameState.makeMove(space, 'x')
                flag = false

                if (settings.gameMode == PLAYER_VS_BOT)
                    androidHint()

            } else {
                img.setImageResource(R.drawable.of)
                img.visibility = View.VISIBLE
                gameState.makeMove(space, 'o')
                flag = true
            }

        if (gameState.isOver()) {
            blockField(true)
            if (gameState.winnerExists()!=null) {
                showGameOverDialog(
                    this,
                    "Player ${gameState.winnerExists()!!.uppercaseChar()} ${getString(R.string.won)}"
                )
            } else
                showGameOverDialog(this, getString(R.string.draw))
        }
    }

    private fun showGameOverDialog(context: Context, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_game_over, null)
        alertDialogBuilder.setView(view)

        view.findViewById<TextView>(R.id.dialog_message).text = message

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        view.findViewById<AppCompatImageButton>(R.id.item_alert_btn_ok)
            .setOnClickListener {
                alertDialog.cancel()
            }
    }

    private fun showSettingsDialog(context: Context) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_settings, null)
        alertDialogBuilder.setView(view)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        val tempSettings = settings.copy()

        val gameMode = view.findViewById<RadioGroup>(R.id.item_settings_game_mode)
        val gameDifficulty = view.findViewById<RadioGroup>(R.id.item_settings_difficulty)
//        val gameWhoFirst = view.findViewById<RadioGroup>(R.id.item_settings_who_the_first)


        if (settings.gameMode == PLAYER_VS_PLAYER) {
            view.findViewById<RadioButton>(R.id.item_settings_player_vs_player).isChecked = true
            gameDifficulty.visibility = View.GONE
        } else {
            view.findViewById<RadioButton>(R.id.item_settings_player_vs_bot).isChecked = true
            gameDifficulty.visibility = View.VISIBLE
        }
        if (settings.gameDifficulty == EASY)
            view.findViewById<RadioButton>(R.id.item_settings_difficulty_easy).isChecked = true
        else
            view.findViewById<RadioButton>(R.id.item_settings_difficulty_hard).isChecked = true
//
//        if (settings.whoGoesFirst == X_THE_FIRST)
//            view.findViewById<RadioButton>(R.id.item_settings_who_the_first_X).isChecked = true
//        else
//            view.findViewById<RadioButton>(R.id.item_settings_who_the_first_O).isChecked = true
//

        gameMode.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.item_settings_player_vs_player -> {
                    tempSettings.gameMode = PLAYER_VS_PLAYER
                    gameDifficulty.visibility = View.GONE
                }

                R.id.item_settings_player_vs_bot -> {
                    tempSettings.gameMode = PLAYER_VS_BOT
                    gameDifficulty.visibility = View.VISIBLE
                }

                else -> Toast.makeText(this, "$checkedId", Toast.LENGTH_SHORT).show()
            }
        }
        gameDifficulty.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.item_settings_difficulty_easy -> tempSettings.gameDifficulty = EASY
                R.id.item_settings_difficulty_hard -> tempSettings.gameDifficulty = HARD
                else -> Toast.makeText(this, "$checkedId", Toast.LENGTH_SHORT).show()
            }
        }
//        gameWhoFirst.setOnCheckedChangeListener { _, checkedId ->
//            when (checkedId) {
//                R.id.item_settings_who_the_first_X -> tempSettings.whoGoesFirst = X_THE_FIRST
//                R.id.item_settings_who_the_first_O -> tempSettings.whoGoesFirst = O_THE_FIRST
//                else -> Toast.makeText(this, "$checkedId", Toast.LENGTH_SHORT).show()
//            }
//        }

        view.findViewById<AppCompatImageButton>(R.id.item_settings_btn_ok)
            .setOnClickListener {
                settings = tempSettings.copy()
                Log.d("settings", "showSettingsDialog: $tempSettings $settings ")
                startNewGame()
                alertDialog.cancel()
            }

        view.findViewById<AppCompatImageButton>(R.id.item_settings_btn_cancel)
            .setOnClickListener {
                alertDialog.dismiss()
            }
    }

    private fun blockField(flag: Boolean) {
        binding.isOver.visibility = if (flag) View.VISIBLE else View.GONE
    }

    private fun setFieldInvisible() {
        binding.cell00.itemImage.visibility = View.INVISIBLE
        binding.cell01.itemImage.visibility = View.INVISIBLE
        binding.cell02.itemImage.visibility = View.INVISIBLE
        binding.cell10.itemImage.visibility = View.INVISIBLE
        binding.cell11.itemImage.visibility = View.INVISIBLE
        binding.cell12.itemImage.visibility = View.INVISIBLE
        binding.cell20.itemImage.visibility = View.INVISIBLE
        binding.cell21.itemImage.visibility = View.INVISIBLE
        binding.cell22.itemImage.visibility = View.INVISIBLE
    }

    private fun androidHint() {
        if (!flag) {
            if (!gameState.isOver()) {
                var space = 0
                if (settings.gameDifficulty == EASY)
                    space = AndroidPlayer().easyMove(gameState)
                else
                    space = AndroidPlayer().hardMove(b,'x')

                gameState.makeMove(space, 'o')
                drawHint(space, R.drawable.of)
                flag = true
            }
        }
    }

    private fun drawHint(index: Int, img: Int) {

        when (index) {
            0 -> {
                binding.cell00.itemImage.visibility = View.VISIBLE
                binding.cell00.itemImage.setImageResource(img)
            }

            1 -> {
                binding.cell01.itemImage.visibility = View.VISIBLE
                binding.cell01.itemImage.setImageResource(img)
            }

            2 -> {
                binding.cell02.itemImage.visibility = View.VISIBLE
                binding.cell02.itemImage.setImageResource(img)
            }

            3 -> {
                binding.cell10.itemImage.visibility = View.VISIBLE
                binding.cell10.itemImage.setImageResource(img)
            }

            4 -> {
                binding.cell11.itemImage.visibility = View.VISIBLE
                binding.cell11.itemImage.setImageResource(img)
            }

            5 -> {
                binding.cell12.itemImage.visibility = View.VISIBLE
                binding.cell12.itemImage.setImageResource(img)
            }

            6 -> {
                binding.cell20.itemImage.visibility = View.VISIBLE
                binding.cell20.itemImage.setImageResource(img)
            }

            7 -> {
                binding.cell21.itemImage.visibility = View.VISIBLE
                binding.cell21.itemImage.setImageResource(img)
            }

            8 -> {
                binding.cell22.itemImage.visibility = View.VISIBLE
                binding.cell22.itemImage.setImageResource(img)
            }
        }
    }
}