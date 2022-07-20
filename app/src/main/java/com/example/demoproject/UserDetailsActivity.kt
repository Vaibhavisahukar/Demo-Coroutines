package com.example.demoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.databinding.ActivityUserDetailsBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_details.*

class UserDetailsActivity : AppCompatActivity() {

    lateinit var userDetailsBinding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_user_details)

        val details = intent.getParcelableExtra<ResultsItem>("User item")
        userDetailsBinding.results = details
    }
}
