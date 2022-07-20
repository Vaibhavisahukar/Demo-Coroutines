package com.example.demoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demoproject.data.model.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_details.*

class UserDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val details = intent.getParcelableExtra<ResultsItem>("User item")
        nameDetails.text = details?.name?.title+" "+details?.name?.first + " " + details?.name?.last

        val url = details?.picture?.large
        Picasso.get()
            .load(url)
            .into(imageDetails)
        email.text = getString(R.string.email)+details?.email
        phone.text = getString(R.string.phones)+details?.phone
        city.text = details?.location?.city+ ", " + details?.location?.country
        age.text = getString(R.string.age)+details?.dob?.age.toString()
        dob.text = getString(R.string.dob)+details?.dob?.date
        address.text = getString(R.string.address)+details?.location?.street?.number.toString() + ", " + details?.location?.street?.name
        postcode.text = getString(R.string.postcode)+details?.location?.postcode
    }
}
