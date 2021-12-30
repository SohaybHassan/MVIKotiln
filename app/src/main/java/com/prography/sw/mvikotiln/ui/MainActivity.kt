package com.prography.sw.mvikotiln.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.prography.sw.mvikotiln.ui.main.MainFragment
import com.prography.sw.mvikotiln.R
import com.prography.sw.mvikotiln.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showMainFragment()


    }

    fun initViewModel(){
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)
    }

    fun showMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment(), "MainFragment").commit()
    }
}