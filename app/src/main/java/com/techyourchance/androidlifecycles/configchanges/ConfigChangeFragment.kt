package com.techyourchance.androidlifecycles.configchanges

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.techyourchance.androidlifecycles.R
import timber.log.Timber

class ConfigChangeFragment: Fragment() {

    interface Listener {
        fun onUserInputChanged(userInput: String)
    }

    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate(); identity: ${System.identityHashCode(this)}")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView()")
        if (rootView == null) {
            Timber.i("initializing the view hierarchy")
            rootView = layoutInflater.inflate(R.layout.fragment_config_change, container, false).apply {
                findViewById<EditText>(R.id.edtNumber).addTextChangedListener {
                    val activity = requireActivity()
                    if (activity is Listener) {
                        activity.onUserInputChanged(it?.toString() ?: "")
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

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.i("onSaveInstanceState()")
        super.onSaveInstanceState(outState)
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
        Timber.i("onDestroy(); identity: ${System.identityHashCode(this)}")
        super.onDestroy()
    }


    companion object {
        fun newInstance(): ConfigChangeFragment {
            return ConfigChangeFragment()
        }
    }
}