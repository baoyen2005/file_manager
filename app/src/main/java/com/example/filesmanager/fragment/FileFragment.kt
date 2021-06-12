package com.example.filesmanager.fragment

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filesmanager.Adapter.FileAdapter
import com.example.filesmanager.R
import java.io.File


class FileFragment : Fragment(), FileAdapter.OnItemClickListener {

    private val fileList = ArrayList<File>()
    lateinit var fileAdapter: FileAdapter
    private var filePath: String? = null
    fun newInstance(): FileFragment {
        return FileFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_file, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_menu)
        val bundle = this.arguments
        filePath = bundle?.getString("path")

        setUpRecyclerView(view)


        checkPermission()
    }

    private fun setUpRecyclerView(view: View) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycle_internal)
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
//        return arrayList
        //Log.d("AAAA",files.toString())
//        for (singleFile in files) {
//            if (singleFile.isDirectory && !singleFile.isHidden) {
//                arrayList.add(singleFile)
//            }
//
//        }
//        for (singleFile in files ) {
//            if (singleFile.isDirectory &&!singleFile.isHidden ||
//                singleFile.name.toLowerCase().endsWith(".jpg") ||
//                singleFile.name.toLowerCase().endsWith(".png") ||
//                singleFile.name.toLowerCase().endsWith(".mp3") ||
//                singleFile.name.toLowerCase().endsWith(".wav") ||
//                singleFile.name.toLowerCase().endsWith(".mp4") ||
//                singleFile.name.toLowerCase().endsWith(".pdf") ||
//                singleFile.name.toLowerCase().endsWith(".docx") ||
//                singleFile.name.toLowerCase().endsWith(".apk") ||
//                singleFile.name.toLowerCase().endsWith(".jpeg") ||
//                singleFile.name.toLowerCase().endsWith(".webp") ||
//                singleFile.name.toLowerCase().endsWith(".gif") ||
//                singleFile.name.toLowerCase().endsWith(".pptx") ||
//                singleFile.name.toLowerCase().endsWith(".xlsx")||
//                singleFile.name.toLowerCase().endsWith(".xls") ||
//                singleFile.name.toLowerCase().endsWith(".txt")||
//                singleFile.name.toLowerCase().endsWith(".xml")
//
//            ) {
//                arrayList.add(singleFile)
//            }
//        }
//            for (singleFile in files) {
//                if (singleFile.isDirectory && !singleFile.isHidden ||
//                    singleFile.name.toLowerCase().endsWith(".jpg") ||
//                    singleFile.name.toLowerCase().endsWith(".png") ||
//                    singleFile.name.toLowerCase().endsWith(".mp3") ||
//                    singleFile.name.toLowerCase().endsWith(".wav") ||
//                    singleFile.name.toLowerCase().endsWith(".mp4") ||
//                    singleFile.name.toLowerCase().endsWith(".pdf") ||
//                    singleFile.name.toLowerCase().endsWith(".docx") ||
//                    singleFile.name.toLowerCase().endsWith(".apk") ||
//                    singleFile.name.toLowerCase().endsWith(".jpeg") ||
//                    singleFile.name.toLowerCase().endsWith(".webp") ||
//                    singleFile.name.toLowerCase().endsWith(".gif") ||
//                   // singleFile.name.toLowerCase().endsWith(".exo") ||
//                    singleFile.name.toLowerCase().endsWith(".pptx") ||
//                    singleFile.name.toLowerCase().endsWith(".xlsx")||
//                    singleFile.name.toLowerCase().endsWith(".xls") ||
//                    singleFile.name.toLowerCase().endsWith(".txt")||
//                    singleFile.name.toLowerCase().endsWith(".xml")
//                   // singleFile.name.toLowerCase().endsWith(".aac")
//
//
//                ) {
//                    Log.d("TAG", "File da add")
//                } else {
//                    arrayList.add(singleFile)
//                }


      //  }
        return arrayList


    }

    private fun displayFiles() {



//        val internalStorage: String = System.getenv("EXTERNAL_STORAGE")
        val DIR_INTERNAL = Environment.getExternalStorageDirectory().toString()
        val storage = File(DIR_INTERNAL)
        //val fileList = ArrayList<File>()
       // if (filePath != null) {
         //   fileList.clear()
           // fileList.addAll(findFiles(File(filePath)))
        //} else {
            fileList.clear()
            fileList.addAll(findFiles(storage))
       // }

        fileAdapter.updateData(fileList)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
        //return
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(file: File) {

//        val parentFragment = parentFragment as FileManagerFragment
//        parentFragment?.goNextFolder(file.absolutePath.toString())

        val arrayList = ArrayList<File>()
        val files = file.listFiles()
        if (files != null ) {
            arrayList.clear()
            arrayList.addAll(files)
        }
     //   var  listFolder = ArrayList<File>()
        //.clear()
        fileList.clear()
        fileList.addAll(findFiles(File(file.absolutePath)))
        fileAdapter.notifyDataSetChanged()
    }

    override fun onOptionsMenuClicked(view: View, file: File) {
        val popupMenu = PopupMenu(context,view)

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            if (item.itemId == R.id.ic_information) {
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


}