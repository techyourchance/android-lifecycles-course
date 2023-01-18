package com.techyourchance.androidlifecycles.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.techyourchance.androidlifecycles.R
import timber.log.Timber

class Fragment1: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_1, container, false)
    }

    companion object {
        fun newInstance(): Fragment1 {
            return Fragment1()
        }
    }
}