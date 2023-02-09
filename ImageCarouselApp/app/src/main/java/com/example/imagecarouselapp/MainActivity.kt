//Team Members:
//Patrick Lin <pjlin@csu.fullerton.edu> 887501237
//Gabriel Warkentin <gabrielwarkentin@csu.fullerton.edu> 886150697
//Preshus Dizon <preshus.dzn@csu.fullerton.edu> 889109922
//Abhiruchi Shinde <sabhiruchi7@csu.fullerton.edu> 885964049
//Gaurav Joshi <joshi.gaurav171@csu.fullerton.edu> 885948109

package com.example.imagecarouselapp

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    val textInputModel: MusicState = MusicState(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            0
        )

        val ImgSlideFrame = this.supportFragmentManager.findFragmentById(
            R.id.img_slide_frame
        )
        if (ImgSlideFrame == null ) {
            val frag = ImgSlideFragment()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.img_slide_frame, frag)
                .commit()
        }

        val SlideControllerFrame = this.supportFragmentManager.findFragmentById(
            R.id.slide_controller_frame
        )
        if (SlideControllerFrame == null ) {
            val frag = SlideControllerFragment()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.slide_controller_frame, frag)
                .commit()
        }

        val SongInfoFrame = this.supportFragmentManager.findFragmentById(
            R.id.song_info_frame
        )
        if (SongInfoFrame == null) {
            val frag = SongInfoFragment()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.song_info_frame, frag)
                .commit()
        }
    }
}