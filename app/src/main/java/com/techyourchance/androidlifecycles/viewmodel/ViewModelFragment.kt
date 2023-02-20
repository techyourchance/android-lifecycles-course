package com.techyourchance.androidlifecycles.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.techyourchance.androidlifecycles.R
import timber.log.Timber

class ViewModelFragment: Fragment() {

    private lateinit var viewModel: FragmentViewModel

    private var rootView: View? = null
    private lateinit var btnToggleCount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate(); savedInstanceState: $savedInstanceState")
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView()")
        if (rootView == null) {
            Timber.i("initializing the view hierarchy")
            rootView = layoutInflater.inflate(R.layout.fragment_view_model, container, false).apply {
                btnToggleCount = findViewById(R.id.btnToggleCount)
            }
        }

        btnToggleCount.setOnClickListener {
            viewModel.toggleCounter()
            updateUi(viewModel.isCounting)
        }

        return rootView
    }


    private fun updateUi(isCounting: Boolean) {
        if (isCounting) {
            btnToggleCount.text = "Stop counter"
        } else {
            btnToggleCount.text = "Start counter"
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated()")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.i("onSaveInstanceState()")
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()
        updateUi(viewModel.isCounting)
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()
    }

    override fun onDestroyView() {
        Timber.i("onDestroyView()")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        super.onDestroy()
    }


    companion object {
        fun newInstance(): ViewModelFragment {
            return ViewModelFragment()
        }
    }
}