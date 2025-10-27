package com.sabya.truecallerandroidassignment.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sabya.truecallerandroidassignment.R
import com.sabya.truecallerandroidassignment.databinding.ActivityMainBinding
import com.sabya.truecallerandroidassignment.presentation.viewmodel.MainViewModel
import com.sabya.truecallerandroidassignment.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val toolbar = mBinding.includeToolbar.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Truecaller Android Assignment"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Perform three tasks when button is clicked
        mBinding.buttonFetch.setOnClickListener { mViewModel.performTasks() }

        // Handle loading, success, and error states
        mViewModel.state.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                    Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                }

                is Resource.Success -> {
                    mBinding.progressBar.visibility = View.GONE
                }

                is Resource.Error -> {
                    mBinding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        // Observe ViewModel updates
        mViewModel.result1.observe(this) { mBinding.textView1.text = "15th character: "+ it }
        mViewModel.result2.observe(this) { mBinding.textView2.text = it }
        mViewModel.result3.observe(this) { mBinding.textView3.text = it }
    }
}