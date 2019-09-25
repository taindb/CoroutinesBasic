package com.taindb.couroutinetest.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taindb.couroutinetest.R
import kotlinx.android.synthetic.main.activity_lightweight_thread.*
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread


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
            timeDisplayTv.text = "Time = 0"
            coroutines(numberOfThreads)
        }


        startThreadBtn.setOnClickListener {
            timeDisplayTv.text = "Time = 0"
            threads(numberOfThreads)
        }
    }

    private fun threads(n: Int) {
        val startTime = System.currentTimeMillis()
        val threads = List(n) { number ->
            thread {
                Thread.sleep(4000L)
                println(number)
            }
        }

        threads.forEach {
            it.join()
        }



        timeDisplayTv.text = "Time = ${System.currentTimeMillis() - startTime}"
    }

    private fun coroutines(n: Int) {
        val startTime = System.currentTimeMillis()
        runBlocking {

            val job = List(n) { number ->
                async {
                    delay(100L)
                    println("Coroutine ${Thread.currentThread().name}:: $number")
                }
            }

            job.forEach {
                it.join()
            }
        }
        timeDisplayTv.text = "Time = ${System.currentTimeMillis() - startTime}"
    }
}
