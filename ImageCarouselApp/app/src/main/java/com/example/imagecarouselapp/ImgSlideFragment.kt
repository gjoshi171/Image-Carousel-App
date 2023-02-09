//Team Members:
//Patrick Lin <pjlin@csu.fullerton.edu> 887501237
//Gabriel Warkentin <gabrielwarkentin@csu.fullerton.edu> 886150697
//Preshus Dizon <preshus.dzn@csu.fullerton.edu> 889109922
//Abhiruchi Shinde <sabhiruchi7@csu.fullerton.edu> 885964049
//Gaurav Joshi <joshi.gaurav171@csu.fullerton.edu> 885948109

package com.example.imagecarouselapp

import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.squareup.picasso.Picasso

private const val LOG_TAG = "ImgSlideFragment"
class ImgSlideFragment: Fragment() {
    private lateinit var slideModel: SlideModel
    private lateinit var ImgView: ImageView
    private lateinit var ImgCaption: TextView
    private lateinit var seekBar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.slideModel = ViewModelProviders.of(this.requireActivity()).get(SlideModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.img_slide_fragment, container, false)
        this.ImgView = view.findViewById(R.id.img_view)
        this.ImgCaption = view.findViewById(R.id.img_caption)

        this.slideModel.songIndex.observe(viewLifecycleOwner, Observer {
            this.seekBar.text = slideModel.getPosition()
            this.ImgCaption.text = slideModel.getCurrentSongInfo().title
            this.updateToCurrentImage()
        })

        this.slideModel.PausedLength.observe(viewLifecycleOwner, Observer {
            this.seekBar.text = slideModel.getPosition()
        })

        this.seekBar = view.findViewById(R.id.seekLocation)

        return view
    }

    override fun onResume() {
        super.onResume()
        this.updateToCurrentImage()
    }

    private fun updateToCurrentImage() {
        Log.v(LOG_TAG,"ImgSlideFrag.updateToCurrentImage started")
        Picasso.get()
            .load(this.slideModel.getCurrentSongImg())
            .into(this.ImgView)
        Log.v(LOG_TAG,"ImgSlideFrag.updateToCurrentImage passed")
    }
}
