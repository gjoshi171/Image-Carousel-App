//Team Members:
//Patrick Lin <pjlin@csu.fullerton.edu> 887501237
//Gabriel Warkentin <gabrielwarkentin@csu.fullerton.edu> 886150697
//Preshus Dizon <preshus.dzn@csu.fullerton.edu> 889109922
//Abhiruchi Shinde <sabhiruchi7@csu.fullerton.edu> 885964049
//Gaurav Joshi <joshi.gaurav171@csu.fullerton.edu> 885948109

package com.example.imagecarouselapp

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders

private const val LOG_TAG = "SlideControllerFragment"
class SlideControllerFragment : Fragment() {
    private lateinit var slideModel: SlideModel
    private lateinit var music: MusicState
    private lateinit var fragmentMG: FragmentManager
    private lateinit var mainActivity: MainActivity
    private lateinit var PrevButton: Button
    private lateinit var NextButton: Button
    private lateinit var playButton: Button
    private lateinit var stopButton: Button

    private lateinit var seekBar: TextView

    private var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = getActivity() as MainActivity
        music = mainActivity.textInputModel

        fragmentMG = mainActivity.supportFragmentManager

        this.slideModel= ViewModelProviders.of(this.requireActivity()).get(SlideModel::class.java)
        this.slideModel.songIndex.value = this.loadIndex()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        this.music.saveText(this.slideModel.getSongIndex().toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.slide_controller_fragment, container, false)
        this.playButton = view.findViewById(R.id.play_button)
        this.stopButton = view.findViewById(R.id.stop_button)
        this.NextButton = view.findViewById(R.id.next_button)
        this.PrevButton = view.findViewById(R.id.prev_button)


        this.playButton.setOnClickListener {
            this.isPlaying = this.slideModel.playPauseAudio()

            if (this.isPlaying) {
                this.playButton.text = getString(R.string.pause_button_text)
            }
            else {
                this.playButton.text = getString(R.string.play_button_text)
            }
        }

        this.stopButton.setOnClickListener {
            this.slideModel.stopAudio()
            this.playButton.text = getString(R.string.play_button_text)
        }

        this.NextButton.setOnClickListener {
            this.slideModel.nextSong()
            this.playButton.text = getString(R.string.pause_button_text)

            val frag = ImgSlideFragment()
            fragmentMG.beginTransaction().replace(R.id.img_slide_frame, frag).commit()
        }

        this.PrevButton.setOnClickListener {
            this.slideModel.prevSong()
            this.playButton.text = getString(R.string.pause_button_text)

            val frag = ImgSlideFragment()
            fragmentMG.beginTransaction().replace(R.id.img_slide_frame, frag).commit()
        }

        return view
    }

    private fun loadIndex(): Int {
        var tempString = this.music.loadText()
        Log.d(LOG_TAG, "The index to load is: " + tempString)
        return tempString.toInt()
    }

    private fun saveIndex(s: String) {
        Log.d(LOG_TAG, "The index to save is: " + s)
        this.music.saveText(s)
    }
}
