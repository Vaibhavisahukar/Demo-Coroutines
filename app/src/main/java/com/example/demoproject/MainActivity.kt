package com.example.demoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoproject.adapter.RecyclerViewAdapter
import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.viewmodel.MainViewModel
import com.example.demoproject.viewmodel.MainViewModelFactory
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnUserItemClickListener{

    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        (application as UserApplication).applicationComponent.inject(this)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        mainViewModel.userLiveData.observe(this, Observer {
            if(it != null)
                recyclerViewAdapter.setUpdatedData(it.results as ArrayList<ResultsItem>)
            else
                Toast.makeText(this,"Error in getting the data", Toast.LENGTH_SHORT).show()
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)

        val result = ArrayList<ResultsItem>()

        recyclerViewAdapter = RecyclerViewAdapter(result,this)
        recyclerView.adapter = recyclerViewAdapter

    }

    override fun onItemClick(item: ResultsItem, position: Int) {
        val intent : Intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("User item",item)
        startActivity(intent)
    }
}
