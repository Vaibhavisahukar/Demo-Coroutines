package com.example.demoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoproject.adapter.RecyclerViewAdapter
import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.database.UserDatabase
import com.example.demoproject.databinding.ActivityMainBinding
import com.example.demoproject.utils.SwipeGesture
import com.example.demoproject.viewmodel.MainViewModel
import com.example.demoproject.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnUserItemClickListener{

    lateinit var mainBinding: ActivityMainBinding
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        (application as UserApplication).applicationComponent.inject(this)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        mainViewModel.allUsers.observe(this, Observer {
            if(it != null)
                recyclerViewAdapter.setUpdatedData(it as ArrayList<ResultsItem>)
            else
                Toast.makeText(this,"Error in getting the data", Toast.LENGTH_SHORT).show()
        })

        mainBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)

            val result = ArrayList<ResultsItem>()

            recyclerViewAdapter = RecyclerViewAdapter(result,this@MainActivity)
            adapter = recyclerViewAdapter
            /*recyclerViewAdapter.setOnUserItemClickDelete({

            })*/

        }

        val swipeGesture = object : SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction){
                    ItemTouchHelper.RIGHT -> mainViewModel.deleteUser(recyclerViewAdapter.results.get(viewHolder.absoluteAdapterPosition))
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerView)

    }

    override fun onItemClick(item: ResultsItem, position: Int) {
        val intent : Intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("User item",item)
        startActivity(intent)
    }
}
