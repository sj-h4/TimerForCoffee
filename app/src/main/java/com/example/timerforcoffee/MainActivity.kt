package com.example.timerforcoffee

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val handler = Handler()
    var timeValue = 45

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val runnable = object : Runnable {
            override fun run() {
                timeValue--
                timeToText(timeValue)?.let {
                    timeText.text = it
                }
                if (timeValue > 0) handler.postDelayed(this, 1000)
            }
        }

        //startボタンの処理
        start.setOnClickListener {
            handler.post(runnable)
        }

        //stopボタンの処理
        stop.setOnClickListener {
            handler.removeCallbacks(runnable)
        }

        //reetボタンの処理
        reset.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 45
            timeToText()?.let {
                timeText.text = it
            }
        }
    }

    private fun timeToText(time:Int=45): String? {
        return if (time < 0) {
            null
        }
        else if (time == 0) {
            "00:45"
        }
        else {
            val m = time % 3600 / 60
            val s = time % 60
            "%02d:%02d".format(m, s)
        }
    }
}