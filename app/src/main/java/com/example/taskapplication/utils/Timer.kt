package com.example.taskapplication.utils

import android.os.Looper
import java.util.logging.Handler

class Timer(listener : OnTimerTickListener) {

    interface OnTimerTickListener{

        fun onTimerTick(duration : String)
    }

    private var handler= android.os.Handler(Looper.getMainLooper())
    private lateinit var runnable : Runnable

    private var duration = 0L
    private var delay=100L

    init {
        runnable= Runnable {
            duration+=delay
            handler.postDelayed(runnable,delay)
            listener.onTimerTick(format())
        }
    }

     fun start(){
        handler.postDelayed(runnable,delay)
    }

     fun pause(){
        handler.removeCallbacks(runnable)
    }

     fun stop(){
        handler.removeCallbacks(runnable)
        duration=0L
    }

    fun format() : String{
        val millis =duration %1000
        val second = (duration/1000) % 60
        val minuts = (duration / (1000*60)) % 60
        val hours = (duration/ (1000 * 60 * 60))

        var formatted : String=if (hours>0)
            "%02d:%02d:%02d".format(hours,minuts,second,millis/10)
        else
            "%02d:%02d:%02d".format(minuts,second,millis/10)

        return formatted
    }

}