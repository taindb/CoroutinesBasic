package com.taindb.couroutinetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, SampleListFragment(), SampleListFragment.TAG)
                .commitNow()
        }
    }

    override fun onBackPressed() {
        if (!supportFragmentManager.popBackStackImmediate()) super.onBackPressed()
    }

}

