package com.devj.dcine.features.video.presenter

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.devj.dcine.features.video.domain.model.Video

class PlaybackActivity : ComponentActivity() {
    companion object {
        const val VIDEO = "video"
    }

    lateinit var video: Video
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            video = intent.extras?.getParcelable(VIDEO, Video::class.java)
                ?: throw IllegalArgumentException("Video data is required")
        } else {
            @Suppress("DEPRECATION")
            video = intent.extras?.getParcelable(VIDEO)
                ?: throw IllegalArgumentException("Video data is required")
        }
        setContent {
            PlaybackScreen(video)
        }
    }
}