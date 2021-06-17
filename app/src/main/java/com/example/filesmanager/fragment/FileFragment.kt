package com.example.filesmanager.fragment

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filesmanager.Adapter.FileAdapter
import com.example.filesmanager.R
import com.example.filesmanager.activity.MainActivity
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log


class FileFragment : Fragment(), FileAdapter.OnItemClickListener {

    private val fileList = ArrayList<File>()
    lateinit var fileAdapter: FileAdapter
    private var filePath: String? = null
    private var fileClick : File? = null
    private var stFileClick = Stack<String>()
    lateinit var tvInformation :TextView
    lateinit var drawerLayoutFile:DrawerLayout
    lateinit var mDrawerToggle: ActionBarDrawerToggle

    fun newInstance(): FileFragment {
        return FileFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_file, container, false)
        tvInformation = view.findViewById(R.id.txt_infomation)
       Log.d("aaaaaa",tvInformation.text.toString())
        drawerLayoutFile = view.findViewById(R.id.drawerLayoutFile)

        //mDrawerToggle= ActionBarDrawerToggle(activity,drawerLayoutFile,R.string.d)

       // drawerLayoutFile.openDrawer(Gravity.RIGHT)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_menu)
        //tvInformation = view.findViewById(R.id.txt_infomation)
      // Log.d("aaaaaa",tvInformation.toString())

        val bundle = this.arguments
        filePath = bundle?.getString("path")

        setUpRecyclerView(view)


