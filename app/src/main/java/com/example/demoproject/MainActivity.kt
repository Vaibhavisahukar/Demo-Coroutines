package com.example.demoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoproject.adapter.UserPagingAdapter
import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.databinding.ActivityMainBinding
import com.example.demoproject.viewmodel.MainViewModel
import com.example.demoproject.viewmodel.MainViewModelFactory
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), UserPagingAdapter.OnUserItemClickListener{

    lateinit var mainBinding: ActivityMainBinding
    //lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var mainViewModel: MainViewModel
    lateinit var userPagingAdapter: UserPagingAdapter

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        (application as UserApplication).applicationComponent.inject(this)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        mainViewModel.userLiveData.observe(this, Observer {
            if(it != null)
                //userPagingAdapter.setUpdatedData(it.results as ArrayList<ResultsItem>)
                    userPagingAdapter.submitData(lifecycle, it)
            else
                Toast.makeText(this,"Error in getting the data", Toast.LENGTH_SHORT).show()
        })

        mainBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            val decoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)

            val result = ArrayList<ResultsItem>()

            userPagingAdapter = UserPagingAdapter(result,this@MainActivity)
            adapter = userPagingAdapter
        }

    }

    override fun onItemClick(item: ResultsItem, position: Int) {
        val intent : Intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("User item",item)
        startActivity(intent)
    }
}
