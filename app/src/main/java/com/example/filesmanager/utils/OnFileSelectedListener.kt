package com.example.filesmanager.utils

import android.view.View
import java.io.File

interface OnFileSelectedListener {
    fun onFileLongClicked(file: File, position: Int)
    fun onFileClicked(file: File)
    fun onFileMenuFileClicked(view: View,file: File)
}