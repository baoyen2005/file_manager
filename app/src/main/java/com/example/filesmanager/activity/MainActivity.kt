package com.example.filesmanager.activity

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.example.filesmanager.R
import com.example.filesmanager.fragment.CleanFragment
import com.example.filesmanager.fragment.FileFragment
import com.example.filesmanager.fragment.ToolFragment
import com.example.filesmanager.Adapter.ViewPagerAdapter
import com.example.filesmanager.fragment.FileManagerFragment
import com.example.filesmanager.utils.IOBackPressed
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), PackageManager.PERMISSION_GRANTED);

        setFragment()
    }

    private fun setFragment() {
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botton_navigation)
        adapter  = ViewPagerAdapter(supportFragmentManager)
        //Lỗi đoạn này này cậu, nó bị không đúng fragment nên kho0ong tạo đc menu
        // the sua sao c?
        adapter.addFragment(CleanFragment(), "Làm")
        adapter.addFragment(ToolFragment(), "Công cụ")
        adapter.addFragment(FileManagerFragment(), "Quản lý tập tin")
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {




            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0-> {
                        bottomNavigationView.menu.findItem(R.id.ic_cleanup).isChecked = true
                        return
                    }
                    1-> {
                        bottomNavigationView.menu.findItem(R.id.ic_tool).isChecked = true
                        return
                    }
                    2-> {
                        bottomNavigationView.menu.findItem(R.id.ic_file).isChecked = true
                        return
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        bottomNavigationView.setOnNavigationItemSelectedListener {item ->
            return@setOnNavigationItemSelectedListener when (item.itemId){
                R.id.ic_cleanup  ->{
                    viewPager.currentItem= 0
                    true
                }
                R.id.ic_tool  ->{
                    viewPager.currentItem = 1
                    true

                }
                R.id.ic_file  -> {
                    viewPager.currentItem = 2
                    true
                }

                else -> { false }
            }
        }

//        bottomNavigationView.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener
//        {
//            override fun onNavigationItemSelected(item: MenuItem): Boolean {
//                TODO("Not yet implemented")
//            }
//        })
    }

    override fun onBackPressed() {
        if (adapter.getItem(2).getChildFragmentManager().getBackStackEntryCount() > 0) {
            adapter.getItem(2).getChildFragmentManager().popBackStackImmediate();
        } else {

                super.onBackPressed();

        }
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.fileFragment)
        (fragment as? IOBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed();
        }
    }

}