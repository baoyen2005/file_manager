package com.example.filesmanager.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import java.io.File
import java.util.*

// lay duong dan file >> folder trong file do
class FileOpen {
    fun openFile(context: Context, file :File){
        val uri : Uri = Uri.parse(file.absolutePath)

        // Activity A đang muốn xem thông tin đc hiển thị ở Activity B
        val intent = Intent(Intent.ACTION_VIEW)
        if(uri.toString().contains(".doc")){
            intent.setDataAndType(uri,"application/msword")
        }
        else if (uri.toString().contains(".pdf")){
            intent.setDataAndType(uri,"application/pdf")
        }
        else if (uri.toString().contains(".pptx")){
            intent.setDataAndType(uri,"application/mspowerpoint")
        }

        else if (uri.toString().contains(".xlsx")|| uri.toString().contains(".xls")){
            intent.setDataAndType(uri,"application/msexcel")
        }
        else if (uri.toString().contains(".txt")){
            intent.setDataAndType(uri,"application/notepad")
        }
        else if (uri.toString().contains(".mp3") || uri.toString().contains(".wav")) {
            intent.setDataAndType(uri, "audio/x-wav")
        }

        else if (uri.toString().lowercase(Locale.getDefault()).contains(".jpeg") ||
            uri.toString().lowercase(Locale.getDefault()).contains(".jpg") ||
            uri.toString().lowercase(Locale.getDefault()).contains(".png"))
            {
            intent.setDataAndType(uri, "image/jpeg")
        }
        else if (uri.toString().contains(".mp4")) {
            intent.setDataAndType(uri, "video/*")
        } else {
            intent.setDataAndType(uri, "*/*")
        }
        //addFlagsbạn đang nối thêm các cờ mới.
        //FLAG_GRANT_READ_URI_PERMISSION chỉ cấp quyền cho Uri được chỉ định trên data của mục đích
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        Log.d("TAG2",intent.toString())
         context.startActivity(intent)


    }
}