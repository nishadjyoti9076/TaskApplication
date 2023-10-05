package com.example.taskapplication.ui.main

import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.ActivityCompat
import com.example.taskapplication.R
import com.example.taskapplication.databinding.ActivityMain2Binding
import kotlinx.coroutines.NonCancellable.start
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Timer

const val REQUEST_CODE=200
class MainActivity : AppCompatActivity() ,
    com.example.taskapplication.utils.Timer.OnTimerTickListener {

    lateinit var binding : ActivityMain2Binding
    private var permission= arrayOf(android.Manifest.permission.RECORD_AUDIO)
    private var permission_granted=false

    private lateinit var recorder : MediaRecorder
    private var dirPath = ""
    private var fileName = ""
    private var isRecording = false
    private var isPause = false
    private lateinit var vibrator: Vibrator

    private lateinit var timer : com.example.taskapplication.utils.Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        permission_granted=ActivityCompat.checkSelfPermission(this,permission[0])==
                PackageManager.PERMISSION_GRANTED

        if (!permission_granted)
            ActivityCompat.requestPermissions(this,permission,REQUEST_CODE)


        timer= com.example.taskapplication.utils.Timer(this)
        vibrator=getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        binding.start.setOnClickListener {
            when{
                isPause -> resumeRecording()
                isRecording -> pauseRecorder()

                else -> startRecording()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50,VibrationEffect.DEFAULT_AMPLITUDE))
            }

        }
    }

    private fun pauseRecorder(){
        recorder.pause()
        isPause=true
        timer.pause()
    }

    private fun resumeRecording(){
        recorder.resume()
        isPause=false
        timer.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode== REQUEST_CODE)
            permission_granted=grantResults[0]==PackageManager.PERMISSION_GRANTED
    }

    private fun startRecording(){
        if (!permission_granted){
            ActivityCompat.requestPermissions(this,permission, REQUEST_CODE)
            return
        }

        recorder= MediaRecorder()


        dirPath="${externalCacheDir?.absolutePath}"
        var simpleDateFormat =SimpleDateFormat("yyyy/MM/dd")
        var date = simpleDateFormat.format(Date())
        fileName="audio_recorder_$date"

        recorder.apply {

            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("$dirPath$fileName.mp3")
            try {
                prepare()
            }catch (e : IOException){

            }

        }
        start()
        isRecording=true
        isPause=false
        timer.pause()
    }

    private fun stopRecording(){
        timer.stop()
    }

    override fun onTimerTick(duration: String) {
        binding.time.setText(duration)
    }
}