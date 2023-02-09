//Team Members:
//Patrick Lin <pjlin@csu.fullerton.edu> 887501237
//Gabriel Warkentin <gabrielwarkentin@csu.fullerton.edu> 886150697
//Preshus Dizon <preshus.dzn@csu.fullerton.edu> 889109922
//Abhiruchi Shinde <sabhiruchi7@csu.fullerton.edu> 885964049
//Gaurav Joshi <joshi.gaurav171@csu.fullerton.edu> 885948109

package com.example.imagecarouselapp

import android.content.Context
import android.util.Log
import java.io.File

private const val LOG_TAG = "MusicState"
class MusicState(_context: Context) {
    private var context: Context = _context
    private val textFileName = "playerState.txt"

    private fun makeFile(): File {
        return File(this.context.filesDir, this.textFileName)
    }

    fun saveText(s: String) {
        val file = this.makeFile()

        file.delete()
        file.writeText(s)
        Log.v(LOG_TAG, "String was written to file: " + s)
    }

    fun loadText(): String {
        val file = this.makeFile()
        var s = "0"

        if(file.exists()) {
            s = file.readText()
        }

        Log.v(LOG_TAG, "String was read from file: " + s)
        return s
    }
}