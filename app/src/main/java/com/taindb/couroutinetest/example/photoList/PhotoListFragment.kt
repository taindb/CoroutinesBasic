package com.taindb.couroutinetest.example.photoList

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taindb.couroutinetest.R
import com.taindb.couroutinetest.RetroPhoto
import com.taindb.couroutinetest.network.Api
import com.taindb.couroutinetest.network.RetrofitClientInstance
import kotlinx.android.synthetic.main.fragment_photos_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class PhotoListFragment : androidx.fragment.app.Fragment(), CoroutineScope {
    private var retrofit: Api = RetrofitClientInstance.getRetrofitInstance().create(Api::class.java)

    companion object {
        const val TAG = "PhotoListFragment"
    }

    private var adapter: PhotoListAdapter? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photos_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPhotoListSequentially()
        showLoading()
    }

//    private fun getPhotoList() {
//        launch(Dispatchers.Main) {
//            val pageContent = getPageContent()
//            generateDataList(pageContent)
//            hideLoading()
//        }
//    }

    private fun getPhotoListParallel() {
        launch {
            val list1 = async { getPhotoList() }
            val list2 = async { getPhotoList() }
            val data = list1.await().subList(0, 3) + list2.await().subList(0, 3)
            generateDataList(data)
            hideLoading()
        }
    }

    private fun getPhotoListSequentially() {
        launch {
            val list1 = getPhotoList()
            val list2 = getPhotoList()
            val data = list1.subList(0, 3) + list2.subList(0, 3)
            generateDataList(data)
            hideLoading()
        }
    }


    private suspend fun getPhotoList(): List<RetroPhoto> = withContext(Dispatchers.IO) {
        val call = retrofit.getAllPhotosAsync()
        call.await()
    }

    private fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    private fun generateDataList(photoList: List<RetroPhoto>) {
        adapter = PhotoListAdapter(context!!, photoList)
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        customRecyclerView!!.layoutManager = layoutManager
        customRecyclerView!!.adapter = adapter
    }

}
