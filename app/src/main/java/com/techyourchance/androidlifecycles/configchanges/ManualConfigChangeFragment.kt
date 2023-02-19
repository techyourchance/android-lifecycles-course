package com.techyourchance.androidlifecycles.configchanges

import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.techyourchance.androidlifecycles.R
import timber.log.Timber


class ManualConfigChangeFragment: Fragment() {

    interface Listener {
        fun onUserInputChanged(userInput: String)
    }

    private var rootView: View? = null

    private lateinit var btnToggleAnimation: Button

    private var animatedImageView: View? = null
    private lateinit var startConstraintSet: ConstraintSet
    private lateinit var endConstraintSet: ConstraintSet
    private lateinit var currentConstraintSet: ConstraintSet
    private var isAnimationEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate(); savedInstanceState: $savedInstanceState")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView()")
        if (rootView == null) {
            Timber.i("initializing the view hierarchy")
            rootView = layoutInflater.inflate(R.layout.fragment_manual_config_change, container, false).apply {
                btnToggleAnimation = findViewById(R.id.btnToggleAnimation)
                btnToggleAnimation.setOnClickListener {
                    setAnimationEnabled(!isAnimationEnabled)
                }
            }
            setAnimationEnabled(false)
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
        Timber.i("onDestroy()")
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Timber.i("onConfigurationChanged()")
        super.onConfigurationChanged(newConfig)
        if (isAnimationEnabled) {
            val viewId = animatedImageView!!.id
            startConstraintSet.clear(viewId, ConstraintSet.START)
            startConstraintSet.connect(viewId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, getHorizontalMargin());
            endConstraintSet.clear(viewId, ConstraintSet.END)
            endConstraintSet.connect(viewId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, getHorizontalMargin());
        }
    }

    private fun setAnimationEnabled(enabled: Boolean) {
        val constraintLayout = rootView as ConstraintLayout

        if (enabled) {
            var imageViewId = 0
            animatedImageView = ImageView(requireContext()).apply {
                setBackgroundResource(R.drawable.ic_point)
                id = View.generateViewId()
                imageViewId = id
                constraintLayout.addView(this, 0)
            }

            createAnimationConstraints(constraintLayout, imageViewId)

            startConstraintSet.applyTo(constraintLayout)

            currentConstraintSet = startConstraintSet

            constraintLayout.post {
                startAnimation(constraintLayout)
                btnToggleAnimation.text= "Disable animation"
            }
        } else {
            TransitionManager.endTransitions(constraintLayout)
            if (animatedImageView != null) {
                constraintLayout.removeView(animatedImageView)
                animatedImageView = null
            }
            btnToggleAnimation.text = "Enable animation"
        }

        isAnimationEnabled = enabled
    }

    private fun createAnimationConstraints(constraintLayout: ConstraintLayout, imageViewId: Int) {
        startConstraintSet = ConstraintSet().apply {
            clone(constraintLayout)
            connect(imageViewId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, getHorizontalMargin());
            connect(imageViewId, ConstraintSet.TOP, R.id.btnToggleAnimation, ConstraintSet.BOTTOM, 50);
        }
        endConstraintSet = ConstraintSet().apply {
            clone(constraintLayout)
            connect(imageViewId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, getHorizontalMargin());
            connect(imageViewId, ConstraintSet.TOP, R.id.btnToggleAnimation, ConstraintSet.BOTTOM, 50);
        }
    }

    private fun getHorizontalMargin(): Int {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        return (displayMetrics.widthPixels.toDouble() * 0.2).toInt()
    }

    private fun startAnimation(constraintLayout: ConstraintLayout) {
        val nextConstraintSet = if (currentConstraintSet == startConstraintSet) {
            endConstraintSet
        } else {
            startConstraintSet
        }

        val transition: Transition = ChangeBounds()
        transition.interpolator = AccelerateDecelerateInterpolator()
        transition.duration = 500
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
            }

            override fun onTransitionEnd(transition: Transition) {
                if (isAnimationEnabled) {
                    startAnimation(constraintLayout)
                }
            }

            override fun onTransitionCancel(transition: Transition) {
            }

            override fun onTransitionPause(transition: Transition) {
            }

            override fun onTransitionResume(transition: Transition) {
            }
        })
        TransitionManager.beginDelayedTransition(constraintLayout, transition)

        nextConstraintSet.applyTo(constraintLayout)

        currentConstraintSet = nextConstraintSet
    }

    companion object {
        fun newInstance(): ManualConfigChangeFragment {
            return ManualConfigChangeFragment()
        }
    }
}