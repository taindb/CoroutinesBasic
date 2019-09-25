package com.taindb.couroutinetest.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.taindb.couroutinetest.R
import com.taindb.couroutinetest.network.Api
import com.taindb.couroutinetest.network.RetrofitClientInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.fragment_docs.*
import kotlinx.android.synthetic.main.fragment_photos_main.loadingView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.coroutineScope as coroutineScope1

var mNetwork: Api = RetrofitClientInstance.getRetrofitInstance().create(Api::class.java)

class DocsFragment : androidx.fragment.app.Fragment(), CoroutineScope {

    companion object {
        const val TAG = "DocsFragment"
    }

    val mimeType = "text/html"
    val encoding = "UTF-8"

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_docs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        docsWv.settings.javaScriptEnabled = true


        launch {
            fetchDocs()
        }

        val async = async {
            fetchDocs()
        }



        showLoading()
    }

    fun userNeedDocs() {
//        CoroutineScope(Dispatchers.Main).launch {
//            // This block starts a new coroutine
//            // "in" the scope.
//            //
//            // It can call suspend functions
//            fetchDocs()
//        }

        launch {

            coroutineScope1 {

            }
            launch {
                kotlinx.coroutines.delay(1000)
                println("launch")
            }
            async {
                kotlinx.coroutines.delay(500)
                println("async")
            }
        }


    }

    // Dispatchers.Main
//    suspend fun fetchDocs() {
//        // Dispatchers.Main
//        var result = getDocs("https://medium.com")
//
//        // Dispatchers.Main
//        showDocs(result)
//    }

    private fun showDocs(result: String) {
        docsWv.loadData(result, mimeType, encoding)
        hideLoading()
    }

    private fun showError() {
        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
    }

    private suspend fun getss() {
        coroutineScope1 {
            kotlinx.coroutines.coroutineScope {

            }
        }
    }

//    suspend fun fetchTwoDocs() {
//        supervisorScope {
//            launch { fetchDoc(1) }
//            async { fetchDoc(2) }
//        }
//    }

    // run on IO thread
    private suspend fun getDocs(url: String): String = withContext(Dispatchers.IO) {
        //        var call: Deferred<String>? = null
//        supervisorScope {
//            async {
//                throw Exception("crash here")
//            }
//        }
//        delay(3000)
//        // a waiting for completion (docs result)
//        return "heehhe"

        val docsWithCoroutineAsync = mNetwork.getDocsWithCoroutineAsync(url)
        docsWithCoroutineAsync.await()

    }

    suspend fun foundError() {
        val coroutineScope = coroutineScope1 {
            val async = async {
                throw Exception("throw")
            }
            async
        }
    }


    private fun fetchDocs() {
        val subscribe = mNetwork.getDocumentsWithRxJava("https://developer.android.com")
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                showDocs(result)
            }, {
                showError()
            })
    }


    private fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    private suspend fun get(url: String): String = withContext(Dispatchers.IO) {
        val docsWithCoroutineAsync = mNetwork.getDocsWithCoroutineAsync(url)
        docsWithCoroutineAsync.await()
    }

    suspend fun fetchAndCombineLostError(url1: String, url2: String): String {
        val deferred1 = async { get(url1) }
        val deferred2 = async { get(url2) }
        return deferred1.await() + deferred2.await()
    }

    suspend fun fetchAndCombine(url1: String, url2: String): String =
        coroutineScope1 {
            val deferred1 = async { get(url1) }
            val deferred2 = async { get(url2) }
            deferred1.await() + deferred2.await()
        }
}