package com.example.testassignment.presentation.core

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.testassignment.extensions.hideKeyboard
import javax.inject.Inject

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    /**
     * Using for triggering inflating finished event
     */
    abstract fun onViewReady(inflatedView: View, args: Bundle?)

    /**
     * Using for initializing view models with viewModelProvider
     */
    abstract fun initViewModel()

    /**
     * Using for handling parent view state after any recreations or popping back from another fragment
     * @property isViewStateSaveEnabled should be set to true to save the view state
     * all subscriptions (like livedata args) then should be defined in [Fragment.onStart] callback
     */
    private var mView: View? = null
    protected var isViewStateSaveEnabled = false
    private var isAlreadyInitialised = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView == null) {
            _binding = inflate.invoke(inflater, container, false)
            mView = binding.root
        } else {
            isAlreadyInitialised = true
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isViewStateSaveEnabled) {
            if (!isAlreadyInitialised) {
                onViewReady(view, savedInstanceState)
            }
        } else {
            onViewReady(view, savedInstanceState)
        }
        initViewModel()

        view.post {
            hideKeyboard()
        }
    }

    override fun onDestroy() {
        hideKeyboard()
        super.onDestroy()
    }

    interface OnKeyboardVisibilityListener {
        fun onVisibilityChanged(visible: Boolean)
    }

    fun setKeyboardVisibilityListener(onKeyboardVisibilityListener: OnKeyboardVisibilityListener) {
        mView?.let { parentView ->
            parentView.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                private var alreadyOpen = false
                private val defaultKeyboardHeightDP = 100
                private val EstimatedKeyboardDP = defaultKeyboardHeightDP + 48
                private val rect: Rect = Rect()
                override fun onGlobalLayout() {
                    val estimatedKeyboardHeight = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        EstimatedKeyboardDP.toFloat(),
                        parentView.resources.displayMetrics
                    )
                        .toInt()
                    parentView.getWindowVisibleDisplayFrame(rect)
                    val heightDiff: Int = parentView.rootView.height - (rect.bottom - rect.top)
                    val isShown = heightDiff >= estimatedKeyboardHeight
                    if (isShown == alreadyOpen) return
                    alreadyOpen = isShown
                    onKeyboardVisibilityListener.onVisibilityChanged(isShown)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!isViewStateSaveEnabled) {
            _binding = null
            (mView?.parent as? ViewGroup)?.removeView(mView)
        }
    }

}