package com.techyourchance.androidlifecycles.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.techyourchance.androidlifecycles.R
import timber.log.Timber

class Fragment1: Fragment() {

    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate()")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView()")
        if (rootView == null) {
            Timber.i("initializing the view hierarchy")
            rootView = layoutInflater.inflate(R.layout.fragment_1, container, false).apply {
                findViewById<Button>(R.id.btnNextFragment).apply {
                    setOnClickListener {
                        Timber.i("Button clicked: next Fragment")
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, Fragment2.newInstance(), "fragmentTag")
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated()")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()
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
        fun newInstance(): Fragment1 {
            return Fragment1()
        }
    }
}