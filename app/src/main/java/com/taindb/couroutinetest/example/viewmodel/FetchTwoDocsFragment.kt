package com.taindb.couroutinetest.example.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.taindb.couroutinetest.R
import kotlinx.android.synthetic.main.fragment_docs.*
import kotlinx.android.synthetic.main.fragment_photos_main.loadingView


class FetchTwoDocsFragment : androidx.fragment.app.Fragment() {

    companion object {
        const val TAG = "DocsFragment"
    }

    val mimeType = "text/html"
    val encoding = "UTF-8"

    protected lateinit var docViewModel: DocViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_docs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        docViewModel = ViewModelProviders.of(this).get(DocViewModel::class.java)
        docsWv.settings.javaScriptEnabled = true

        docViewModel.userNeedTwoDocWithCoroutine()
        showLoading()

        docViewModel.twoDocsLiveData.observe(this, Observer<String> {
            showDocs(it)
        })

        docViewModel.errorLiveData.observe(this, Observer {
            showError(it)
            hideLoading()
        })
    }


    private fun showDocs(result: String) {
        docsWv.loadData(result, mimeType, encoding)
        hideLoading()
    }

    private fun showError(error: String) {
        docsWv.loadData(error, mimeType, encoding)
    }

    private fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView.visibility = View.GONE
    }

}

