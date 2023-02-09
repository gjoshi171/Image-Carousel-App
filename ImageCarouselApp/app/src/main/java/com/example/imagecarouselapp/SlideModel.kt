//Team Members:
//Patrick Lin <pjlin@csu.fullerton.edu> 887501237
//Gabriel Warkentin <gabrielwarkentin@csu.fullerton.edu> 886150697
//Preshus Dizon <preshus.dzn@csu.fullerton.edu> 889109922
//Abhiruchi Shinde <sabhiruchi7@csu.fullerton.edu> 885964049
//Gaurav Joshi <joshi.gaurav171@csu.fullerton.edu> 885948109

package com.example.imagecarouselapp

import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*

private const val LOG_TAG = "ImgSlide:ViewModel"

class SlideModel : ViewModel() {
    data class Song (
        val title: String,
        val album: String,
        val artist: String,
        val img_url: String,
        val audio_url: String,
        val id: UUID = UUID.randomUUID()
    )

    var songArray2 = listOf<Song>(
        Song("Mercy Mercy Me (The Ecology)",
             "What's Going On",
             "Marvin Gaye",
             "https://github.com/Arbalest007/411-Assets/raw/main/01%20-%20whats%20going%20on.png",
             "https://github.com/Arbalest007/411-Assets/raw/main/01%20-%20Mercy%20Mercy%20Me%20(The%20Ecology)-.mp3"
             ),
        Song("Como Te Quiero",
             "Con Todo El Mundo",
             "Khruangbin",
             "https://github.com/Arbalest007/411-Assets/raw/main/02%20-%20con%20todo%20el%20mundo.jpg",
             "https://github.com/Arbalest007/411-Assets/raw/main/02%20-%20Como%20Te%20Quiero.mp3"
             ),
        Song("Dan The Dancer",
             "Puberty 2",
             "Mitski",
             "https://github.com/Arbalest007/411-Assets/raw/main/03%20-%20Puberty%202.png",
             "https://github.com/Arbalest007/411-Assets/raw/main/03%20-%20Dan%20The%20Dancer.mp3"
            )
        )

    var songArray = listOf<Song>(
        Song("Mercy Mercy Me (The Ecology)",
            "What's Going On",
            "Marvin Gaye",
            "https://quingabe.com/cs411/images/01%20-%20whats%20going%20on.png",
            "https://quingabe.com/cs411/audio/01%20-%20Mercy%20Mercy%20Me%20%28The%20Ecology%29-.mp3"
        ),
        Song("Como Te Quiero",
            "Con Todo El Mundo",
            "Khruangbin",
            "https://quingabe.com/cs411/images/02%20-%20con%20todo%20el%20mundo.jpg",
            "https://quingabe.com/cs411/audio/02%20-%20Como%20Te%20Quiero.mp3"
        ),
        Song("Dan The Dancer",
            "Puberty 2",
            "Mitski",
            "https://quingabe.com/cs411/images/03%20-%20Puberty%202.png",
            "https://quingabe.com/cs411/audio/03%20-%20Dan%20The%20Dancer.mp3"
        )
    )
    private lateinit var mediaPlayer: MediaPlayer
    private var playerPrepped = false
    private var playerPaused = false
    var PausedLength = MutableLiveData<Int>(0)
    var songIndex = MutableLiveData<Int>(0)

    private fun setSongIndex(index: Int) {
        this.songIndex.value = this.keepSongIndexInBounds(index)
    }

    fun getSongIndex(): Int {
        Log.d(LOG_TAG, "The current index returned is: " + songIndex.value.toString())
        return this.songIndex.value!!
    }
    fun increaseSongIndex(): Int? {
        this.setSongIndex(this.songIndex.value!! + 1)
        this.PausedLength.value = 0
        return this.songIndex.value
    }
    fun decreaseSongIndex(): Int? {
        this.setSongIndex(this.songIndex.value!! - 1)
        this.PausedLength.value = 0
        return this.songIndex.value
    }
    fun keepSongIndexInBounds(ind: Int): Int {
        if (ind >= this.songArray.size) {
            return 0
        }
        else if ( ind < 0) {
            return this.songArray.size - 1
        }
        return ind
    }

    fun getCurrentSongImg(): String {
        Log.d(LOG_TAG, "The current song image URL is: " + this.songArray[this.getSongIndex()].img_url)
        return this.songArray[this.getSongIndex()].img_url
    }
    
    fun getCurrentSongAudio(): String {
        return this.songArray[this.getSongIndex()].audio_url
    }
    fun getCurrentSongInfo(): Song {
        return this.songArray[this.getSongIndex()]
    }

    fun getPosition(): String {
        var seconds = this.PausedLength.value!! / 1000;
        var minutes = seconds / 60;
        var finalSeconds = seconds % 60;
        var finalSecondsFormatted = "";

        if(finalSeconds < 10)
            finalSecondsFormatted = "0%d"
        else finalSecondsFormatted = "%d"

        return String.format("%d:" + finalSecondsFormatted, minutes, finalSeconds)
    }

    private fun prepMediaPlayer() {
        Log.v(LOG_TAG, "Prepping media player")
        var songCounter = this.getSongIndex()
        var audioUrl = this.getCurrentSongAudio()
        this.mediaPlayer = MediaPlayer()
        this.mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
        this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            this.mediaPlayer.setDataSource(audioUrl)
            this.mediaPlayer.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // return true if playing, false if paused
    fun playPauseAudio(): Boolean {
        if (!this.playerPrepped) {
            this.prepMediaPlayer()
            this.playerPrepped = true
            this.playerPaused = true
        }

        Log.v(LOG_TAG, "playerPaused flag: ${this.playerPaused}")
        if (this.playerPaused) {
            Log.v(LOG_TAG, "Play/Resume song")
            this.mediaPlayer.seekTo(this.PausedLength.value!!);
            this.mediaPlayer.start();
            this.playerPaused = false;
            return true // playing
        }
        else {
            Log.v(LOG_TAG, "Pause song")
            this.mediaPlayer.pause();
            this.PausedLength.value = this.mediaPlayer.currentPosition;
            this.playerPaused = true;
            return false // paused
        }
    }

    fun stopAudio() {
        if (this.playerPrepped) {
            Log.v(LOG_TAG, "Stop Song")
            this.mediaPlayer.release()
            this.playerPrepped = false
            this.playerPaused = true
            this.PausedLength.value = 0
        }
    }

    fun nextSong() {
        if (!this.playerPrepped) {
            this.increaseSongIndex()
            this.playPauseAudio()
        }
        else {
            this.stopAudio()
            this.increaseSongIndex()
            this.playPauseAudio()
        }
    }

    fun prevSong() {
        if (!this.playerPrepped) {
            this.decreaseSongIndex()
            this.playPauseAudio()
        }
        else {
            this.stopAudio()
            this.decreaseSongIndex()
            this.playPauseAudio()
        }
    }
}
