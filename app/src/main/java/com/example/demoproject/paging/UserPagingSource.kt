package com.example.demoproject.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.data.retrofit.Api
import java.lang.Exception

class UserPagingSource(val api: Api): PagingSource<Int, ResultsItem>() {
    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

        /*if (state.anchorPosition != null) {
            val anchorPage = state.closestPageToPosition(state.anchorPosition!!)
            if (anchorPage?.prevKey != null){
                return anchorPage.prevKey!!.plus(1)
            } else if (anchorPage?.nextKey != null) {
                return anchorPage.nextKey!!.minus(1)
            }
        }
        return null*/
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        return try {
            val position = params.key ?: 1
            val response = api.getUserDetails()
            //val response = api.getUserDetails(position)
            Log.d(response.toString(),"response")
            LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.results.size) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}