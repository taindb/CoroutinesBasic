package com.taindb.couroutinetest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MainViewModel() : ViewModel(), CoroutineScope {
    private lateinit var job : Job

    init {
        job = Job()
    }

    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

    var fetchDocLiveData  = MutableLiveData<String>()



    fun fetchDoc() {

    }
    override fun onCleared() {
        super.onCleared()
    }
}