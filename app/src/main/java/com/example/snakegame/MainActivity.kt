package com.example.snakegame

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.snakegame.R
import com.example.snakegame.databinding.ActivityMainBinding
import com.example.snakegame.databinding.ActivityMainPageBinding
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : Activity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var board: RelativeLayout
    private lateinit var border: RelativeLayout
    private lateinit var lilu: RelativeLayout
    private lateinit var upButton: Button
    private lateinit var downButton: Button
    private lateinit var leftButton: Button
    private lateinit var rightButton: Button
    private lateinit var pauseButton: Button
    private lateinit var newgame: Button
    private lateinit var resume: Button
    private lateinit var playagain: Button
    private lateinit var score: Button
    private lateinit var meat: ImageView
    private lateinit var snake: ImageView
    private var snakeSegments = mutableListOf<ImageView>()
    private val handler = Handler()
    private var delayMillis = 30L
    private var currentDirection = "right"
    private var scorex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        board = binding.board
        border = binding.relativeLayout
        upButton = binding.up
        downButton = binding.down
        leftButton = binding.left
        rightButton = binding.right
        pauseButton = binding.pause
        newgame = binding.newGame
        resume = binding.resume
        playagain = binding.playagain
        score = binding.score
        meat = ImageView(this)
        snake = ImageView(this)
        val lilu = findViewById<LinearLayout>(R.id.lilu)

        binding.button5.setOnClickListener {
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        board.visibility = View.INVISIBLE
        playagain.visibility = View.INVISIBLE
        score.visibility = View.INVISIBLE
//        score2.visibility = View.INVISIBLE

        newgame.setOnClickListener {


            board.visibility = View.VISIBLE
            newgame.visibility = View.INVISIBLE
            resume.visibility = View.INVISIBLE
//            score2.visibility = View.VISIBLE


            snake.setImageResource(R.drawable.snake)
            snake.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            board.addView(snake)
            snakeSegments.add(snake) // Add the new snake segment to the list


            var snakeX = snake.x
            var snakeY = snake.y
            meat.setImageResource(R.drawable.food)
            meat.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            board.addView(meat)
            //sachmlis random reg
            val random = Random()
            val randomX =
                random.nextInt(801) - 400
            val randomY =
                random.nextInt(801) - 400


            meat.x = randomX.toFloat()
            meat.y = randomY.toFloat()

            fun checkFoodCollision() {
                val distancebarier = 50

                val distance = sqrt((snake.x - meat.x).pow(2) + (snake.y - meat.y).pow(2))

                if (distance < distancebarier) { // distanciis shemowmeba

                    val newSnake =
                        ImageView(this) // axali snake nawilis damateba
                    newSnake.setImageResource(R.drawable.snake)
                    newSnake.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    board.addView(newSnake)

                    snakeSegments.add(newSnake)

                    val randomX =
                        random.nextInt(801) - -100
                    val randomY =
                        random.nextInt(801) - -100


                    meat.x = randomX.toFloat()
                    meat.y = randomY.toFloat()


                    delayMillis--
                    scorex++


                }
            }


            val runnable = object : Runnable {
                override fun run() {

                    for (i in snakeSegments.size - 1 downTo 1) { // poziciebis daaptaetiba
                        snakeSegments[i].x = snakeSegments[i - 1].x
                        snakeSegments[i].y = snakeSegments[i - 1].y
                    }


                    when (currentDirection) {
                        "up" -> {
                            snakeY -= 10
                            if (snakeY < -490) { // zevidan gadacdena
                                snakeY = -490f
                                border.setBackgroundColor(getResources().getColor(R.color.red))
                                playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                lilu.visibility = View.INVISIBLE

                                score.text = "your score is  " + scorex.toString()
                                score.visibility = View.VISIBLE


                            }

                            snake.translationY = snakeY
                        }

                        "down" -> {
                            snakeY += 10
                            val maxY =
                                board.height / 2 - snake.height + 30
                            if (snakeY > maxY) { // qvevidan gadacdena
                                snakeY = maxY.toFloat()
                                border.setBackgroundColor(getResources().getColor(R.color.red))
                                playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                lilu.visibility = View.INVISIBLE

                                score.text =
                                    "your score is  " + scorex.toString() // Update delay text view
                                score.visibility = View.VISIBLE
//                                score2.visibility = View.INVISIBLE


                            }
                            snake.translationY = snakeY
                        }

                        "left" -> {
                            snakeX -= 10
                            if (snakeX < -490) { // marcxnidan gadacdena
                                snakeX = -490f
                                border.setBackgroundColor(getResources().getColor(R.color.red))
                                playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                lilu.visibility = View.INVISIBLE
                                score.text = "your score is  " + scorex.toString()
                                score.visibility = View.VISIBLE

                            }
                            snake.translationX = snakeX
                        }

                        "right" -> {
                            snakeX += 10
                            val maxX =
                                board.height / 2 - snake.height + 30
                            if (snakeX > maxX) { // marjvnidan gadacdena
                                snakeX = maxX.toFloat()
                                border.setBackgroundColor(getResources().getColor(R.color.red))
                                playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                lilu.visibility = View.INVISIBLE

                                score.text =
                                    "your score is  " + scorex.toString()
                                score.visibility = View.VISIBLE


                            }
                            snake.translationX = snakeX
                        }

                        "pause" -> {
                            snakeX += 0
                            snake.translationX = snakeX
                        }
                    }

                    checkFoodCollision()
                    handler.postDelayed(this, delayMillis)
                }
            }
            handler.postDelayed(runnable, delayMillis)

            binding.up.setOnClickListener {
                currentDirection = "up"
            }

            binding.down.setOnClickListener {
                currentDirection = "down"
            }

            binding.left.setOnClickListener {
                currentDirection = "left"
            }

            binding.right.setOnClickListener {
                currentDirection = "right"
            }

            binding.pause.setOnClickListener {
                currentDirection = "pause"
                board.visibility = View.INVISIBLE
                newgame.visibility = View.VISIBLE
                resume.visibility = View.VISIBLE
            }

            binding.resume.setOnClickListener {
                currentDirection = "right"
                board.visibility = View.VISIBLE
                newgame.visibility = View.INVISIBLE
                resume.visibility = View.INVISIBLE
            }

            binding.playagain.setOnClickListener {
                recreate()
            }

        }


    }

}