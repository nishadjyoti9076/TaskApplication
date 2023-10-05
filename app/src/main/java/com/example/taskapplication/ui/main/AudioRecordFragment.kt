package com.example.taskapplication.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.taskapplication.R
import com.example.taskapplication.databinding.FragmentAudioRecordBinding
import java.io.IOException
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [AudioRecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioRecordFragment : Fragment() {

    companion object{
        private const val LOG_TAG = "AudioRecordTest"
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
        const val REQUEST_CODE=200
    }

    private var permission= arrayOf(android.Manifest.permission.RECORD_AUDIO)
    private var permission_granted=false
    private val speechRecognizer : SpeechRecognizer by lazy {
        SpeechRecognizer.createSpeechRecognizer(requireContext())
    }

    lateinit var binding : FragmentAudioRecordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentAudioRecordBinding.inflate(inflater,container,false)
        permission_granted= ActivityCompat.checkSelfPermission(requireContext(),permission[0])==
                PackageManager.PERMISSION_GRANTED

        if (!permission_granted)
            ActivityCompat.requestPermissions(requireActivity(),permission,REQUEST_CODE)



        binding.start.setOnTouchListener { view, motionEvent ->

            when(motionEvent.action){

                MotionEvent.ACTION_UP -> {
                    speechRecognizer.stopListening()
                    return@setOnTouchListener true

                }
                MotionEvent.ACTION_DOWN -> {

                    if (permission_granted){
                        startListen()
                    }
                    return@setOnTouchListener true

                }
                else -> {

                    return@setOnTouchListener true
            }
            }
        }

        return binding.root
    }


    fun startListen(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())

        speechRecognizer.setRecognitionListener(object  : RecognitionListener{
            override fun onReadyForSpeech(p0: Bundle?) {
            }

            override fun onBeginningOfSpeech() {
                binding.etRecoedData.setText("Listening")
            }

            override fun onRmsChanged(p0: Float) {
            }

            override fun onBufferReceived(p0: ByteArray?) {
            }

            override fun onEndOfSpeech() {
            }

            override fun onError(p0: Int) {
            }

            override fun onResults(bundle: Bundle?) {
                bundle?.let {
                    val result=it.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    binding.etRecoedData.setText(result?.get(0))
                }
            }

            override fun onPartialResults(p0: Bundle?) {
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
            }
        })

        speechRecognizer.startListening(intent)
    }

    private var fileName: String = ""
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(android.Manifest.permission.RECORD_AUDIO)



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode== REQUEST_CODE)
            permission_granted=grantResults[0]==PackageManager.PERMISSION_GRANTED
    }

    private fun onRecord(start: Boolean) = if (start) {
        startRecording()
    } else {
        stopRecording()
    }

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }
        }
    }

    private fun stopPlaying() {
        player?.release()
        player = null
    }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }

            start()
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

}