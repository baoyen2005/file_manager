package com.example.filesmanager.fragment

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filesmanager.Adapter.FileAdapter
import com.example.filesmanager.R
import com.example.filesmanager.utils.FileOpen
import java.io.File


class FileFragment : Fragment(),FileAdapter.OnItemClickListener {

    private val fileList = ArrayList<File>()
    lateinit var fileAdapter: FileAdapter
    fun newInstance(): FileFragment{
        return FileFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_file, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_menu)
        checkPermission()
    }
    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity, READ_EXTERNAL_STORAGE)){
                    Toast.makeText(context,"Chờ cấp quyền truy cập", Toast.LENGTH_SHORT).show()
                }
                else{
                    ActivityCompat.requestPermissions(context as Activity, arrayOf(
                            READ_EXTERNAL_STORAGE,
                            WRITE_EXTERNAL_STORAGE
                        ),305)               }
            }
            else{
                displayFiles()
            }


        }
        else{
            displayFiles()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 305) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayFiles()
            } else {
                Toast.makeText(context, "permission not run", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun findFiles(file: File): ArrayList<File> {
        val arrayList = ArrayList<File>()

        val files = file.listFiles()
        //Log.d("AAAA",files.toString())
        for (singleFile in files) {
            if (singleFile.isDirectory && !singleFile.isHidden) {
                arrayList.add(singleFile)
            }

        }
        for (singleFile in files) {
            if (singleFile.name.toLowerCase().endsWith(".jpg") ||
                singleFile.name.toLowerCase().endsWith(".png") ||
                singleFile.name.toLowerCase().endsWith(".mp3") ||
                singleFile.name.toLowerCase().endsWith(".wav") ||
                singleFile.name.toLowerCase().endsWith(".mp4") ||
                singleFile.name.toLowerCase().endsWith(".pdf") ||
                singleFile.name.toLowerCase().endsWith(".doc") ||
                singleFile.name.toLowerCase().endsWith(".apk") ||
                singleFile.name.toLowerCase().endsWith(".jpeg") ||
                singleFile.name.toLowerCase().endsWith(".webp") ||
                singleFile.name.toLowerCase().endsWith(".gif") ||
                singleFile.name.toLowerCase().endsWith(".pptx") ||
                singleFile.name.toLowerCase().endsWith(".xlsx")||
                singleFile.name.toLowerCase().endsWith(".xls") ||
                singleFile.name.toLowerCase().endsWith(".txt")||
                singleFile.name.toLowerCase().endsWith(".xml")

            ) {
                arrayList.add(singleFile)
            }
        }
            for (singleFile in files) {
                if (singleFile.isDirectory && !singleFile.isHidden ||
                    singleFile.name.toLowerCase().endsWith(".jpg") ||
                    singleFile.name.toLowerCase().endsWith(".png") ||
                    singleFile.name.toLowerCase().endsWith(".mp3") ||
                    singleFile.name.toLowerCase().endsWith(".wav") ||
                    singleFile.name.toLowerCase().endsWith(".mp4") ||
                    singleFile.name.toLowerCase().endsWith(".pdf") ||
                    singleFile.name.toLowerCase().endsWith(".doc") ||
                    singleFile.name.toLowerCase().endsWith(".apk") ||
                    singleFile.name.toLowerCase().endsWith(".jpeg") ||
                    singleFile.name.toLowerCase().endsWith(".webp") ||
                    singleFile.name.toLowerCase().endsWith(".gif") ||
                   // singleFile.name.toLowerCase().endsWith(".exo") ||
                    singleFile.name.toLowerCase().endsWith(".pptx") ||
                    singleFile.name.toLowerCase().endsWith(".xlsx")||
                    singleFile.name.toLowerCase().endsWith(".xls") ||
                    singleFile.name.toLowerCase().endsWith(".txt")||
                    singleFile.name.toLowerCase().endsWith(".xml")
                   // singleFile.name.toLowerCase().endsWith(".aac")


                ) {
                    Log.d("TAG", "File da add")
                } else {
                    arrayList.add(singleFile)
                }


        }
        return arrayList

    }
    private fun displayFiles() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycle_internal)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //  fileList .clear()

        val internalStorage: String = System.getenv("EXTERNAL_STORAGE")
        val storage = File(internalStorage)
        //val fileList = ArrayList<File>()
        fileList.addAll(findFiles(storage))

        fileAdapter = FileAdapter(requireContext(), fileList,this)
        recyclerView?.adapter = fileAdapter

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
       // Toast.makeText(context,"Item $position", Toast.LENGTH_SHORT).show()
        if(file.isDirectory){ // kiem tra xem co phai thu muc k
            var bundle = Bundle()
            bundle.putString("path",file.absolutePath) // file.absolutePath đường dẫn file

            val fragment: Fragment = FileFragment().newInstance()
            fragment.arguments = bundle

            // thay the fragment nay trong chinh màn hình đó
            activity?.supportFragmentManager!!.beginTransaction().replace(R.id.view_pager,fragment).
            addToBackStack(null).commit()
        }
        else{
                val fileOpen = FileOpen()
                fileOpen.openFile(requireContext(),file)
        }
    }
}