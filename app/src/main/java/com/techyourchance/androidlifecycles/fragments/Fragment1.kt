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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView()")
        return layoutInflater.inflate(R.layout.fragment_1, container, false).apply {
            findViewById<Button>(R.id.btnNextFragment).setOnClickListener {
                Toast.makeText(requireContext(), "TODO", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated()")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        Timber.i("onDestroyView()")
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): Fragment1 {
            return Fragment1()
        }
    }
}