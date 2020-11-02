package com.example.timerforcoffee

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val handler = Handler()
    var timeValue = 0
    var cntValue = 0
    val limitTime = 45
    private lateinit var soundPool: SoundPool
    var sound = 0

    val runnable = object : Runnable {
        override fun run() {
            timeValue++
            if (timeValue == limitTime + 1) {
                cntValue++
                timeValue = 0
                soundPool.play(sound, 1.0f, 1.0f, 0, 0, 1.0f)

            }

            timeToText(timeValue)?.let {
                timeText.text = it
            }
            countText.text = "%d湯目".format(cntValue)

            if (cntValue <= 5) handler.postDelayed(this, 1000)
            else startAndReset()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(1).build()

        sound = soundPool.load(this, R.raw.cursor1, 1)

        startAndReset()
    }

    fun startAndReset(){
        // 実行中のときcntは1、それ以外では0
        var cnt = 0

        fun resetTimer() {
            handler.removeCallbacks(runnable)
            timeValue = 0
            cnt = 0
            timeToText()?.let {
                timeText.text = it
            }
        }

        //startボタンの処理
        start.setOnClickListener {
            if (cnt == 0) {
                cnt = 1
                cntValue += 1
                handler.post(runnable)
            }
        }


        //reetボタンの処理
        reset.setOnClickListener {
            resetTimer()
            cntValue = 0
            countText.text = "%d湯目".format(cntValue)
        }
    }



    private fun timeToText(time:Int=0): String? {
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


