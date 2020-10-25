package com.example.timerforcoffee

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val handler = Handler()
    var timeValue = 45

    val runnable = object : Runnable {
        override fun run() {
            timeValue--
            timeToText(timeValue)?.let {
                timeText.text = it
            }
            if (timeValue > 0) handler.postDelayed(this, 1000)
            else startAndReset()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startAndReset()
    }

    fun startAndReset(){
        // 実行中のときcntは1、それ以外では0
        var cnt = 0

        fun resetTimer() {
            handler.removeCallbacks(runnable)
            timeValue = 45
            cnt = 0
            timeToText()?.let {
                timeText.text = it
            }
        }

        if (timeValue == 0) {
            resetTimer()
        }

        //startボタンの処理
        start.setOnClickListener {
            if (cnt == 0) {
                cnt = 1
                handler.post(runnable)
            }
        }


        //reetボタンの処理
        reset.setOnClickListener {
            resetTimer()
        }
    }



    private fun timeToText(time:Int=45): String? {
        return if (time < 0) {
            null
        }
        else {
            val m = time % 3600 / 60
            val s = time % 60
            "%02d:%02d".format(m, s)
        }
    }
}