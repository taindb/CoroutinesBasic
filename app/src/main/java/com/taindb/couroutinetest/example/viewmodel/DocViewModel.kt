package com.taindb.couroutinetest.example.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taindb.couroutinetest.network.Api
import com.taindb.couroutinetest.network.RetrofitClientInstance
import kotlinx.coroutines.*

class DocViewModel : ViewModel() {

    val docLiveData = MutableLiveData<String>()

    val errorLiveData = MutableLiveData<String>()

    val lostDocsLiveData = MutableLiveData<String>()

    val twoDocsLiveData = MutableLiveData<String>()

    val twoDocsSupervisorLiveData = MutableLiveData<String>()

    var retrofit: Api = RetrofitClientInstance.getRetrofitInstance().create(Api::class.java)

    private val coroutineExceptionHandler
            = CoroutineExceptionHandler { _, throwable ->
        errorLiveData.postValue("Error message: ${throwable.message}" )
    }

    fun userNeedDoc() {
        viewModelScope.launch(coroutineExceptionHandler) {
            fetchDoc()
        }
    }

    private suspend fun fetchDoc() {
        coroutineScope {
            val doc = get("https://developer.android.com")
            docLiveData.postValue(doc)
        }
    }

    fun userNeed1000ODocs() {
        viewModelScope.launch(coroutineExceptionHandler) {
            fetch1000Docs()
            lostDocsLiveData.postValue("Lots and lots of work are completed")
        }
    }

    fun userNeedTwoDocWithCoroutine() {
        viewModelScope.launch(coroutineExceptionHandler) {
            fetch2DocsWithCoroutine()
        }
    }

    fun userNeedTwoDocWithSupervisor() {
        viewModelScope.launch(coroutineExceptionHandler) {
            fetch2DocsWithSupervisorCoroutine()
        }
    }

    private suspend fun fetch2DocsWithCoroutine() = coroutineScope {
        async {
            fetch2Doc()
        }

        launch {
            throw java.lang.Exception("Cannot finish the process")
        }

    }

    private suspend fun fetch2DocsWithSupervisorCoroutine() {
        supervisorScope {
            async {
                delay(5000)
                fetch2Doc()
            }

            launch {
                throw java.lang.Exception("Cannot finish the process")
            }

        }
    }

    private suspend fun fetch1000Docs() {
        coroutineScope {
            launch {
                repeat(10) {
                    if (it == 3) {
                        throw Exception("it == 3")
                    }
                    get("https://developer.android.com")
                }
            }
        }
    }


    private suspend fun fetch2Doc() {
        coroutineScope {
            val doc = get("https://developer.android.com")
            twoDocsLiveData.postValue(doc)
        }
    }

    private suspend fun get(url: String): String = withContext(Dispatchers.IO) {
        delay(400)
        val deferred = retrofit.getDocsWithCoroutineAsync(url)
        deferred.await()
    }

    private suspend fun getDeffered(url: String): Deferred<String> = withContext(Dispatchers.IO) {
        retrofit.getDocsWithCoroutineAsync(url)
    }

}