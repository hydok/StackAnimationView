package com.hydok.stackanimationview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.doOnEnd
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class VerticalStackAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleRes) {

    companion object {
        private const val DEFAULT_IN_DURATION = 500L
        private const val DEFAULT_OUT_DURATION = 500L
        private const val DEFAULT_REST_DURATION = 500L
    }

    private val viewStack = ArrayList<View>()

    private var inDuration = DEFAULT_IN_DURATION
    private var outDuration = DEFAULT_OUT_DURATION
    private var restDuration = DEFAULT_REST_DURATION

    private var isAnimPlayed = false

    private var endAction : (() -> Unit)? = null

    fun launchAnimation(action: suspend (VerticalStackAnimationView) -> Unit) = also {
        if (!isAnimPlayed) {
            val a = findViewTreeLifecycleOwner()!!.lifecycleScope.launch {
                clearView()
                action.invoke(this@VerticalStackAnimationView)
                endAction?.invoke()
                isAnimPlayed = false
            }
            isAnimPlayed = true
        }
    }

    fun doOnEnd(endAction: () -> Unit)= also {
        this.endAction = endAction
    }

    suspend fun inView(view: View, topMarginDp: Int = 5) = also {
        view.id = View.generateViewId()
        addView(view)
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        if (viewStack.isNotEmpty()) {
            constraintSet.connect(
                view.id,
                ConstraintSet.TOP,
                viewStack.last().id,
                ConstraintSet.BOTTOM,
                topMarginDp.toDp().toInt()
            )
        }
        constraintSet.connect(view.id, ConstraintSet.START, this.id, ConstraintSet.START, 0)
        constraintSet.connect(view.id, ConstraintSet.END, this.id, ConstraintSet.END, 0)
        constraintSet.applyTo(this)

        val alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        val translationAnimator = ObjectAnimator.ofFloat(view, "translationY", 50f, 0f)
        AnimatorSet().apply {
            playTogether(alphaAnimator, translationAnimator)
            duration = inDuration
            start()
        }
        viewStack.add(view)
        delay(inDuration + restDuration)
    }

    private fun outView(view: View) = also {
        val alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        val translationAnimator = ObjectAnimator.ofFloat(view, "translationY", 0f, 50f)
        AnimatorSet().apply {
            playTogether(alphaAnimator, translationAnimator)
            duration = outDuration
            start()
            doOnEnd {
                removeView(view)
            }
        }

    }

    fun clearView() = also {
        viewStack.forEach { outView(it) }
        viewStack.clear()
    }

    fun setDuration(
        inDuration: Long = DEFAULT_IN_DURATION,
        outDuration: Long = DEFAULT_OUT_DURATION,
        restDuration: Long = DEFAULT_REST_DURATION
    ) = also {
        this.inDuration = inDuration
        this.outDuration = outDuration
        this.restDuration = restDuration
    }

    private fun Int.toDp(): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    )
}