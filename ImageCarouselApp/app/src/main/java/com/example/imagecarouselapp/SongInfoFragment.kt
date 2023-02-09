//Team Members:
//Patrick Lin <pjlin@csu.fullerton.edu> 887501237
//Gabriel Warkentin <gabrielwarkentin@csu.fullerton.edu> 886150697
//Preshus Dizon <preshus.dzn@csu.fullerton.edu> 889109922
//Abhiruchi Shinde <sabhiruchi7@csu.fullerton.edu> 885964049
//Gaurav Joshi <joshi.gaurav171@csu.fullerton.edu> 885948109

package com.example.imagecarouselapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders


private const val LOG_TAG = "ImgSlideFragment"
class SongInfoFragment: Fragment() {
    private lateinit var slideModel: SlideModel
    private lateinit var title: TextView
    private lateinit var album: TextView
    private lateinit var artist: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.slideModel = ViewModelProviders.of(this.requireActivity()).get(SlideModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.song_detail_fragment, container, false)
        this.title = view.findViewById(R.id.song_title)
        this.album = view.findViewById(R.id.song_album)
        this.artist = view.findViewById(R.id.song_artist)

        this.slideModel.songIndex.observe(viewLifecycleOwner, Observer {
            var song = slideModel.getCurrentSongInfo()
            this.title.text = song.title
            this.album.text = song.album
            this.artist.text = song.artist
        })

        Log.v(LOG_TAG, "Info box has been created!")
        return view
    }

}

