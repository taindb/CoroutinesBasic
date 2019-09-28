package com.taindb.couroutinetest.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taindb.couroutinetest.R
import kotlinx.android.synthetic.main.activity_lightweight_thread.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis


class ThreadAndCoroutinesFragment : androidx.fragment.app.Fragment() {
    companion object {
        const val TAG = "ThreadAndCoroutinesFragment"
    }

    private val numberOfThreads = 100_000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_lightweight_thread, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCoroutinesBtn.setOnClickListener {
            coroutines()
        }


        startThreadBtn.setOnClickListener {
            timeDisplayTv.text = "Time = 0"
            threads()
        }
    }

    /**
     * Create 10k coroutines and each one print a number after 0.1 second delay
     */
    private fun coroutines() = runBlocking {
        val measureTimeMillis = measureTimeMillis {

            val jobs = List(10_000) {number->
                launch {
                    delay(100)
                    println(number)
                }
            }
            jobs.forEach { it.join() }
        }

        timeDisplayTv.text = "$measureTimeMillis ms"
        println("Total Time = $measureTimeMillis")

    }


    /**
     * Create 10k thread and each one print a number after 0.1 second delay
     */
    private fun threads() = runBlocking {
        val measureTimeMillis = measureTimeMillis {
            val jobs = List(10_000) {number ->
                thread {
                    Thread.sleep(100)
                    println(number)
                }
            }

            jobs.forEach { it.join() }
        }
        timeDisplayTv.text = "$measureTimeMillis ms"
        println("Total Time = $measureTimeMillis")
    }

}
