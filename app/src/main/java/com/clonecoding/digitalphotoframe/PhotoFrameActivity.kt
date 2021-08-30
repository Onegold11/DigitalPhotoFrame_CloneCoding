package com.clonecoding.digitalphotoframe

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import java.util.*
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    /**
     * Photo image view
     */
    private val photoImageView: ImageView by lazy {
        findViewById(R.id.photoImageView)
    }

    /**
     * Background photo image view
     */
    private val backgroundPhotoImageView: ImageView by lazy {
        findViewById(R.id.backgroundPhotoImageView)
    }

    /**
     * Current photo position
     */
    private var currentPosition = 0

    /**
     * Photo list
     */
    private val photoList = mutableListOf<Uri>()

    private lateinit var timer: Timer

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_frame)

        this.getPhotoUriFromIntent()

        this.startTimer()
    }

    /**
     * Get photo uri from intent
     */
    private fun getPhotoUriFromIntent() {

        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0..size) {
            intent.getStringExtra("photo$i")?.let{
                this.photoList.add(Uri.parse(it))
            }
        }
    }

    /**
     * Start photo mode
     */
    private fun startTimer() {

        this.timer = timer(period = 5 * 1000) {
            runOnUiThread {

                Log.d(PhotoFrameActivity::class.simpleName, "5초 지나감 !!")
                
                val next = if (photoList.size <= currentPosition + 1) 0 else currentPosition + 1

                backgroundPhotoImageView.setImageURI(photoList[currentPosition])

                photoImageView.alpha = 0f
                photoImageView.setImageURI(photoList[next])
                photoImageView.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .start()

                currentPosition = next
            }
        }
    }

    override fun onStop() {
        super.onStop()

        Log.d(PhotoFrameActivity::class.simpleName, "onStop!!! timer cancel")

        this.timer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(PhotoFrameActivity::class.simpleName, "onDestroy!!! timer cancel")

        this.timer?.cancel()
    }

    override fun onStart() {
        super.onStart()

        Log.d(PhotoFrameActivity::class.simpleName, "onStart!!! timer start")

        startTimer()
    }
}