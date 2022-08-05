package com.example.demoproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoproject.adapter.UserPagingAdapter
import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.databinding.ActivityMainBinding
import com.example.demoproject.utils.SwipeGesture
import com.example.demoproject.viewmodel.MainViewModel
import com.example.demoproject.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), UserPagingAdapter.OnUserItemClickListener {

    private lateinit var mainBinding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var userPagingAdapter: UserPagingAdapter
    private val visibleThreshold = 5
    private var previousTotal = 0
    private val result = ArrayList<ResultsItem>()
    private var loading = true
    var pastVisibleItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        (application as UserApplication).applicationComponent.inject(this)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        allFilter.setOnClickListener {
            userPagingAdapter.filterList("all")
        }
        femaleFilter.setOnClickListener {
            userPagingAdapter.filterList("female")
        }
        maleFilter.setOnClickListener {
            userPagingAdapter.filterList("male")
        }

        mainViewModel.allUsers.observe(this, Observer {
            if (it != null)
                userPagingAdapter.setUpdatedData(it as ArrayList<ResultsItem>, false)
            else
                Toast.makeText(this, "Error in getting the data", Toast.LENGTH_SHORT).show()
        })

        mainBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration =
                DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)

            userPagingAdapter = UserPagingAdapter(result, this@MainActivity)
            adapter = userPagingAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) { //check for scroll down
                        visibleItemCount = (layoutManager as LinearLayoutManager).childCount
                        totalItemCount = (layoutManager as LinearLayoutManager).itemCount
                        pastVisibleItems =
                            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        if (loading) {
                            if (totalItemCount > previousTotal) {
                                loading = false
                                previousTotal = totalItemCount
                            }
                        }
                        if (!loading && (totalItemCount - visibleItemCount)
                            <= (pastVisibleItems + visibleThreshold)
                        ) {

                            mainViewModel.getUsers()

                            loading = true
                        }
                    }
                }
            })
        }

        val swipeGesture = object : SwipeGesture(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        mainViewModel.deleteUser(userPagingAdapter.results[viewHolder.absoluteAdapterPosition])
                        userPagingAdapter.removeItem(viewHolder.absoluteAdapterPosition)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onItemClick(item: ResultsItem, position: Int) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("User item", item)
        startActivity(intent)
    }
}
