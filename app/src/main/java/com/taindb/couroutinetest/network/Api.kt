package com.taindb.couroutinetest.network

import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Url


interface Api {

    @GET("/photos")
    fun getAllPhotosAsync(): Deferred<List<RetroPhoto>>

    @GET
    fun getDocumentsWithRxJava(@Url url : String) :Single<String>

    @GET
    fun getDocsWithCoroutineAsync(@Url url : String) :Deferred<String>
}