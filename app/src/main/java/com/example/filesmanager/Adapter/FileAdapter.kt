package com.example.filesmanager.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.filesmanager.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FileAdapter (private var context: Context, private var fileList: ArrayList<File>, private val listener:OnItemClickListener):
    RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    var quanlityFile  = ArrayList<Int>()
    var lastModified = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return  FileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.file_container,parent,false))
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileItem = fileList[position]
        holder.tvName.text = fileList[position].name
        var date: Date = Date()
        date.time = fileList[position].lastModified() // tra ve int
        val simpleDateFormat:SimpleDateFormat = SimpleDateFormat("dd/MM")
        holder.tvTime.text = simpleDateFormat.format(date)

       // lastModified.clear()
        lastModified .add(position,simpleDateFormat.format(date))
        if(!fileList[position].isDirectory){
            if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".mp3")|| fileList[position].name.lowercase(
                    Locale.getDefault()).endsWith(".mp4")) {
                holder.imgFile.setImageResource(R.drawable.mp3)
            } else if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".gif")) {
                holder.imgFile.setImageResource(R.drawable.gif)
            } else if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".apk")) {
                holder.imgFile.setImageResource(R.drawable.apk)
            } else if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".jpeg") || (fileList[position].name.lowercase(
                    Locale.getDefault()
                )
                    .endsWith(".jpg"))
                || fileList[position].name.lowercase(Locale.getDefault()).endsWith(".png")) {
                holder.imgFile.setImageResource(R.drawable.jpg)
            } else if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".pdf")) {
                holder.imgFile.setImageResource(R.drawable.pdf)
            } else if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".docx")) {
                holder.imgFile.setImageResource(R.drawable.doc)
            }  else if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".pptx")) {
                holder.imgFile.setImageResource(R.drawable.ppt)
            }
            else if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".xlsx")|| fileList[position].name.lowercase(
                    Locale.getDefault()
                )
                    .endsWith(".xls")) {
                holder.imgFile.setImageResource(R.drawable.excel)
            }
            else if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".txt")) {
                holder.imgFile.setImageResource(R.drawable.txt)
            }
            else if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".wav")) {
                holder.imgFile.setImageResource(R.drawable.wav)
            }
            else if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".webp")) {
                holder.imgFile.setImageResource(R.drawable.webp)
            }
            else if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".xml")) {
                holder.imgFile.setImageResource(R.drawable.xml)
            }
            else {
                holder.imgFile.setImageResource(R.drawable.folde4)
            }
        }

        if (fileList[position].isDirectory) {
            val files = fileList[position].listFiles()
            if (files != null) {
                var length = files.size
                Log.d("aaa", "onBindViewHolder: " + length)
                if (length >= 1) {
                    if (length == 1) {
                        holder.tvSize.text = "1 file"
                        //quanlityFile.clear()
                        quanlityFile.add(position,1)
                    } else {
                        holder.tvSize.text = "$length files"
                        if(holder.tvSize.text != "empty")
                          //  quanlityFile.clear()
                            quanlityFile.add(position,length)
                    }
                } else {
                    holder.tvSize.text = "empty"
                  //  quanlityFile.clear()
                    if(holder.tvSize.text == "empty")  quanlityFile.add(position,0)
                }
            }else {
                holder.tvSize.text = "empty"
                //quanlityFile.clear()
                if(holder.tvSize.text == "empty") quanlityFile.add(position,0)
            }
            Log.d("bbbb", quanlityFile.toString())

        }
        else{
            val files = fileList[position]
            val size  = files.length()
            if(size >= 1024){
                var sizeF :Double = (size/1024).toDouble()
                holder.tvSize.text = "$sizeF Mb"
            }
            else
            {
                holder.tvSize.text = "$size Kb"
            }
        }
        //quanlityFile = 0
        // ko phai thu muc thi ko cho an tiep
        holder.itemView.setOnClickListener {
            if (listener!= null && fileItem.isDirectory){
                listener.onItemClick(fileItem)
            }
        }
        holder.tvOption.setOnClickListener {
            listener.onOptionsMenuClicked(it!!, fileItem,position)

        }


    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName : TextView =
            itemView.findViewById(R.id.tv_fileName)
        var tvSize : TextView = itemView.findViewById(R.id.tvFileSize)

        var tvTime: TextView = itemView.findViewById(R.id.tv_time)

        var container : CardView =
            itemView.findViewById(R.id.container)
        var imgFile : ImageView = itemView.findViewById(R.id.img_fileType)
        var tvOption :TextView = itemView.findViewById(R.id.textViewOptions)

    }
    interface OnItemClickListener{
        fun onItemClick(file: File)
        fun  onOptionsMenuClicked(view:View, file: File,position: Int)
      //  fun onBackPressed()
    }

    fun updateData(newList : List<File>){
       // fileList.clear()
        fileList = newList as ArrayList<File>
        Log.d("aaa", "updateData: "+newList.size)
        notifyDataSetChanged()
    }
}