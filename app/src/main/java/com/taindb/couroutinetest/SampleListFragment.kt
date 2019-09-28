package com.taindb.couroutinetest

import android.os.Bundle
import android.widget.ArrayAdapter
import com.taindb.couroutinetest.example.ThreadAndCoroutinesFragment
import com.taindb.couroutinetest.example.viewmodel.FetchDocFragment
import com.taindb.couroutinetest.example.viewmodel.FetchTwoDocsFragment
import com.taindb.couroutinetest.example.viewmodel.FetchTwoDocsSupervisoFragment
import com.taindb.couroutinetest.example.viewmodel.LostWorkFragment

class SampleListFragment : androidx.fragment.app.ListFragment() {

    companion object {
        const val TAG = "SampleListFragment"
        private const val THREAD_COROUTINES_LAUNCH = "1. Thread vs Coroutines"
//        private const val SAMPLE_GET_PHOTOS = "2. Photo List"
        private const val SAMPLE_GET_DOCUMENTS = "2. Showing Document"
        private const val SAMPLE_LOAD_LOST_DOCUMENT = "3. Load Lost Documents"
        private const val SAMPLE_FETCH_TWO_DOCS = "4. Fetch two docs"
        private const val SAMPLE_FETCH_TWO_DOCS_WITH_SUPERVISOR = "5. Fetch two docs (Supervisor)"
        private const val SAMPLE_EXCEPTION_HANDLER = "6. Exception Handling (handler)"
        private const val SAMPLE_LIFECYCLE = "7. Lifecycle Awareness (LifecycleObserver)"
        private const val SAMPLE_SCOPED_FRAGMENT = "8. Lifecycle Awareness (ScopedFragment)"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val list = arrayListOf(
            THREAD_COROUTINES_LAUNCH,
//            SAMPLE_GET_PHOTOS,
            SAMPLE_GET_DOCUMENTS,
            SAMPLE_LOAD_LOST_DOCUMENT,
            SAMPLE_FETCH_TWO_DOCS,
            SAMPLE_FETCH_TWO_DOCS_WITH_SUPERVISOR
//            SAMPLE_EXCEPTION_HANDLER,
//            SAMPLE_LIFECYCLE,
//            SAMPLE_SCOPED_FRAGMENT
        )
        listAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list)
        listView.setOnItemClickListener { _, _, position, _ ->
            when (list[position]) {
                THREAD_COROUTINES_LAUNCH -> showFragment(
                    ThreadAndCoroutinesFragment(),
                    ThreadAndCoroutinesFragment.TAG
                )
//                SAMPLE_GET_PHOTOS -> showFragment(
//                    PhotoListFragment(),
//                    PhotoListFragment.TAG
//                )
                SAMPLE_GET_DOCUMENTS -> showFragment(
                    FetchDocFragment(),
                    FetchDocFragment.toString()
                )
                SAMPLE_LOAD_LOST_DOCUMENT -> showFragment(LostWorkFragment(), LostWorkFragment.TAG)
                SAMPLE_FETCH_TWO_DOCS -> showFragment(FetchTwoDocsFragment(), FetchTwoDocsFragment.TAG)
                SAMPLE_FETCH_TWO_DOCS_WITH_SUPERVISOR -> showFragment(FetchTwoDocsSupervisoFragment(), FetchTwoDocsSupervisoFragment.TAG)
//                SAMPLE_EXCEPTION_HANDLER -> showFragment(
//                    ExceptionHandlerFragment(),
//                    ExceptionHandlerFragment.TAG
//                )
//                SAMPLE_LIFECYCLE -> showFragment(
//                    LifecycleAwareFragment(),
//                    LifecycleAwareFragment.TAG
//                )
//                SAMPLE_SCOPED_FRAGMENT -> showFragment(
//                    AndroidScopedFragment(),
//                    AndroidScopedFragment.TAG
//                )
            }
        }
    }

    private fun showFragment(fragment: androidx.fragment.app.Fragment, tag: String) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }
}