        checkPermission()

    }

    private fun setUpRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycle_internal)
        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fileAdapter = FileAdapter(requireContext(), fileList, this)
        recyclerView?.adapter = fileAdapter
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity, READ_EXTERNAL_STORAGE
                    )
                ) {
                    Toast.makeText(context, "Chờ cấp quyền truy cập", Toast.LENGTH_SHORT).show()
                } else {
                    ActivityCompat.requestPermissions(
                        context as Activity, arrayOf(
                            READ_EXTERNAL_STORAGE,
                            WRITE_EXTERNAL_STORAGE
                        ), 305
                    )
                }
            } else {
                displayFiles()
            }


        } else {
            displayFiles()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 305) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayFiles()
            } else {
                Toast.makeText(context, "permission not run", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun findFiles(root: File): ArrayList<File> {
        Log.d("aaa", "findFiles: "+root.absolutePath)
        val arrayList = ArrayList<File>()
        val files = root.listFiles()
        if (files != null ) {
            arrayList.clear()
            arrayList.addAll(files)
        }
        Log.d("aaa", "findFiles: " + arrayList.size)

        return arrayList


    }

    private fun displayFiles() {


//        val internalStorage: String = System.getenv("EXTERNAL_STORAGE")
        val DIR_INTERNAL = Environment.getExternalStorageDirectory().toString()
        val storage = File(DIR_INTERNAL)
        //val fileList = ArrayList<File>()
        //if (filePath != null) {
          //  fileList.clear()
            //fileList.addAll(findFiles(File(filePath)))
        //} else {
            fileList.clear()
            fileList.addAll(findFiles(storage))
       //}

        fileAdapter.updateData(fileList)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
        //return
    }

    override fun onItemClick(file: File) {

      //  val parentFragment = parentFragment as FileManagerFragment
        //parentFragment?.goNextFolder(file.absolutePath.toString())


        stFileClick.add(file.absolutePath)

        val arrayList = ArrayList<File>()
        val files = file.listFiles()
        if (files != null ) {
            arrayList.clear()
            arrayList.addAll(files)
        }

        fileList.clear()
        fileList.addAll(findFiles(File(file.absolutePath)))
        fileAdapter.notifyDataSetChanged()
       // onBackPressed()




    }


    override fun onOptionsMenuClicked(view: View, file: File,position:Int) {
        val popupMenu = PopupMenu(context,view)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            if (item.itemId == R.id.ic_information) {
                drawerLayoutFile.openDrawer(Gravity.RIGHT)
                findInformation(file,position)

                Toast.makeText(context, "Item 1", Toast.LENGTH_SHORT).show()
                return@OnMenuItemClickListener true
            } else if (item.itemId == R.id.ic_mark) {
                Toast.makeText(requireContext(), "Item 2", Toast.LENGTH_SHORT).show()
                return@OnMenuItemClickListener true
            } else if (item.itemId == R.id.ic_rename) {
                Toast.makeText(context, "doi ten", Toast.LENGTH_SHORT).show()
                return@OnMenuItemClickListener true
            }
            if (item.itemId == R.id.ic_copy) {
                Toast.makeText(context, "sao chep", Toast.LENGTH_SHORT).show()
                return@OnMenuItemClickListener true
            } else if (item.itemId == R.id.ic_cut) {
                Toast.makeText(requireContext(), "cut", Toast.LENGTH_SHORT).show()
                return@OnMenuItemClickListener true
            } else if (item.itemId == R.id.ic_delete) {
                    dialogYesOrNo(requireContext(),"Delete","Bạn có chắc muốn xóa file không?",
                    DialogInterface.OnClickListener{dialog, id ->
                        val temp = fileList.remove(file)

                        fileAdapter.notifyDataSetChanged()
                    })

                //Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show()

                return@OnMenuItemClickListener true
            }
            if (item.itemId == R.id.ic_press) {
                Toast.makeText(context, "nens", Toast.LENGTH_SHORT).show()
                return@OnMenuItemClickListener true
            } else if (item.itemId == R.id.ic_share) {
                Toast.makeText(requireContext(), "share", Toast.LENGTH_SHORT).show()
                return@OnMenuItemClickListener true
            } else if (item.itemId == R.id.ic_bank) {
                Toast.makeText(context, "bank", Toast.LENGTH_SHORT).show()
                return@OnMenuItemClickListener true
            }
            false
        })
        popupMenu.inflate(R.menu.file_menu)

        popupMenu.show()
    }

    private fun findInformation(file: File,position:Int) {
        val size  = file.length()//Byte
        var sizeMB :Double = 0.toDouble() // Mb
        var sizeGB:Double = 0.toDouble() // GB
        if(size >= 1024){
            sizeMB = (size/1024).toDouble()

        }
        else if(size> 1024*1024){
            sizeGB = (sizeMB)/1024
        }
        if(file.isDirectory && size > 1024*1024 && fileAdapter.quanlityFile[position]>1 ){
            Log.d("yen", fileAdapter.quanlityFile.toString())
            tvInformation!!.text ="${file.name}" +
                    "\n\nKiểu: folder"+
                    "\nKích thước: $sizeGB Gb"+
                    "\nNội dung: ${fileAdapter.quanlityFile[position]} files"+
                    "\nSửa đổi lần cuối: ${fileAdapter.lastModified[position]}"+
                    "\nQuy trình: ${file.absolutePath}"

        }
        else if(file.isDirectory && size > 1024*1024 && fileAdapter.quanlityFile[position] == 1 ){
            tvInformation!!.text ="${file.name}" +
                    "\n\nKiểu: folder"+
                    "\nKích thước: $sizeGB Gb"+
                    "\nNội dung: ${fileAdapter.quanlityFile[position]} file"+
                    "\nSửa đổi lần cuối: ${fileAdapter.lastModified[position]}"+
                    "\nQuy trình: ${file.absolutePath}"

        }
        else if(file.isDirectory && size > 1024*1024 && fileAdapter.quanlityFile[position] == 0 ){
            tvInformation!!.text ="${file.name}" +
                    "\n\nKiểu: folder"+
                    "\nKích thước: $sizeGB Gb"+
                    "\nNội dung: 0 file"+
                    "\nSửa đổi lần cuối: ${fileAdapter.lastModified[position]}"+
                    "\nQuy trình: ${file.absolutePath}"

        }
        else  if(file.isDirectory && 1024 < size && size< 1024*1024&& fileAdapter.quanlityFile[position]>1 ){
            tvInformation!!.text ="${file.name}" +
                    "\n\nKiểu: folder"+
                    "\nKích thước: $sizeMB Mb"+
                    "\nNội dung: ${fileAdapter.quanlityFile[position]} files"+
                    "\nSửa đổi lần cuối: ${fileAdapter.lastModified[position]}"+
                    "\nQuy trình: ${file.absolutePath}"

        }
        else  if(file.isDirectory && 1024 < size && size< 1024*1024 && fileAdapter.quanlityFile[position]==1 ){
            tvInformation!!.text ="${file.name}" +
                    "\n\nKiểu: folder"+
                    "\nKích thước: $sizeMB Mb"+
                    "\nNội dung: ${fileAdapter.quanlityFile[position]} file"+
                    "\nSửa đổi lần cuối: ${fileAdapter.lastModified[position]}"+
                    "\nQuy trình: ${file.absolutePath}"
        }
        else  if(file.isDirectory && 1024 < size && size< 1024*1024 && fileAdapter.quanlityFile[position]==0 ){
            tvInformation!!.text ="${file.name}" +
                    "\n\nKiểu: folder"+
                    "\nKích thước: $sizeMB Mb"+
                    "\nNội dung: 0 file"+
                    "\nSửa đổi lần cuối: ${fileAdapter.lastModified[position]}"+
                    "\nQuy trình: ${file.absolutePath}"
        }
        else  if(file.isDirectory && size<1024&& fileAdapter.quanlityFile[position]==1 ){
            tvInformation!!.text ="${file.name}" +
                    "\n\nKiểu: folder"+
                    "\nKích thước: $size Kb"+
                    "\nNội dung: ${fileAdapter.quanlityFile[position]} file"+
                    "\nSửa đổi lần cuối: ${fileAdapter.lastModified[position]}"+
                    "\nQuy trình: ${file.absolutePath}"

        }
        else  if(file.isDirectory && size<1024&& fileAdapter.quanlityFile[position]>1 ){
            tvInformation!!.text ="${file.name}" +
                    "\n\nKiểu: folder"+
                    "\nKích thước: $size Kb"+
                    "\nNội dung: ${fileAdapter.quanlityFile[position]} files"+
                    "\nSửa đổi lần cuối: ${fileAdapter.lastModified[position]}"+
                    "\nQuy trình: ${file.absolutePath}"

        }
        else  if(file.isDirectory && size<1024&& fileAdapter.quanlityFile[position]==1 ){
            tvInformation!!.text ="${file.name}" +
                    "\n\nKiểu: folder"+
                    "\nKích thước: $size Kb"+
                    "\nNội dung: 0 file"+
                    "\nSửa đổi lần cuối: ${fileAdapter.lastModified[position]}"+
                    "\nQuy trình: ${file.absolutePath}"

        }
        else if(!file.isDirectory&& size > 1024*1024){
            tvInformation!!.text ="${file.name}" +
                    "\n\nKiểu: file"+
                    "\nKích thước: $sizeGB Gb"+
                    "\nSửa đổi lần cuối: ${fileAdapter.lastModified[position]}"+
                    "\nQuy trình: ${file.absolutePath}"

        }
        else if(!file.isDirectory&&size>1024 && size < 1024*1024){
            tvInformation!!.text ="${file.name}" +
                    "\n\nKiểu: file"+
                    "\nKích thước: $sizeMB Mb"+
                    "\nSửa đổi lần cuối: ${fileAdapter.lastModified[position]}"+
                    "\nQuy trình: ${file.absolutePath}"

        }
        else if(!file.isDirectory&&size<1024){
            tvInformation!!.text ="${file.name}" +
                    "\n\nKiểu: file"+
                    "\nKích thước: $size Kb"+
                    "\nSửa đổi lần cuối: ${fileAdapter.lastModified[position]}"+
                    "\nQuy trình: ${file.absolutePath}"
        }
    }

    fun dialogYesOrNo(context: Context,  title: String, message: String,
        listener: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
            listener.onClick(dialog, id)
        })
        builder.setNegativeButton("No", null)
        val alert = builder.create()
        alert.setTitle(title)
        alert.setMessage(message)
        alert.show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                @SuppressLint("WrongConstant")
                override fun handleOnBackPressed() {
                  //  fileList.clear()
                  if(stFileClick.size >1 ){
                      fileList.clear()
                     // fileAdapter.updateData
                      stFileClick.pop()
                      fileList.addAll(findFiles(File(stFileClick[stFileClick.size-1])))
                      //drawerLayoutFile.closeDrawer(Gravity.RIGHT)
                      //fileAdapter.notifyDataSetChanged()
                  }
                  else if(stFileClick.size == 1){
                      fileList.clear()
                      fileList.addAll(findFiles(File(Environment.getExternalStorageDirectory().toString())))
                      stFileClick.pop()

                  }

                     else  if(drawerLayoutFile.isDrawerOpen(Gravity.RIGHT)){
                            drawerLayoutFile.closeDrawer(Gravity.RIGHT)
                        }
                    else{
                      activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                      (context as Activity).finish()
                  }
                    fileAdapter.notifyDataSetChanged()
                   // fileAdapter.updateData(fileList)
                    //fileAdapter.n
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}