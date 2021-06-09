package com.example.filesmanager.Adapter

import android.content.Context
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

class FileAdapter (private var context: Context, private var fileList: ArrayList<File>, private val listener:OnItemClickListener):
    RecyclerView.Adapter<FileAdapter.FileViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return  FileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.file_container,parent,false))
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.tvName.text = fileList[position].name

        var date: Date = Date()
        date.time = fileList[position].lastModified() // tra ve int

        val simpleDateFormat:SimpleDateFormat = SimpleDateFormat("dd/MM")
        holder.tvTime.text = simpleDateFormat.format(date)

        if(!fileList[position].isDirectory){
            if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".mp3")|| fileList[position].name.lowercase(
                    Locale.getDefault()
                )
                    .endsWith(".mp4")) {
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
            } else if (fileList[position].name.lowercase(Locale.getDefault()).endsWith(".doc")) {
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
            val files: Array<File> = fileList[position].listFiles()
            if (files != null) {
                val length = files.size
                if (length >= 1) {
                    if (length == 1) {
                        holder.tvSize.text = "$length file"
                    } else {
                        holder.tvSize.text = "$length files"
                    }
                } else {
                    holder.tvSize.text = "empty"
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var tvName : TextView =
            itemView.findViewById(R.id.tv_fileName)
        var tvSize : TextView = itemView.findViewById(R.id.tvFileSize)

        var tvTime: TextView = itemView.findViewById(R.id.tv_time)

        var container : CardView =
            itemView.findViewById(R.id.container)
        var imgFile : ImageView = itemView.findViewById(R.id.img_fileType)

        init {
            itemView.setOnClickListener  (this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(fileList[position])

        }

    }
    interface OnItemClickListener{
        fun onItemClick(file: File)

    }
}